// EID test list
// Invalid:
// null
// undefined
// ''
// '1000010' -> too short
// '100001000' -> too long
// '40000100' -> invalid sex
// '4aa00100' -> invalid birth year
// '10000000' -> invalid serial number
// '10000101' -> invalid key
// Valid:
// '19845606'
// '30600233'
// '29999922'
// '11111151'
// '19800767'


import { isEid } from '../src/eid';

describe('EID', () => {
    it('should return false when eid is null', () => {
        expect(isEid(null)).toBe(false);
    });

    it('should return false when eid is undefined', () => {
        expect(isEid(undefined)).toBe(false);
    });

    it('should return false when eid is empty', () => {
        expect(isEid('')).toBe(false);
    });

    it('should return false when eid is too short', () => {
        expect(isEid('1000010')).toBe(false);
    });

    it('should return false when eid is too long', () => {
        expect(isEid('100001000')).toBe(false);
    });

    it('should return false when eid is an invalid sex', () => {
        expect(isEid('40000100')).toBe(false);
    });
});
