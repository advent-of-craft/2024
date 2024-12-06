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


describe('EID', () => {
    test('implement your tests here', () => {
        expect(43).toBe(42);
    });
});
