package com.milkcocoa.info.shochu_club.server.domain.repository

interface MailBackend {
    fun sendEmail(
        from: String,
        subject: String,
        body: String,
        to: List<String>,
        cc: List<String> = emptyList(),
        bcc: List<String> = emptyList(),
    )
}