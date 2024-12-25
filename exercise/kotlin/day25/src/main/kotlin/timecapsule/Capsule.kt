package timecapsule

import java.io.File
import java.io.FileNotFoundException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Capsule {
    private val template = "timecapsule_template.html"
    val filePath = "timecapsule.html"

    var pastMessage: String? = null
    var timestamp: LocalDateTime? = null
    var hasPastMessage = loadPastMessage()

    private fun loadPastMessage(): Boolean = File(filePath)
        .let {
            if (!it.exists()) return false

            val htmlContent = it.readText()
            val startIndex = htmlContent.indexOf("<!--MESSAGE_START-->")
            val endIndex = htmlContent.indexOf("<!--MESSAGE_END-->")

            if (startIndex == -1 || endIndex == -1) return false

            pastMessage = htmlContent.substring(startIndex + 18, endIndex).trim()
            val lastModifiedInstant = Instant.ofEpochMilli(it.lastModified())
            timestamp = LocalDateTime.ofInstant(lastModifiedInstant, ZoneId.systemDefault())

            return true
        }

    fun saveMessage(message: String) = File(template)
        .let {
            if (!it.exists()) {
                throw FileNotFoundException("HTML template file not found!")
            }

            val filledContent = it.readText()
                .replace("{{message}}", message)
                .replace(
                    "{{timestamp}}",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss"))
                )

            File(filePath).writeText(filledContent)

            timestamp = LocalDateTime.now()
            pastMessage = message
        }
}