## Day 12: Collect or not collect?
### Create the `Children` Class
We start by creating a new class, `ChildrenRepository`, that will encapsulate the childrenRepository list.
This class will handle collection-related operations and delegate complex behavior outside the `Santa` class.

Some baby step to handle this transformation: 

- Extract `for` loop to find child into a method in `Santa`.
- Create empty `ChildrenRepository`.
- Implement `ChildrenRepository` class (you can get inspired by the extract method in `Santa`.

```java
public class ChildrenRepository {
    private final List<Child> childrenRepository;

    public ChildrenRepository() {
        this.childrenRepository = new ArrayList<>();
    }

    public void addchild(Child child) {
        childrenRepository.add(child);
    }

    public Child findchild(String childName) {
        Optional<Child> found = Optional.empty();
        for (int i = 0; i < childrenRepository.size(); i++) {
            Child currentChild = childrenRepository.get(i);
            if (currentChild.getName().equals(childName)) {
                found = Optional.of(currentChild);
                break;
            }
        }
        Child child = found.orElseThrow(NoSuchElementException::new);
        return child;
    }
}
```

### Refactor the `Santa` Class to Use `ChildrenRepository`

Now that we have the `ChildrenRepository` class to handle collection logic, we can simplify the `Santa` class by delegating the relevant tasks to the new `ChildrenRepository` class.

Some baby step to handle this transformation:

- Use the `ChildrenRepository` implementation
- Remove old `List<Child> childrenRepository` list.

```java
public class Santa {
    private final ChildrenRepository repository;

    public Santa() {
        this.repository = new ChildrenRepository();
    }

    public Toy chooseToyForChild(String childName) {
        Child child = repository.findChild(childName);

        if ("naughty".equals(child.getBehavior()))
            return child.getThirdChoice();

        if ("nice".equals(child.getBehavior()))
            return child.getSecondChoice();

        if ("very nice".equals(child.getBehavior()))
            return child.getFirstChoice();

        return null;
    }

    public void addchild(Child child) {
        repository.addChild(child);
    }
}
```

### Advantages of the new code:

Encapsulation of the Collection Logic: The `ChildrenRepository` class is now responsible for managing the list of Child objects. 

It hides the details of how children are stored and searched for from the `Santa` class.

Improved Readability: The `Santa` class no longer deals with low-level list operations (e.g., looping through the list). Instead, it uses the `ChildrenRepository` class to find a child and delegate collection-specific responsibilities.

Better Maintainability: If new behaviors related to the collection of children need to be added (like sorting, removing, or filtering children), they can be added to the `ChildrenRepository` class without affecting the `Santa` class.

### Bonus 
Apply first class collection in `Child` class for wishlist that will encapsulate the `List<Toy>`. This class will handle any operations related to the wishlist of toys.

```java
public class Wishlist {
    List<Toy> wishlist;

    public Wishlist() {
    }

    public Toy getFirstChoice () {
        return wishlist.get(0);
    }
    
    public Toy getSecondChoice () {
        return wishlist.get(1);
    }

    public Toy getThirdChoice() {
        return wishlist.get(2);
    }

    public void setWishList(Toy firstChoice, Toy secondChoice, Toy thirdChoice) {
        this.wishlist = List.of(firstChoice, secondChoice, thirdChoice);
    }
}
```

Finally, we can replace `List<Toy>` in `Child` by `Wishlist` implementation. 

### After a few refactorings...
- Move some behaviors closest to their entities
- Create `Behavior` enum
- Add factory method to create a child
  - Make it impossible to instantiate a `Child` without a valid `Wishlist`

```java
public class Santa {
    private final ChildrenRepository childrenRepository;

    public Santa(ChildrenRepository childrenRepository) {
        this.childrenRepository = childrenRepository;
    }

    public Toy chooseToyForChild(String childName) {
        return childrenRepository.findByName(childName)
                .map(Child::chooseToy)
                .orElseThrow(NoSuchElementException::new);
    }
}

// ChildrenRepository is now an interface
public interface ChildrenRepository {
    Optional<Child> addChild(Child child);
    Optional<Child> findByName(String childName);
}
```

We applied some refactorings to improve tests readability:

```java
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

// Test Adapter
public class InMemoryChildrenRepository implements ChildrenRepository {
    private final List<Child> children;

    public InMemoryChildrenRepository() {
        this.children = new ArrayList<>();
    }

    @Override
    public Optional<Child> addChild(Child child) {
        children.add(child);
        return Optional.of(child);
    }

    @Override
    public Optional<Child> findByName(String childName) {
        return children.stream()
                .filter(child -> child.getName().equals(childName))
                .findFirst();
    }
}
```

## Reflect
- How are collections used in your codebase?
- What are the `benefits` and `drawbacks` of using first-class collections?
- How do `first-class collections` affect the `maintainability` of your code?