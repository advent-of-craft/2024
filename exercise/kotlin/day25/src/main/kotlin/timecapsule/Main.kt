package timecapsule

fun main() {
    val timeCapsule = Capsule()

    println("🕰️ Welcome to the Time Capsule 🎅")

    if (timeCapsule.hasPastMessage) {
        println("\n📜 Message from your past self:")
        println("Written on: ${timeCapsule.timestamp}")
        println("💌 Message: ${timeCapsule.pastMessage}\n")
    } else {
        println("\n📜 No message from your past self yet.")
    }

    print("✍️  Enter a message for your future self: ")
    val message = readlnOrNull() ?: ""

    timeCapsule.saveMessage(message)

    println("\n🎉 Your message has been saved and added to the Time Capsule!")
    println("Opening the Time Capsule in your browser...\n")

    Browser.open(timeCapsule.filePath)

    println("🌟 Thank you for participating in the Craft Advent Calendar! 🌟")
}