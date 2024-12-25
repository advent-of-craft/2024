import * as fs from "fs";
import * as path from "path";

export class Capsule {
    private readonly templatePath = "timecapsule_template.html";
    public static readonly filePath = "timecapsule.html";

    public pastMessage: string | null = null;
    public timestamp: Date | null = null;
    public hasPastMessage: boolean = false;

    constructor() {
        this.hasPastMessage = this.loadPastMessage();
    }

    private loadPastMessage(): boolean {
        if (!fs.existsSync(Capsule.filePath)) {
            return false;
        }

        const htmlContent = fs.readFileSync(Capsule.filePath, "utf-8");
        const startIndex = htmlContent.indexOf("<!--MESSAGE_START-->");
        const endIndex = htmlContent.indexOf("<!--MESSAGE_END-->");

        if (startIndex === -1 || endIndex === -1) {
            return false;
        }

        this.pastMessage = htmlContent.substring(startIndex + 18, endIndex).trim();
        this.timestamp = fs.statSync(Capsule.filePath).mtime;

        return true;
    }

    public saveMessage(message: string): void {
        if (!fs.existsSync(this.templatePath)) {
            throw new Error("HTML template file not found!");
        }

        const templateContent = fs.readFileSync(this.templatePath, "utf-8");
        const filledContent = templateContent
            .replace(/{{message}}/g, message) // Replace all occurrences of {{message}}
            .replace(
                "{{timestamp}}",
                new Date().toLocaleString("en-US", { dateStyle: "long", timeStyle: "short" })
            );

        fs.writeFileSync(Capsule.filePath, filledContent, "utf-8");

        this.timestamp = new Date();
        this.pastMessage = message;
    }
}
