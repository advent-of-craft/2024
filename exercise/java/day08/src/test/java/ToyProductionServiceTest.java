import domain.Toy;
import doubles.InMemoryToyRepository;
import org.junit.jupiter.api.Test;
import service.ToyProductionService;

import static domain.Toy.State.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ToyProductionServiceTest {
    private static final String TOY_NAME = "Train";

    @Test
    void assignToyToElfShouldPassTheItemInProduction() {
        var repository = new InMemoryToyRepository();
        var service = new ToyProductionService(repository);
        repository.save(new Toy(TOY_NAME, UNASSIGNED));

        service.assignToyToElf(TOY_NAME);

        assertThat(repository.findByName(TOY_NAME).getState())
                .isEqualTo(IN_PRODUCTION);
    }
}