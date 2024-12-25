package timecapsule

import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.io.StdIn.readLine

@main def main(): Unit =
  val timeCapsule = new Capsule()

  println("🕰️ Welcome to the Time Capsule 🎅")

  if timeCapsule.hasPastMessage then
    println("\n📜 Message from your past self:")
    println(
      s"Written on: ${
        timeCapsule.timestamp.map(t =>
          DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss", Locale.ENGLISH)
            .format(t)
        ).getOrElse("Unknown")
      }"
    )
    println(s"💌 Message: ${timeCapsule.pastMessage.getOrElse("")}\n")
  else
    println("\n📜 No message from your past self yet.")

  print("✍️  Enter a message for your future self: ")
  val message = readLine().trim

  timeCapsule.saveMessage(message)

  println("\n🎉 Your message has been saved and added to the Time Capsule!")
  println("Opening the Time Capsule in your browser...\n")

  Browser.open(timeCapsule.FilePath)

  println("🌟 Thank you for participating in the Craft Advent Calendar! 🌟")
