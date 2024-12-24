package core.control;

import java.util.function.BooleanSupplier;

public interface PowerGenerator {
    BooleanSupplier hasEnoughPowerToReach(int powerNeeded);

    int harnessAllPower();

    void rechargePower();
}
