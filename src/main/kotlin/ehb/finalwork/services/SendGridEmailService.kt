package ehb.finalwork.services

import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import org.springframework.core.env.Environment



@Service
class SendGridEmailService : EmailService {

    @Autowired
    private lateinit var environment: Environment

    override fun sendEmail(to: String, subject: String, body: String) {
        val sendGridApiKey = environment.getProperty("sendgrid.api.key") // Replace with your actual SendGrid API key
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
                    .logo-bg {
                        background-color: #C4D663;
                        width: 15%;
                        padding-right: 2%;
                    }
                    .logo-bg img{
                        width: 100%;
                        height: auto;
                        padding: 15px;
                    }
                </style>
            </head>
            <body>
                <div class="logo-bg">
                <img src="https://sneakpeek-frontend.onrender.com/assets/logo-10436431.png" alt="Logo" width="150" height="100">
                </div>
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