package ehb.finalwork.services

interface EmailService {
    fun sendEmail(to: String, subject: String, body: String)
}