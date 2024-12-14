package children

import children.db2.X5T78
import children.dtos.Child
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface ChildMapper {
    companion object {
        val INSTANCE: ChildMapper = Mappers.getMapper(ChildMapper::class.java)
    }

    @Mapping(source = "n1", target = "firstName")
    @Mapping(source = "n2", target = "middleName")
    @Mapping(source = "n3", target = "lastName")
    @Mapping(source = "cityOfBirthPc", target = "birthCity")
    @Mapping(source = "personBd", target = "birthDate")
    @Mapping(source = "salutation", target = "gender")
    @Mapping(source = "stNum", target = "address.number")
    @Mapping(source = "stName", target = "address.street")
    @Mapping(source = "stC", target = "address.city")
    @Mapping(source = "stCid", target = "address.countryId")
    fun toDto(child: X5T78): Child
}