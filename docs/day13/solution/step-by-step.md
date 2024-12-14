## Day 13: Refactor your code with the Mikado method.
Let's see how we can build our Mikado graph, then how we'll execute it.  

### First sub-goal

To achieve the main goal "Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers", we can start with a first sub-goal: "Prepare the code for an easy addition of the the `X for Y` discount family"

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
  
To achieve this, we need to create an `X for Y` discount computation method

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method

### Scratch Refactoring
To create this method, we can use the [`scratch refactoring`](https://understandlegacycode.com/blog/key-points-of-working-effectively-with-legacy-code/#use-scratch-refactoring-to-get-familiar-with-the-code) technique, 
where we can quickly write some code to explore a design, then discard it at the end.  

In our case, we can do some scratching to build the `Three for two` discount computation method, 
which in turn will help us build a generic `X for Y` discount method and discover its parameters, 
then discard the code at the end.

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch

Here's how we fill the parameters and the contents of the `X for Y` discount type computation method.  

The idea is to start by moving the discount code of `Three for two` in a single if, inside method `handleOffers`. 

To achieve this, we can do the following:

- Create a high level `if(offer.offerType == SpecialOfferType.THREE_FOR_TWO)/else` at the start of the
  method
- Cut the existing code and paste it inside both the if and else blocks
- Clean-up each branch code leveraging on our IDE recommendations
- Separate the computation part from the addition of the discount to the receipt
  
Once done, we can extract the discount computation of `Three for two` type in a method, the extracted method will look like this:

```java
private Discount computeThreeForTwoDiscount(Product p, double quantity, double unitPrice, int quantityAsInt, Discount discount) {
    int x = 3;
    int numberOfXs = quantityAsInt / x;
    if (quantityAsInt > 2) {
      double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
      discount = new Discount(p, "3 for 2", -discountAmount);
    }
    return discount;
}
```

We can remove useless parameters (such as the `Discount` and the `double quantity`) and simplify the method which becomes:

```java
 private Discount computeThreeForTwoDiscount(Product p, int quantity, double unitPrice) {
    int y = 2;
    if (quantity <= 2) {
        return null;
    }
    int x = 3;
    int numberOfXs = quantity / x;
    double discountAmount = unitPrice * (quantity - numberOfXs * y + quantity % x);
    return new Discount(p, x+ " for " + y, -discountAmount);
}
```

Then we write down this method somewhere, and we discard all what we did.
      
### Add unit tests for the `X for Y` discount computation method
We can unit test the newly created `X for Y` discount computation method.

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method
      
It's time to tackle the second sub-goal:

### New sub-goal : Implement the `Two for one` discount computation

We add the new sub-goal to the Mikado graph:

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method
  - [ ] 👍Implement the `Two for one` discount computation

To achieve this, we need to first add a test case testing the `Two for one` discount computation method:

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method
  - [ ] 👍Implement the `Two for one` discount computation
    - [ ] Add test case

Then we can add an `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) {}` in the `santamarket.model.ShoppingSleigh.handleOffers()` method, 
which we fill with an implementation based on the generic `X for Y` computation method.

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method 
  - [ ] 👍Implement the `Two for one` discount computation
    - [ ] Add test case
    - [ ] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`

### Third sub-goal
Last, we can achieve the third goal, which is to refactor the existing `Three for two` discount computation code and replace it with the generic `X for Y` implementation.

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [ ] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method
  - [ ] 👍Implement the `Two for one` discount computation
    - [ ] Add test case
    - [ ] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`
  - [ ] 👍Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount
  
### Mikado graph execution

It's time to execute the Mikado graph that we've just built, starting from the leaves of the graph.   
We start by creating the `X for Y` discount computation method, based on the `Three for two` scratch.  
We can parametrize it with an "XForYOffer" record parameter:

```java
public record XForYOffer(int x, int y) {
}

Discount computeXForYDiscount(XForYOffer offer, Product p, int quantity, double unitPrice) {
  if (quantity <= offer.y())
    return null;
  int numberOfXs = quantity / offer.x();
  double discountAmount = unitPrice * (quantity - numberOfXs * offer.y() + quantity % offer.x());
  return new Discount(p, offer.x() + " for " + offer.y(), -discountAmount);
}
```

In the Mikado graph, we add an [x] for each step we've already completed:
- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [ ] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [ ] Create an `X for Y` discount computation method
      - [x] Add an implementation based on the `Three for two` scratch
      - [ ] Unit test the method
  - [ ] 👍Implement the `Two for one` discount computation
    - [ ] Add test case
    - [ ] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`
  - [ ] 👍Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount

We can move now to adding some unit tests to test the newly created method:
[ComputeDiscountTest.java](../../../solution/day13/src/test/java/santamarket/model/ComputeDiscountTest.java)

When testing, we discovered a bug in these tests : "threeForOneOfferWithFourTeddyBears" and "threeForOneOfferWithEightTeddyBears".  
However, since we are refactoring, we want to keep the behavior as is, even if it is bugged.  
So we disable these failing tests and we add a "Parking-lot" item to the Mikado graph to take care of the bug fixing after we finish the refactoring.

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [x] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [x] Create an `X for Y` discount computation method
      - [x] Add an implementation based on the `Three for two` scratch
      - [x] Unit test the method
  - [ ] 👍Implement the `Two for one` discount computation
    - [ ] Add test case
- [ ] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`
  - [ ] 👍Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount
- [ ] Parking-Lot:
  - [ ] Fix the bug in `X for Y` discount computation method
  
It's time now to tackle the second sub-goal "Implement the `Two for one` discount computation":
We start by adding the new `TWO_FOR_ONE` offer type in [SpecialOfferType.java](../../../solution/day13/src/main/java/santamarket/model/SpecialOfferType.java):

Then we add a new test for this new discount type in [Santamarkettest.java](../../../solution/day13/src/test/java/santamarket/model/SantamarketTest.java):

```java
@Test
void twoForOne() {
  SantamarketCatalog catalog = new FakeCatalog();

  Product teddyBear = new Product("teddyBear", ProductUnit.EACH);
  double teddyBearPrice = 1;
  catalog.addProduct(teddyBear, teddyBearPrice);

  ChristmasElf christmasElf = new ChristmasElf(catalog);
  christmasElf.addSpecialOffer(SpecialOfferType.TWO_FOR_ONE, teddyBear, 0.);

  ShoppingSleigh sleigh = new ShoppingSleigh();
  int numberOfTeddyBears = 4;
  sleigh.addItemQuantity(teddyBear, numberOfTeddyBears);

  // ACT
  Receipt receipt = christmasElf.checksOutArticlesFrom(sleigh);

  // ASSERT
  double expectedNonDiscountedPrice = numberOfTeddyBears*teddyBearPrice;
  double expectedTotalPrice = 2*teddyBearPrice;
  ReceiptItem expectedReceiptItem = new ReceiptItem(teddyBear, numberOfTeddyBears, teddyBearPrice, expectedNonDiscountedPrice);
  Discount expectedDiscount = new Discount(teddyBear, "2 for 1", expectedTotalPrice-expectedNonDiscountedPrice);
  ReceiptMatcher matcher = matches(expectedTotalPrice, List.of(expectedReceiptItem), List.of(expectedDiscount));
  assertThat(receipt, matcher);
}
```

Last, we implement the relevant code in `handleOffers` method:

```java
if (offer.offerType == SpecialOfferType.TWO_FOR_ONE){
  discount=computeXForYDiscount(new XForYOffer(2,1),p,(int)quantity,catalog.getUnitPrice(p));
}
```

Our Mikado graph looks like this now:

- [ ] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [x] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [x] Create an `X for Y` discount computation method
      - [x] Add an implementation based on the `Three for two` scratch
      - [x] Unit test the method
  - [x] 👍Implement the `Two for one` discount computation
    - [x] Add test case
    - [x] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`
  - [ ] 👍Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount
- [ ] Parking-Lot:
  - [ ] Fix the bug in `X for Y` discount computation method
  
It's time now to tackle the third sub-goal "Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount":
This is quite easy, we start by adding this if in `handleOffers` method:

```java
else if (offer.offerType == SpecialOfferType.THREE_FOR_TWO) {
discount = computeXForYDiscount(new XForYOffer(3, 2), p, (int) quantity, catalog.getUnitPrice(p));
}
```

Then we can rely on the IDE to clean the useless remaining code.
Our Mikado graph looks like this now:

- [x] 👍Deploy a generic method to compute the `X for Y` discount offers, covering `Three for two` and `Two for one` offers.
  - [x] 👍Prepare the code for an easy addition of the `X for Y` discount type family
    - [x] Create an `X for Y` discount computation method
      - [x] Add an implementation based on the `Three for two` scratch
      - [x] Unit test the method
  - [x] 👍Implement the `Two for one` discount computation
    - [x] Add test case
    - [x] In `handleOffers`, add `if (offer.offerType == SpecialOfferType.TWO_FOR_ONE) { computeXForYDiscount(2,1, ...) } else { existing implementation }`
  - [x] 👍Refactor the existing code to use the `X for Y` discount computation method with the `Three for two` discount
- [ ] Parking-Lot:
  - [ ] Fix the bug in `X for Y` discount computation method

### Fix the bug in `X for Y` discount computation method
This can be done now since we are done with the main goal. We change the `computeXForYDiscount` code to make `threeForOneOfferWithFourTeddyBears` and `threeForOneOfferWithEightTeddyBears` tests pass.



## Reflect
- How did using the `Mikado method` impact your understanding of the code structure?
- In what ways did the `scratch refactoring` approach help your discovery of parameters and structures for the generic discount method?
- What `similar areas` of complex logic in your own codebase could benefit from a Mikado approach to refactoring?
