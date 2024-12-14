using Children.Db2;
using Children.DTOs;
using Riok.Mapperly.Abstractions;

namespace Children
{
    [Mapper]
    public partial class ChildMapper
    {
        [MapProperty(nameof(X5T78.N_1), nameof(Child.FirstName))]
        [MapProperty(nameof(X5T78.N_2), nameof(Child.MiddleName))]
        [MapProperty(nameof(X5T78.N_3), nameof(Child.LastName))]
        [MapProperty(nameof(X5T78.CityOfBirth__pc), nameof(Child.BirthCity))]
        [MapProperty(nameof(X5T78.Person_BD), nameof(Child.BirthDate))]
        [MapProperty(nameof(X5T78.Salutation), nameof(Child.Gender))]
        [MapProperty(nameof(X5T78.ST_Num), $"{nameof(Child.Address)}.{nameof(Address.Number)}")]
        [MapProperty(nameof(X5T78.ST____Name), $"{nameof(Child.Address)}.{nameof(Address.Street)}")]
        [MapProperty(nameof(X5T78.ST_C), $"{nameof(Child.Address)}.{nameof(Address.City)}")]
        [MapProperty(nameof(X5T78.ST_CID), $"{nameof(Child.Address)}.{nameof(Address.CountryId)}")]
        public partial Child ToDto(X5T78 child);
    }
}