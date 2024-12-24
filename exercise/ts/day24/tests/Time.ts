export class Time {
    static readonly now = new Date(Date.UTC(2024, 11, 24, 23, 30, 45));
    static provider = () => Time.now;
}