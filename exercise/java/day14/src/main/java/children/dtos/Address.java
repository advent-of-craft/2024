package children.dtos;

import lombok.Data;

@Data
public class Address {
    private String number;
    private String street;
    private String city;
    private int countryId;
}