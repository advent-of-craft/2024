import { EmailService } from '../../src/routine/emailService';

export class EmailServiceDouble implements EmailService {
    private _readNewEmailsCalled: boolean = false;

    get readNewEmailsCalled(): boolean {
        return this._readNewEmailsCalled;
    }
    readNewEmails() {
        this._readNewEmailsCalled = true;
    }
}
