using Children.Db2;

namespace Children.Tests
{
    public static class X5T78Mother
    {
        public static readonly X5T78 Alice = new()
        {
            Id = Guid.NewGuid().ToString(),
            N_1 = "Alice",
            N_2 = "Marie",
            N_3 = "Smith",
            CityOfBirth__pc = "Paradise",
            Person_BD = "19/03/2017",
            Salutation = "Girl",
            Type_pc = "PPMPX_09/1",
            Serv__Gender__TYPE_pc = "X",
            DeclaredMonthlySalary__c = "0",
            LegalDocumentExpirationDate1__c = "01/09/2030",
            LegalDocumentIssuingCountry1__c = "Paradise",
            LegalDocumentName1__c = "Ali",
            LegalDocumentNumber1__c = "9892389098",
            ST_Num = "123",
            ST____Name = "Sunny Street",
            ST_C = "Paradise",
            ST_CID = "99"
        };

        public static readonly X5T78 Bob = new()
        {
            Id = Guid.NewGuid().ToString(),
            N_1 = "Bob",
            N_3 = "Brown",
            CityOfBirth__pc = "Paradise",
            Person_BD = "01/09/2021",
            Salutation = "Boy",
            Type_pc = "PP0PLX_09/1",
            Serv__Gender__TYPE_pc = "VJX",
            DeclaredMonthlySalary__c = "0",
            LegalDocumentExpirationDate1__c = "12/09/2078",
            LegalDocumentIssuingCountry1__c = "Paradise",
            LegalDocumentName1__c = "Bobby",
            LegalDocumentNumber1__c = "9U129731873191JK",
            ST_Num = "9",
            ST____Name = "Oak Street",
            ST_C = "Paradise",
            ST_CID = "98988"
        };
    }
}