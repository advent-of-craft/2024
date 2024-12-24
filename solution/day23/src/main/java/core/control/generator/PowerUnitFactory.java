package core.control.generator;

import io.vavr.collection.Seq;

public interface PowerUnitFactory {
    Seq<ReindeerPowerUnit> bringAllReindeers();
}
