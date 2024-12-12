package gifts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class SantaTest {

    private static final Toy PLAYSTATION = new Toy("playstation");
    private static final Toy BALL = new Toy("ball");
    private static final Toy PLUSH = new Toy("plush");


    @Test
    void given_naughty_child_when_distributing_gifts_then_child_receives_third_choice() {
        Child bobby = new Child("bobby", "naughty");
        bobby.setWishList(PLAYSTATION, PLUSH, BALL);
        Santa santa = new Santa();
        santa.addChild(bobby);
        Toy got = santa.chooseToyForChild("bobby");

        Assertions.assertEquals(BALL, got);
    }

    @Test
    void given_nice_child_when_distributing_gifts_then_child_receives_second_choice() {
        Child bobby = new Child("bobby", "nice");
        bobby.setWishList(PLAYSTATION, PLUSH, BALL);
        Santa santa = new Santa();
        santa.addChild(bobby);
        Toy got = santa.chooseToyForChild("bobby");

        Assertions.assertEquals(PLUSH, got);
    }

    @Test
    void given_very_nice_child_when_distributing_gifts_then_child_receives_first_choice() {
        Child bobby = new Child("bobby", "very nice");
        bobby.setWishList(PLAYSTATION, PLUSH, BALL);
        Santa santa = new Santa();
        santa.addChild(bobby);
        Toy got = santa.chooseToyForChild("bobby");

        Assertions.assertEquals(PLAYSTATION, got);
    }

    @Test
    void given_non_existing_child_when_distributing_gifts_then_exception_thrown() {
        Santa santa = new Santa();
        Child bobby = new Child("bobby", "very nice");
        bobby.setWishList(PLAYSTATION, PLUSH, BALL);
        santa.addChild(bobby);

        Assertions.assertThrows(NoSuchElementException.class, () -> santa.chooseToyForChild("alice"));
    }
}
