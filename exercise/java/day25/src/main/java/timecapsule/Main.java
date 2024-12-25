package timecapsule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        Capsule timeCapsule = new Capsule();

        out.println("🕰️ Welcome to the Time Capsule 🎅");

        if (timeCapsule.hasPastMessage()) {
            out.println("\n📜 Message from your past self:");
            out.println("Written on: " + timeCapsule.getTimestamp());
            out.println("💌 Message: " + timeCapsule.getPastMessage() + "\n");
        } else {
            out.println("\n📜 No message from your past self yet.");
        }

        out.print("✍️  Enter a message for your future self: ");
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        String futureMessage = scanner.nextLine();

        try {
            timeCapsule.saveMessage(futureMessage);
            out.println("\n🎉 Your message has been saved and added to the Time Capsule!");
            out.println("Opening the Time Capsule in your browser...\n");
            Browser.open(Capsule.FILE_PATH);
        } catch (IOException e) {
            out.println("❌ Error saving the message: " + e.getMessage());
        }

        out.println("🌟 Thank you for participating in the Craft Advent Calendar! 🌟");
    }
}