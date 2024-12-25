package timecapsule

import java.awt.Desktop
import java.io.File
import java.nio.file.{Files, Paths}
import java.time.Instant.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*
import java.time.{Instant, ZoneId}
import java.util.Locale

class Capsule:
  val FilePath = "timecapsule.html"
  private val Template = "timecapsule_template.html"
  var pastMessage: Option[String] = None
  var timestamp: Option[Instant] = None
  var hasPastMessage: Boolean = loadPastMessage()

  def saveMessage(message: String): Unit =
    val templatePath = Paths.get(Template)
    if !Files.exists(templatePath) then
      throw new java.io.FileNotFoundException("HTML template file not found!")

    val formatter = ofPattern("MMMM dd, yyyy HH:mm:ss", Locale.ENGLISH)
    val filledContent = Files.readString(templatePath)
      .replace("{{message}}", message)
      .replace("{{timestamp}}", formatter.format(now().atZone(ZoneId.systemDefault())))

    Files.write(
      Paths.get(FilePath),
      filledContent.getBytes
    )

    timestamp = Some(now())
    pastMessage = Some(message)

  private def loadPastMessage(): Boolean =
    val path = Paths.get(FilePath)
    if !Files.exists(path) then return false

    val htmlContent = Files.readString(path)
    val startIndex = htmlContent.indexOf("<!--MESSAGE_START-->")
    val endIndex = htmlContent.indexOf("<!--MESSAGE_END-->")

    if startIndex == -1 || endIndex == -1 then return false

    pastMessage = Some(htmlContent.substring(startIndex + 18, endIndex).trim)
    timestamp = Some(Files.getLastModifiedTime(path).toInstant)
    true

object Browser:
  def open(filePath: String): Unit =
    if Desktop.isDesktopSupported then
      Desktop.getDesktop.browse(
        new File(filePath).toURI
      )
    else
      println(s"Unable to open the file in the browser: $filePath")
