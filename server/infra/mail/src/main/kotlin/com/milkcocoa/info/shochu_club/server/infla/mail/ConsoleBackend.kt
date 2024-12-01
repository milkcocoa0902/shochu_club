package com.milkcocoa.info.shochu_club.server.infla.mail

import com.milkcocoa.info.shochu_club.server.domain.repository.MailBackend
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ConsoleBackend: MailBackend {
    override fun sendEmail(
        from: String,
        subject: String,
        body: String,
        to: List<String>,
        cc: List<String>,
        bcc: List<String>
    ) {
        println(
            """
                ------------------------------------------
                session: ${UUID.randomUUID()}
                date: ${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}
                ------------------------------------------
                to: ${to.joinToString(", ")}
                cc: ${cc.joinToString(", ")}
                bcc: ${bcc.joinToString(", ")}
                ------------------------------------------
                title: $subject
                body: $body
                ------------------------------------------
            """.trimIndent()
        )
    }
}