package com.milkcocoa.info.shochu_club.server.infla.mail

import com.milkcocoa.info.shochu_club.server.domain.repository.MailBackend
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FileBackend(private val outputDir: File) : MailBackend {
    init {
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
    }

    override fun sendEmail(
        from: String,
        subject: String,
        body: String,
        to: List<String>,
        cc: List<String>,
        bcc: List<String>
    ) {
        val fileName = "email-${UUID.randomUUID()}.txt"
        val file = File(outputDir, fileName)
        file.writeText(
            """
            ------------------------------------------
            date: ${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}
            ------------------------------------------
            from: $from
            to: ${to.joinToString(", ")}
            cc: ${cc.joinToString(", ")}
            bcc: ${bcc.joinToString(", ")}
            ------------------------------------------
            subject: $subject
            body: $body
            ------------------------------------------
            """.trimIndent()
        )
        println("Email saved to: ${file.absolutePath}")
    }
}
