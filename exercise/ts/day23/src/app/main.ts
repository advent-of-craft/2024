import {ControlSystem} from "../control.core/controlSystem";
import {ReindeersNeedRestException, SleighNotStartedException} from "../control.core/exceptions";

const controlSystem = new ControlSystem();
controlSystem.startSystem();

const readline = require("readline").createInterface({
    input: process.stdin,
    output: process.stdout
});

function askQuestion() {
    readline.question("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ", (command: string) => {
        switch (command) {
            case "ascend":
            case "a":
                try {
                    controlSystem.ascend();
                } catch (e) {
                    if (e instanceof ReindeersNeedRestException || e instanceof SleighNotStartedException) {
                        console.log(e.message);
                    }
                }
                break;
            case "descend":
            case "d":
                try {
                    controlSystem.descend();
                } catch (e) {
                    if (e instanceof SleighNotStartedException) {
                        console.log(e.message);
                    }
                }
                break;
            case "park":
            case "p":
                try {
                    controlSystem.park();
                } catch (e) {
                    if (e instanceof SleighNotStartedException) {
                        console.log(e.message);
                    }
                }
                break;
            case "quit":
            case "q":
                readline.close();
                controlSystem.stopSystem();
                return;
            default:
                console.log("Invalid command. Please try again.");
        }
        askQuestion();
    });
}

askQuestion();
