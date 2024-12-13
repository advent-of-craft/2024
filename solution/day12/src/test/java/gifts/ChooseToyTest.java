package gifts;

import gifts.fakes.InMemoryChildrenRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChooseToyTest {
    private static final Toy PLAYSTATION = new Toy("playstation");
    private static final Toy PLUSH = new Toy("plush");
    private static final Toy BALL = new Toy("ball");

    private final Faker faker = new Faker();

    private Santa santa;
    private InMemoryChildrenRepository childrenRepository;

    @BeforeEach
    void setup() {
        childrenRepository = new InMemoryChildrenRepository();
        santa = new Santa(childrenRepository);
    }

    @Test
    void given_naughty_child_when_distributing_gifts_then_child_receives_third_choice() {
        assertThat(chooseToyForChildWhoHasBeen(Behavior.NAUGHTY))
                .isEqualTo(BALL);
    }

    @Test
    void given_nice_child_when_distributing_gifts_then_child_receives_second_choice() {
        assertThat(chooseToyForChildWhoHasBeen(Behavior.NICE))
                .isEqualTo(PLUSH);
    }

    @Test
    void given_very_nice_child_when_distributing_gifts_then_child_receives_first_choice() {
        assertThat(chooseToyForChildWhoHasBeen(Behavior.VERY_NICE))
                .isEqualTo(PLAYSTATION);
    }

    @Test
    void given_non_existing_child_when_distributing_gifts_then_exception_thrown() {
        assertThatThrownBy(() -> santa.chooseToyForChild("non existing child"))
                .isInstanceOf(NoSuchElementException.class);
    }

    private Toy chooseToyForChildWhoHasBeen(Behavior behavior) {
        // Acceptable in test to use get method as we are sure that the child will be created
        var child = Child.create(faker.name().firstName(), behavior, PLAYSTATION, PLUSH, BALL).get();

        return childrenRepository
                .addChild(child)
                .map(c -> santa.chooseToyForChild(c.getName()))
                .orElseThrow();
    }
}
