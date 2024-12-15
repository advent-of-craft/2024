using Children.Db2;

namespace Children.Tests;

public class MappingTests
{
    private readonly ChildMapper _mapper = new();
    private readonly VerifySettings _settings;

    public MappingTests()
    {
        _settings = new VerifySettings();
        _settings.DontScrubDateTimes();
    }

    [Fact]
    public async Task Map_X5T78_To_Girl()
    {
        var db2Child = new X5T78
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
            LegalDocumentName1__c = "ID",
            LegalDocumentNumber1__c = "9892389098",
            ST_Num = "123",
            ST____Name = "Sunny Street",
            ST_C = "Paradise",
            ST_CID = "99"
        };

        var child = _mapper.ToDto(db2Child);

        await Verify(child, _settings);
    }

    [Fact]
    public async Task Map_X5T78_To_Child_For_A_Boy()
    {
        var db2Child = new X5T78
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
            LegalDocumentName1__c = "ID",
            LegalDocumentNumber1__c = "9U129731873191JK",
            ST_Num = "9",
            ST____Name = "Oak Street",
            ST_C = "Paradise",
            ST_CID = "98988"
        };

        var child = _mapper.ToDto(db2Child);
        await Verify(child, _settings);
    }

    public class Failures
    {
        private readonly ChildMapper _mapper = new();
        [Fact]
        public void Map_X5T78_To_Child_For_A_Boy_With_Null_DateOfBirth()
        {
            var db2Child = new X5T78
            {
                Id = Guid.NewGuid().ToString(),
                N_1 = "Bob",
                N_3 = "Brown",
                CityOfBirth__pc = "Paradise",
                Person_BD = "",
                Salutation = "Boy",
                Type_pc = "PP0PLX_09/1",
                Serv__Gender__TYPE_pc = "VJX",
                DeclaredMonthlySalary__c = "0",
                LegalDocumentExpirationDate1__c = "12/09/2078",
                LegalDocumentIssuingCountry1__c = "Paradise",
                LegalDocumentName1__c = "ID",
                LegalDocumentNumber1__c = "9U129731873191JK",
                ST_Num = "9",
                ST____Name = "Oak Street",
                ST_C = "Paradise",
                ST_CID = "98988"
            };

            var act = () => _mapper.ToDto(db2Child);
            act
                .Should()
                .Throw<ArgumentException>()
                .WithMessage("Date of birth is required");
        }
    }
}