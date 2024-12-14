import {X5T78} from "./db2/x5T78";
import {Address, Child, Gender} from "./dtos/child";

export class ChildMapper {
    public static toDto(db2Child: X5T78): Child {
        const child = new Child();
        child.Id = db2Child.Id;
        child.FirstName = db2Child.N_1;
        child.MiddleName = db2Child.N_2;
        child.LastName = db2Child.N_3;
        child.BirthCity = db2Child.CityOfBirth__pc;
        child.BirthDate = db2Child.Person_BD;
        child.Gender = db2Child.Salutation === 'Girl' ? Gender.Girl : Gender.Boy;
        child.Address = new Address();
        child.Address.Number = db2Child.ST_Num;
        child.Address.Street = db2Child.ST____Name;
        child.Address.City = db2Child.ST_C;
        child.Address.CountryId = parseInt(db2Child.ST_CID, 10);

        return child;
    }
}