package eid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@With
@Getter
@AllArgsConstructor
public class EIDBuilder {
    private final Sex sex;
    private Year year;
    private SerialNumber serialNumber;
    private int key;

    public EIDBuilder(Sex sex) {
        this.sex = sex;
    }

    static EID toEID(EIDBuilder eidBuilder) {
        return new EID(eidBuilder.getSex(), eidBuilder.getYear(), eidBuilder.getSerialNumber());
    }
}