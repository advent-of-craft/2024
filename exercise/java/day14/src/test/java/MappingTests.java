import children.ChildMapper;
import children.db2.X5T78;
import children.dtos.Child;
import children.dtos.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MappingTests {
    private final ChildMapper mapper = ChildMapper.INSTANCE;

    @Test
    void mapX5T78ToGirl() {
        var db2Child = new X5T78();
        db2Child.setId("1");
        db2Child.setN1("Alice");
        db2Child.setN2("Marie");
        db2Child.setN3("Smith");
        db2Child.setCityOfBirth("Paradise");
        db2Child.setPersonBd("19/03/2017");
        db2Child.setSalutation("GIRL");
        db2Child.setTypePc("PPMPX_09/1");
        db2Child.setServGenderTypePc("X");
        db2Child.setDeclaredMonthlySalary("0");
        db2Child.setLegalDocumentExpirationDate("01/09/2030");
        db2Child.setLegalDocumentIssuingCountry("Paradise");
        db2Child.setLegalDocumentName("ID");
        db2Child.setLegalDocumentNumber("9892389098");
        db2Child.setStNum("123");
        db2Child.setStName("Sunny Street");
        db2Child.setStC("Paradise");
        db2Child.setStCid("99");

        Child child = mapper.toDto(db2Child);

        assertThat(child.getId()).isEqualTo("1");
        assertThat(child.getFirstName()).isEqualTo("Alice");
        assertThat(child.getMiddleName()).isEqualTo("Marie");
        assertThat(child.getLastName()).isEqualTo("Smith");
        assertThat(child.getBirthCity()).isEqualTo("Paradise");
        assertThat(child.getBirthDate()).isEqualTo(LocalDate.of(2017, 3, 19));
        assertThat(child.getGender()).isEqualTo(Gender.GIRL);
        assertThat(child.getAddress()).isNotNull();
        assertThat(child.getAddress().getNumber()).isEqualTo("123");
        assertThat(child.getAddress().getStreet()).isEqualTo("Sunny Street");
        assertThat(child.getAddress().getCity()).isEqualTo("Paradise");
        assertThat(child.getAddress().getCountryId()).isEqualTo(99);
    }

    @Test
    void mapX5T78ToBoy() {
        var db2Child = new X5T78();
        db2Child.setId("2");
        db2Child.setN1("Bob");
        db2Child.setN3("Brown");
        db2Child.setCityOfBirth("Paradise");
        db2Child.setPersonBd("01/09/2021");
        db2Child.setSalutation("BOY");
        db2Child.setTypePc("PP0PLX_09/1");
        db2Child.setServGenderTypePc("VJX");
        db2Child.setDeclaredMonthlySalary("0");
        db2Child.setLegalDocumentExpirationDate("12/09/2078");
        db2Child.setLegalDocumentIssuingCountry("Paradise");
        db2Child.setLegalDocumentName("ID");
        db2Child.setLegalDocumentNumber("9U129731873191JK");
        db2Child.setStNum("9");
        db2Child.setStName("Oak Street");
        db2Child.setStC("Paradise");
        db2Child.setStCid("98988");

        Child child = mapper.toDto(db2Child);

        assertThat(child.getId()).isEqualTo("2");
        assertThat(child.getFirstName()).isEqualTo("Bob");
        assertThat(child.getMiddleName()).isNull();
        assertThat(child.getLastName()).isEqualTo("Brown");
        assertThat(child.getBirthCity()).isEqualTo("Paradise");
        assertThat(child.getBirthDate()).isEqualTo(LocalDate.of(2021, 9, 1));
        assertThat(child.getGender()).isEqualTo(Gender.BOY);
        assertThat(child.getAddress()).isNotNull();
        assertThat(child.getAddress().getNumber()).isEqualTo("9");
        assertThat(child.getAddress().getStreet()).isEqualTo("Oak Street");
        assertThat(child.getAddress().getCity()).isEqualTo("Paradise");
        assertThat(child.getAddress().getCountryId()).isEqualTo(98988);
    }
}