package com.milkcocoa.info.shochu_club.server.infla.mail

import com.milkcocoa.info.shochu_club.server.domain.repository.MailBackend
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.io.Closeable
import java.util.LinkedList
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit


class SmtpTransportPool(
    private val session: Session,
    poolSize: Int,
): Closeable{
    private val pool: LinkedList<Transport> = LinkedList()

    private val semaphore = Semaphore(poolSize, true)

    private fun createTransport(): Transport{
        val transport = session.getTransport("smtp") as Transport
        transport.connect()
        return transport
    }

    fun borrowTransport(): Pair<Transport, Session>{
        if (!semaphore.tryAcquire(30, TimeUnit.SECONDS)) {
            throw IllegalStateException("Failed to acquire transport within timeout")
        }
        synchronized(pool){
            val transport = pool.pollFirst() ?: throw IllegalStateException("No transport available in pool")
            if(transport.isConnected.not()){
                transport.connect()
            }

            return transport to session
        }
    }

    fun returnTransport(transport: Transport){
        synchronized(pool){
            pool.add(transport)
        }
        semaphore.release()
    }


    init {
        repeat(poolSize) {
            pool.add(createTransport())
        }
    }

    override fun close() {
        synchronized(pool){
            pool.forEach{ it.close() }
            pool.clear()
        }
    }
}

inline fun SmtpTransportPool.use(crossinline block: (Transport, Session) -> Unit) {
    val (transport, session) = borrowTransport()
    try {
        block(transport, session)
    } finally {
        returnTransport(transport)
    }
}

class SmtpBackend(
    private val pool: SmtpTransportPool
): MailBackend {
    override fun sendEmail(
        from: String,
        subject: String,
        body: String,
        to: List<String>,
        cc: List<String>,
        bcc: List<String>
    ) {
        pool.use { transport, session ->
            val message = MimeMessage(session).apply {
                setFrom(from)
                setRecipients(Message.RecipientType.TO, to.map { InternetAddress(it) }.toTypedArray())
                setRecipients(Message.RecipientType.CC, cc.map { InternetAddress(it) }.toTypedArray())
                setRecipients(Message.RecipientType.BCC, bcc.map { InternetAddress(it) }.toTypedArray())

                setSubject(subject)
                setText(body)
            }

            transport.sendMessage(
                message,
                message.allRecipients
            )
        }
    }
}