package santaChristmasList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import santaChristmasList.operations.Business;
import santaChristmasList.operations.dependencies.Factory;
import santaChristmasList.operations.dependencies.Inventory;
import santaChristmasList.operations.dependencies.WishList;
import santaChristmasList.operations.models.Child;
import santaChristmasList.operations.models.Gift;
import santaChristmasList.operations.models.ManufacturedGift;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessTests {

    private static final String BAR_CODE = "123";

    private Factory factory;
    private Inventory inventory;
    private WishList wishList;

    private Child john;
    private Gift toy;
    private ManufacturedGift manufacturedGift;

    @BeforeEach
    public void setup() {
        factory = new Factory();
        inventory = new Inventory();
        wishList = new WishList();

        john = new Child("John");
        toy = new Gift("Toy");
        manufacturedGift = new ManufacturedGift(BAR_CODE);
    }

    @Test
    public void gift_ShouldBeLoadedIntoSleigh() {
        wishList.put(john, toy);
        factory.put(toy, manufacturedGift);
        inventory.put(BAR_CODE, toy);

        var business = new Business(factory, inventory, wishList);
        var sleigh = business.loadGiftsInSleigh(john);

        assertThat(sleigh.get(john)).isEqualTo("Gift: Toy has been loaded!");
    }

    @Test
    public void gift_ShouldNotBeLoaded_GivenChildIsNotOnWishList() {
        var business = new Business(factory, inventory, wishList);
        var sleigh = business.loadGiftsInSleigh(john);

        assertThat(sleigh.containsKey(john)).isFalse();
    }

    @Test
    public void gift_ShouldNotBeLoaded_GivenToyWasNotManufactured() {
        wishList.put(john, toy);

        var business = new Business(factory, inventory, wishList);
        var sleigh = business.loadGiftsInSleigh(john);

        assertThat(sleigh.containsKey(john)).isFalse();
    }

    @Test
    public void gift_ShouldNotBeLoaded_GivenToyWasMisplaced() {
        wishList.put(john, toy);
        factory.put(toy, manufacturedGift);

        var business = new Business(factory, inventory, wishList);
        var sleigh = business.loadGiftsInSleigh(john);

        assertThat(sleigh.containsKey(john)).isFalse();
    }
}
