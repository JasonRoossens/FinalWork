package ehb.finalwork.services

import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import org.springframework.stereotype.Service
import java.io.IOException



@Service
class SendGridEmailService : EmailService {
    override fun sendEmail(to: String, subject: String, body: String) {
        val sendGridApiKey = "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" // Replace with your actual SendGrid API key
        val fromEmail = "notsneakpeek@hotmail.com" // Replace with your desired "from" email address

        val sendGrid = SendGrid(sendGridApiKey)
        val from = Email(fromEmail)
        val to = Email(to)

        // Your custom HTML template with a logo
        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>$subject</title>
                <style>
                    /* Your custom CSS styles here */
                </style>
            </head>
            <body>
                <img src="https://thelimitedclub.com/cdn/shop/products/dunk-low-denim-light-orewood-brown-459929.png?v=1685909900" alt="Logo" width="150" height="100">
                <h1>$subject</h1>
                <p>$body</p>
            </body>
            </html>
        """.trimIndent()

        val content = Content("text/html", htmlContent)
        val mail = Mail(from, subject, to, content)

        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sendGrid.api(request)

            if (response.statusCode >= 200 && response.statusCode < 300) {
                println("Email sent successfully.")
            } else {
                println("Failed to send email. Status code: ${response.statusCode}")
                println("Response body: ${response.body}")
            }
        } catch (ex: IOException) {
            println("Error sending email: ${ex.message}")
            ex.printStackTrace()
        }
    }
    fun sendWelcomeEmail(to: String, username: String) {
        val subject = "Welcome to Sneakpeek!"
        val body = "Hello $username,\n\nThank you for signing up on Sneakpeek! We're excited to have you on board. Feel free to explore the latest sneaker releases and add your favorite sneakers to your favorites list. If you have any questions or need assistance, don't hesitate to reach out to our support team.\n\nHappy sneaker hunting!\n\nBest regards,\nSneakpeek"

        sendEmail(to, subject, body)
    }
}