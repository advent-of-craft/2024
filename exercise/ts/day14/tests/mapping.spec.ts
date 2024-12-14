import {X5T78} from "../src/children/db2/x5T78";
import {Child, Gender} from "../src/children/dtos/child";
import {ChildMapper} from "../src/children/childMapper";

describe('ChildMapper', () => {
    it('should map X5T78 to Child (Girl)', () => {
        const db2Child: X5T78 = {
            Id: '1',
            N_1: 'Alice',
            N_2: 'Marie',
            N_3: 'Smith',
            CityOfBirth__pc: 'Paradise',
            Person_BD: '2017-03-19',
            Salutation: 'Girl',
            Type_pc: 'PPMPX_09/1',
            Serv__Gender__TYPE_pc: 'X',
            DeclaredMonthlySalary__c: '0',
            LegalDocumentExpirationDate1__c: '2030-09-01',
            LegalDocumentIssuingCountry1__c: 'Paradise',
            LegalDocumentName1__c: 'ID',
            LegalDocumentNumber1__c: '9892389098',
            ST_Num: '123',
            ST____Name: 'Sunny Street',
            ST_C: 'Paradise',
            ST_CID: '99'
        };

        const child: Child = ChildMapper.toDto(db2Child);

        expect(child.Id).toBe(db2Child.Id);
        expect(child.FirstName).toBe(db2Child.N_1);
        expect(child.MiddleName).toBe(db2Child.N_2);
        expect(child.LastName).toBe(db2Child.N_3);
        expect(child.BirthCity).toBe(db2Child.CityOfBirth__pc);
        expect(child.BirthDate).toBe('2017-03-19');
        expect(child.Gender).toBe(Gender.Girl);
        expect(child.Address).toBeDefined();
        expect(child.Address?.Number).toBe(db2Child.ST_Num);
        expect(child.Address?.Street).toBe(db2Child.ST____Name);
        expect(child.Address?.City).toBe(db2Child.ST_C);
        expect(child.Address?.CountryId).toBe(99);
    });

    it('should map X5T78 to Child (Boy)', () => {
        const db2Child: X5T78 = {
            Id: '2',
            N_1: 'Bob',
            N_2: '',
            N_3: 'Brown',
            CityOfBirth__pc: 'Paradise',
            Person_BD: '2021-09-01',
            Salutation: 'Boy',
            Type_pc: 'PP0PLX_09/1',
            Serv__Gender__TYPE_pc: 'VJX',
            DeclaredMonthlySalary__c: '0',
            LegalDocumentExpirationDate1__c: '2078-09-12',
            LegalDocumentIssuingCountry1__c: 'Paradise',
            LegalDocumentName1__c: 'ID',
            LegalDocumentNumber1__c: '9U129731873191JK',
            ST_Num: '9',
            ST____Name: 'Oak Street',
            ST_C: 'Paradise',
            ST_CID: '98988'
        };

        const child: Child = ChildMapper.toDto(db2Child);

        expect(child.Id).toBe(db2Child.Id);
        expect(child.FirstName).toBe(db2Child.N_1);
        expect(child.MiddleName).toBe(db2Child.N_2);
        expect(child.LastName).toBe(db2Child.N_3);
        expect(child.BirthCity).toBe(db2Child.CityOfBirth__pc);
        expect(child.BirthDate).toBe('2021-09-01');
        expect(child.Gender).toBe(Gender.Boy);
        expect(child.Address).toBeDefined();
        expect(child.Address?.Number).toBe(db2Child.ST_Num);
        expect(child.Address?.Street).toBe(db2Child.ST____Name);
        expect(child.Address?.City).toBe(db2Child.ST_C);
        expect(child.Address?.CountryId).toBe(98988);
    });
});
