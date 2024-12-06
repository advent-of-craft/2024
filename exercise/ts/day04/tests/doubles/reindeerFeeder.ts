import { ReindeerFeeder } from '../../src/routine/reindeerFeeder';

export class ReindeerFeederDouble implements ReindeerFeeder {
    private _feedReindeersCalled = false;
    get feedReindeersCalled() {
        return this._feedReindeersCalled;
    }
    feedReindeers() {
        this._feedReindeersCalled = true;
    }
}
