import { exec } from 'child_process';
import * as os from 'os';

export class Browser {
    static open(filePath: string): void {
        const platform = os.platform();
        let command: string;

        if (platform === "win32") {
            // Windows
            command = `start "" "${filePath}"`;
        } else if (platform === "darwin") {
            // macOS
            command = `open "${filePath}"`;
        } else if (platform === "linux") {
            // Linux
            command = `xdg-open "${filePath}"`;
        } else {
            console.error("Unsupported platform for opening a browser.");
            return;
        }

        exec(command, (error) => {
            if (error) {
                console.error("Error opening browser:", error);
            }
        });
    }
}
