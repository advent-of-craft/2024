import domain.Toy;
import doubles.InMemoryToyRepository;
import org.junit.jupiter.api.Test;
import service.ToyProductionService;

import static domain.Toy.State.*;
import static org.assertj.core.api.Assertions.assertThat;

class ToyProductionServiceTest {
    private static final String TOY_NAME = "Train";

    @Test
    void assignToyToElfShouldPassTheItemInProduction() {
        var repository = new InMemoryToyRepository();
        var service = new ToyProductionService(repository);
        repository.save(new Toy(TOY_NAME));

        service.assignToyToElf(TOY_NAME);

        assertThat(repository.getUnsafe(TOY_NAME).getState())
                .isEqualTo(IN_PRODUCTION);
    }
}