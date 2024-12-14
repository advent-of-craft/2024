using Children.Db2;
using FluentAssertions;
using Xunit;
using static Children.DTOs.Gender;

namespace Children.Tests
{
    public class MappingTests
    {
        private readonly ChildMapper _mapper = new();

        [Fact]
        public void Map_X5T78_To_Girl()
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

            child.Id.Should().Be(Guid.Parse(db2Child.Id));
            child.FirstName.Should().Be(db2Child.N_1);
            child.MiddleName.Should().Be(db2Child.N_2);
            child.LastName.Should().Be(db2Child.N_3);
            child.BirthCity.Should().Be(db2Child.CityOfBirth__pc);
            child.BirthDate.Should().Be(new DateOnly(2017, 3, 19));
            child.Gender.Should().Be(Girl);
            child.Address.Should().NotBeNull();
            child.Address?.Number.Should().Be(db2Child.ST_Num);
            child.Address?.Street.Should().Be(db2Child.ST____Name);
            child.Address?.City.Should().Be(db2Child.ST_C);
            child.Address?.CountryId.Should().Be(99);
        }

        [Fact]
        public void Map_X5T78_To_Child_For_A_Boy()
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

            child.Id.Should().Be(Guid.Parse(db2Child.Id));
            child.FirstName.Should().Be(db2Child.N_1);
            child.MiddleName.Should().Be(db2Child.N_2);
            child.LastName.Should().Be(db2Child.N_3);
            child.BirthCity.Should().Be(db2Child.CityOfBirth__pc);
            child.BirthDate.Should().Be(new DateOnly(2021, 9, 1));
            child.Gender.Should().Be(Boy);
            child.Address.Should().NotBeNull();
            child.Address?.Number.Should().Be(db2Child.ST_Num);
            child.Address?.Street.Should().Be(db2Child.ST____Name);
            child.Address?.City.Should().Be(db2Child.ST_C);
            child.Address?.CountryId.Should().Be(98988);
        }
    }
}