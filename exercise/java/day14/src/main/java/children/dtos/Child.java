package children.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Child {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthCity;
    private LocalDate birthDate;
    private Gender gender;
    private Address address;
}