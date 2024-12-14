package children;

import children.db2.X5T78;
import children.dtos.Child;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChildMapper {
    ChildMapper INSTANCE = Mappers.getMapper(ChildMapper.class);

    @Mapping(source = "n1", target = "firstName")
    @Mapping(source = "n2", target = "middleName")
    @Mapping(source = "n3", target = "lastName")
    @Mapping(source = "cityOfBirth", target = "birthCity")
    @Mapping(source = "personBd", target = "birthDate", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "salutation", target = "gender")
    @Mapping(source = "stNum", target = "address.number")
    @Mapping(source = "stName", target = "address.street")
    @Mapping(source = "stC", target = "address.city")
    @Mapping(source = "stCid", target = "address.countryId")
    Child toDto(X5T78 child);
}