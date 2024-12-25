import { Capsule } from "./capsule";
import { Browser } from "./browser";

const readline = require("readline");

(async function main() {
    const capsule = new Capsule();

    console.log("🕰️ Welcome to the Time Capsule 🎅");

    if (capsule.hasPastMessage) {
        console.log("\n📜 Message from your past self:");
        console.log(`Written on: ${capsule.timestamp}`);
        console.log(`💌 Message: ${capsule.pastMessage}\n`);
    } else {
        console.log("\n📜 No message from your past self yet.");
    }

    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout,
    });

    rl.question("✍️  Enter a message for your future self: ", (message: string) => {
        capsule.saveMessage(message);
        console.log("\n🎉 Your message has been saved and added to the Time Capsule!");
        console.log("Opening the Time Capsule in your browser...\n");

        Browser.open(Capsule.filePath);

        console.log("🌟 Thank you for participating in the Craft Advent Calendar! 🌟");
        rl.close();
    });
})();