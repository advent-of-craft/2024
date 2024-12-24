## Day 23: Understand what is going wrong.

The control system of Santa seems to have an issue. Let's start from the beginning.

> Executing the system

To analyse what is wrong, you have two ways to do it:
- Run the main system through the Main class
- Run the tests to check what fails

### The cause (or causes) of the issue

As we can see, a sick reindeer seems to want to rest all the time, therefore harnessing the power of
a sick reindeer to channel it to the control system is not possible.

### Fix the first issue
We can try to remove the sick reindeer from the `getAllReindeers` to check if the sleigh is flying.

It's not working...

The sleigh is not flying and the reindeer still needs rest. It looks like the sleigh does not have
enough magic power to reach the Xmas spirit.

### Fix the second issue
We are not allowed to change the magic stable in order to add new reindeers but we are allowed to add new amplifiers.

Maybe we could change the type of the amplifier used by the reindeer power unit to a more powerful one.

````java
public class ReindeerPowerUnit {
    public Reindeer reindeer;
    
    //A blessed one maybe?
    public MagicPowerAmplifier amplifier = new MagicPowerAmplifier(AmplifierType.BLESSED);

    public ReindeerPowerUnit(Reindeer reindeer) {
        this.reindeer = reindeer;
    }
    // ...
}
````

Let's run the tests: they are working this time!

### Insure the whole system is working: test everything!
We want to make sure there is no more issues with the control system.

We need to **add the parking tests**.

We can drain all the reindeer power, park and check if it's able to fly.
````java
    @Test
    void testPark() throws ReindeersNeedRestException, SleighNotStartedException {
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.startSystem();

        //we want to drain all the magic power to test the parking
        safeAscendManyTimes(controlSystem, 10);

        controlSystem.park();
        controlSystem.ascend();

        assertTrue(controlSystem.action == SleighAction.FLYING);
        assertEquals(
                """
                        Starting the sleigh...\r
                        System ready.\r
                        Ascending...\r
                        Ascending...\r
                        Ascending...\r
                        Parking...\r
                        Ascending...""",
                outputStreamCaptor.toString().trim());
    }
````

The safeAscendManyTimes method would just capture the exception and let the system run.
Like this: 
````java
    private static void safeAscendManyTimes(ControlSystem controlSystem, int numberOfTimes) throws SleighNotStartedException {
        try {
            for (int i=0;i<numberOfTimes;i++){
                controlSystem.ascend();
            }
        }
        catch(ReindeersNeedRestException e) {
            //we want to continue
        }
    }
````

Not optimal but better than not testing anything!

We also need to add the **tests for stopping the system**.

It could look like this 

```java
    @Test
    void testStop() {
        // The system has been started
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.action = SleighAction.PARKED;
        controlSystem.status = SleighEngineStatus.ON;
        controlSystem.stopSystem();
        assertTrue(controlSystem.status == SleighEngineStatus.OFF);
        assertEquals("""
                        Stopping the sleigh...\r
                        System shutdown.""",
                outputStreamCaptor.toString().trim());
    }
```

### Listing all the issues we can improve

Now that the system is functional, we can list all the issues we see with the code we've gone through:
- The control system is open to many unstable states:
  - We can stop the system while flying
  - We can park in the air
  - We can ascend many times which can drain the reindeer magical power quickly
  - By default, the control system's status is flying.
- The tests are very fragile: tight to the implementation
- We are throwing exceptions all over the place
- The behavior are not in the right component
  - The reset of the harnessing of the power is in the control system for instance.
  - Gathering the harnessed power from the deer is also done in the control system
- The control system class is doing everything
- Some code is not used (checkMagicPower in the ReindeerPowerUnit)
- We are tight to the implementation of the external components.
- Adding an extra command will modify the main function.

### Reading the notice as we are analyzing: an issue can hide another...
Going through the notice, everything seems to be good until the very end.

**_Warning Issue: Christmas town only has one divine amplifier and 2 blessed amplifiers._**

We cannot ship this code as it is since there are not enough amplifiers to fit the system!!
The hotfix we did was attaching a blessed amplifier to each reindeer which is not possible.

Addressing the issue is going to take some time.

Let's see analyse ReindeerPowerUnit class. We could inject the amplifier that way we can choose per reindeer the type of amplifier. 

One quick question: _should I unit test the reindeer class or is it enough to check the control system?_

We will, for now, never use the reindeer power unit outside the control system so the minimum tests are sufficient.

In order to keep from adding breaking changes, we can add a second constructor.

```java
    public MagicPowerAmplifier amplifier;

    public ReindeerPowerUnit(Reindeer reindeer) {
        this(reindeer, new MagicPowerAmplifier(AmplifierType.BLESSED));
    }

    public ReindeerPowerUnit(Reindeer reindeer, MagicPowerAmplifier amplifier) {
        this.reindeer = reindeer;
        this.amplifier = amplifier;
    }
```

Now, we have a choice between injecting the power unit searching by names but it will require to know which reindeer has the most power. 
Besides, what we really want is to automatically assign the most powerful amplifier to the reindeer that has the most magical power.

Let's do it quick and change the bringAllReindeers method to add the divine amplifier and the 2 blessed amplifers.

````java
    private List<ReindeerPowerUnit> bringAllReindeers() {
        var allReindeerByMagicalPower = magicStable.getAllReindeers()
                .stream()
                .sorted(Comparator.comparingDouble(Reindeer::getMagicPower).reversed())
                .toList();

        return allReindeerByMagicalPower.stream()
                .map(reindeer -> {
                    int index = allReindeerByMagicalPower.indexOf(reindeer);
                    if (index == 0) {
                        return new ReindeerPowerUnit(reindeer, new MagicPowerAmplifier(AmplifierType.DIVINE));
                    } else if (index == 1 || index == 2) {
                        return new ReindeerPowerUnit(reindeer, new MagicPowerAmplifier(AmplifierType.BLESSED));
                    } else {
                        return new ReindeerPowerUnit(reindeer);
                    }
                }).toList();
    }
````

Not great but all tests are green and it fits the number of available amplifiers!
And with this, no matter which reindeer gets sick, it will figure out which reindeer has the most magical power and dispatch the amplifiers accordingly.

We can optimize this code a bit by using a map in order to manage the available amplifiers.

````java
    private static final Map<Integer, AmplifierType> availableAmplifierByMagicalPower = new HashMap<>();

    static {
        availableAmplifierByMagicalPower.put(1, AmplifierType.DIVINE);
        availableAmplifierByMagicalPower.put(2, AmplifierType.BLESSED);
        availableAmplifierByMagicalPower.put(3, AmplifierType.BLESSED);
    }

    private List<ReindeerPowerUnit> bringAllReindeers() {
        var allReindeerByMagicalPower = magicStable.getAllReindeers()
                .stream()
                .sorted(Comparator.comparingDouble(Reindeer::getMagicPower).reversed())
                .toList();

        return allReindeerByMagicalPower
                .stream()
                .map(reindeer -> attachPowerUnit(reindeer, allReindeerByMagicalPower.indexOf(reindeer) + 1))
                .toList();
    }

    private ReindeerPowerUnit attachPowerUnit(Reindeer reindeer, int indexOfMagicalPower) {
        return generatePowerUnit(reindeer, availableAmplifierByMagicalPower.getOrDefault(indexOfMagicalPower, AmplifierType.BASIC));
    }

    public ReindeerPowerUnit generatePowerUnit(Reindeer reindeer, AmplifierType amplifierToAttach) {
        return new ReindeerPowerUnit(reindeer, new MagicPowerAmplifier(amplifierToAttach));
    }
````

The code is more understandable but several other underlying issues remain.

Now, let's look at the big picture. 
What are the things that are likely to happen? So we can look at the code differently.

- Other reindeer getting sick seems like a strong possibility.
- A change in the available amplifiers.
- Adding a new young reindeer to the stable.
- Every year, reindeer gets older therefore we need to check if the system is usable. 
  - As of now we have to turn on the system try if flying works, not optimal.
- The required magical power to fly might change if we optimize the engine later.
- What if we add new commands with different magic power (stealth, super speed, etc)
- Knowing our magical power left and the available commands.
- More overall security. Not being able to trigger a command that will throw off the system
  - Turn off the system when the sleigh is flying for instance.

### Securing the system using TDD

We need to make sure the system is usable before we can turn it on.

Let's try by adding a message if the system is usable when we turn it on.

We want to emulate the system is not usable.
Right now, we have no way to test this as the reindeer are initialize inside the control system.

Let's add a constructor in the control system to inject the reindeer so the test will be as follows.

````java
    @Test
    void shouldNotBeUsableIfItCannotHoldEnoughMagicPower() {
        List<Reindeer> noReindeer = List.of();

        // The system has been started
        ControlSystem controlSystem = new ControlSystem(noReindeer);
        controlSystem.action = SleighAction.PARKED;
        controlSystem.status = SleighEngineStatus.OFF;

        controlSystem.startSystem();

        assertTrue(controlSystem.status == SleighEngineStatus.ON);
        assertEquals("""
                        Starting the sleigh...\r
                        System is not usable. It needs more magical power.""",
                outputStreamCaptor.toString().trim());
    }
````

Let's add the constructor and adapt the private method.

````java
    public ControlSystem() {
        this.dashboard = new Dashboard();
        this.reindeerPowerUnits = bringAllReindeers(magicStable.getAllReindeers());
    }

    public ControlSystem(List<Reindeer> reindeer) {
        this.dashboard = new Dashboard();
        this.reindeerPowerUnits = bringAllReindeers(reindeer);
    }
````

🔴 The code now compiles and the tests is failing. Great starting point!

We can add a condition in the startSystem method to check if power of all units is enough to reach the Xmas Spirit

🟢 Tests should be green! After a few refactoring, we have this.

````java
    public void startSystem() {
        dashboard.displayStatus("Starting the sleigh...");

        status = SleighEngineStatus.ON;

        dashboard.displayStatus(
                hasEnoughMagicalPowerToReach(XmasSpirit)
                        ? "System ready."
                        : "System is not usable. It needs more magical power.");
    }

    private boolean hasEnoughMagicalPowerToReach(int magicalPowerNeeded) {
        return reindeerPowerUnits
                .stream()
                .mapToDouble(ReindeerPowerUnit::checkMagicPower)
                .sum() >= magicalPowerNeeded;
    }
````

It's all good but in reality, we want to go one step further.
The system should not turn on if not usable. Therefore, no commands can be executed.

Let's adapt the test and make the correct behavior emerge.

````java
    @Test
    void shouldNotBeUsableIfItCannotHoldEnoughMagicPower() {
        //Not enough reindeer to power the sleigh
        List<Reindeer> noReindeer = List.of();

        ControlSystem controlSystem = new ControlSystem(noReindeer);
        controlSystem.action = SleighAction.PARKED;
        controlSystem.status = SleighEngineStatus.OFF;

        controlSystem.startSystem();

        assertSame(controlSystem.status, SleighEngineStatus.OFF);
        assertEquals("""
                        Starting the sleigh...\r
                        System is not usable. It needs more magical power.""",
                outputStreamCaptor.toString().trim());
    }
````

To finish with securing the control system, we don't want the control system to set its own status by default. 
The sleigh should always be parked and the control system should be off when initializing it.

Let's adapt the test and adjust new constructor to add the change.

````java
    @Test
    void shouldNotBeUsableIfItCannotHoldEnoughMagicPower() {
        //Not enough reindeer to power the sleigh
        List<Reindeer> noReindeer = List.of();

        ControlSystem controlSystem = new ControlSystem(noReindeer);

        controlSystem.startSystem();

        assertSame(controlSystem.status, SleighEngineStatus.OFF);
        assertEquals("""
                        Starting the sleigh...\r
                        System is not usable. It needs more magical power.""",
                outputStreamCaptor.toString().trim());
    }
````

````java
    public ControlSystem(List<Reindeer> reindeer) {
        this.status = SleighEngineStatus.OFF;
        this.action = SleighAction.PARKED;
        this.dashboard = new Dashboard();
        this.reindeerPowerUnits = bringAllReindeers(reindeer);
    }
````

Now the control system only turns on if there is enough magical power. We are making progress.

### Starting to break down the control system many responsibilities

The control system class has too many responsibilities.

- It controls the overall system
- It executes the commands 
- It also initiates the power units.
- It harnesses the magical power directly from the reindeer (not the power units)
- It resets its own magical power and the magical power of the reindeer.
- It manages the available amplifiers.

Let's see how we can optimize that.

What does the control system do? 
It executes the commands and interacts with the sleigh and the reindeer power units system.

Let's see if we could add a component to prepare the reindeer power units given a list of special amplifiers and reindeer.
This component will just need to be called from the control system to generate the reindeer power units.

Let's adapt the start the system test to reflect the change using a new constructor.

````java
    @Test
    void shouldStart() {
            var availableAmplifierByMagicalPower = Map.of(
            1, AmplifierType.DIVINE,
            2, AmplifierType.BLESSED,
            3, AmplifierType.BLESSED);

            var factory = new PowerUnitFactory(new MagicStable().getAllReindeers(), availableAmplifierByMagicalPower);

            ControlSystem controlSystem = new ControlSystem(factory);

            controlSystem.startSystem();

            assertTrue(controlSystem.status == SleighEngineStatus.ON);
            }
````

We create the factory class with an interface with, for now, a simple initialisation of the power unit.

````java
public interface PowerUnitFactory {
    List<ReindeerPowerUnit> bringAllReindeers();
}
````

````java
public class BestMagicalPerformancePowerUnitFactory implements PowerUnitFactory {
  private final List<Reindeer> allReindeers;
  private Map<Integer, AmplifierType> availableAmplifierByMagicalPower;

  public PowerUnitFactory(List<Reindeer> allReindeers, Map<Integer, AmplifierType> availableAmplifierByMagicalPower) {
    this.allReindeers = allReindeers;
    this.availableAmplifierByMagicalPower = availableAmplifierByMagicalPower;
  }

  public List<ReindeerPowerUnit> bringAllReindeers() {
    return allReindeers.stream()
            .map(reindeer -> new ReindeerPowerUnit(reindeer,
                    new MagicPowerAmplifier(AmplifierType.BASIC)))
            .toList();
  }
}
````

Tests are passing. Hm, we agreed to never trust a test that is not failing first.

It looks like the logic to check the magic power is flawed... it does not take into account the amplifier.

Let's correct this for now.

````java
    public float harnessMagicPower() {
        var harnessedPower = checkMagicPower();

        if (harnessedPower != 0){
            reindeer.timesHarnessing++;
        }

        return harnessedPower;
    }

    public float checkMagicPower() {
        if (!reindeer.needsRest()) {
            return amplifier.amplify(reindeer.getMagicPower());
        }

        return 0;
    }
````

We haven't refactored the ReindeerPowerUnit class yet but this code is going to do for now.

🔴 The test is now failing! Time to change the code in the factory.
We can easily copy / paste all the code from the control system to the factory.

````java
    @Override
    public List<ReindeerPowerUnit> bringAllReindeers() {
        return bringAllReindeers(allReindeers);
    }

    private List<ReindeerPowerUnit> bringAllReindeers(List<Reindeer> allReindeers) {
        var allReindeerByMagicalPower = allReindeers
                .stream()
                .sorted(Comparator.comparingDouble(Reindeer::getMagicPower).reversed())
                .toList();

        return allReindeerByMagicalPower
                .stream()
                .map(reindeer -> attachPowerUnit(reindeer, allReindeerByMagicalPower.indexOf(reindeer) + 1))
                .toList();
    }

    private ReindeerPowerUnit attachPowerUnit(Reindeer reindeer, int indexOfMagicalPower) {
        return generatePowerUnit(reindeer, availableAmplifierByMagicalPower.getOrDefault(indexOfMagicalPower, AmplifierType.BASIC));
    }

    public ReindeerPowerUnit generatePowerUnit(Reindeer reindeer, AmplifierType amplifierToAttach) {
        return new ReindeerPowerUnit(reindeer, new MagicPowerAmplifier(amplifierToAttach));
    }
````

🟢 The test is now green! Good job.

I now want to remove all the constructors that does not fit the bill in the control system.

Let's adapt the tests and the code.
Only the stop test is failing because of the console output since we now need to start the system before stopping.

Let's correct it and see how it looks.

Note: we added a factory method in the tests to create the control system.

````java
    @Test
    void shouldStop() {
        // The system has been started
        var controlSystem = generateControlSystemUsing(
                new BestMagicalPerformancePowerUnitFactory(
                        getAvailableReindeer(),
                        getAvailableAmplifiers())
        );

        controlSystem.startSystem();

        controlSystem.stopSystem();

        assertTrue(controlSystem.isOff());
        assertEquals("""
                        Starting the sleigh...\r
                        System ready.\r
                        Stopping the sleigh...\r
                        System shutdown.""",
                outputStreamCaptor.toString().trim());
    }
````

We can remove our constructor we used to inject the reindeer.
However, we still have the Main app that uses the default constructor.

Let's adapt this. We can run the application to see if the commands are still working.

````java
    //...
    public static MagicStable magicStable = new MagicStable();

    private static Map<Integer, AmplifierType> getAvailableAmplifiers() {
        return Map.of(
                1, AmplifierType.DIVINE,
                2, AmplifierType.BLESSED,
                3, AmplifierType.BLESSED);
    }

    public static void main(String[] args){
        ControlSystem controlSystem = new ControlSystem(
            new BestMagicalPerformancePowerUnitFactory(
                magicStable.getAllReindeers(),
                getAvailableAmplifiers())
        );
        controlSystem.startSystem();
        //...
    }
//...

````

We can now remove the last constructor and insure we are in a safe state.
Last but not least, we remove a dependency from the external classes (stable)

Let's go further and remove all references to the external classes.
We can clean up the copied code to prepare the reindeer.
We can also clean up the code for the available amplifiers.

The code is much simpler to understand.

### One step further, always one Object Calisthenics rule.

We can use one of the Object Calisthenics tip we learned during the journey. The First Class collection to centralize the logic to gather magical power.

Let's go ahead and create a class for it.

````java
public class MagicConcentrator {
    private final List<ReindeerPowerUnit> reindeerPowerUnits;

    public MagicConcentrator(List<ReindeerPowerUnit> reindeerPowerUnits) {
        this.reindeerPowerUnits = reindeerPowerUnits;
    }

    public boolean hasEnoughMagicalPowerToReach(int magicalPowerNeeded) {
        return reindeerPowerUnits
                .stream()
                .mapToDouble(ReindeerPowerUnit::checkMagicPower)
                .sum() >= magicalPowerNeeded;
    }

    public int harnessAllMagicPower(){
        return reindeerPowerUnits.stream()
                .mapToInt(reindeerPowerUnit -> (int) reindeerPowerUnit.harnessMagicPower())
                .sum();
    }

    public void rechargeMagicPower(){
        // The reindeer rests so the times to harness his magic power resets
        reindeerPowerUnits.forEach(ReindeerPowerUnit::recharge);
    }
}
````

The tests are still passing.

### Remove the dependency to the console output

One of the big issue in the test and in the code is how the code is tighly coupled to the console.

Let's fix this by adding an interface to the dashboard first and inject a ConsoleDashboard.

````java
public interface Dashboard {
    void displayStatus(String message);
}
````

We inject the dashboard into the constructor of the control system

````java
    public ControlSystem(PowerUnitFactory factory, Dashboard dashboard) {
        this.status = SleighEngineStatus.OFF;
        this.action = SleighAction.PARKED;
        this.dashboard = dashboard;
        this.magicConcentrator = new MagicConcentrator(factory.bringAllReindeers());
    }
````

We adapt the factory method in the tests

````java
    private ControlSystem generateControlSystemUsing(PowerUnitFactory powerUnitFactory) {
        return new ControlSystem(
                powerUnitFactory,
                new ConsoleDashboard());
    }
````

And the main application

````java
        ControlSystem controlSystem = new ControlSystem(
                new BestMagicalPerformancePowerUnitFactory(
                        magicStable.getAllReindeers(),
                        getAvailableAmplifiers()),
                new ConsoleDashboard()
        );
````

The tests are still passing! Let's continue.

We can now add a new implementation of the dashboard in the tests to stop sniffing the console output

````java
public class DashboardForTest implements Dashboard {
  StringBuilder allMessages = new StringBuilder();

  @Override
  public void displayStatus(String message) {
    addNewLineWith(message);
  }

  private void addNewLineWith(String message) {
    allMessages.append(message).append("\r\n");
  }

  public String getAllMessages() {
    return allMessages.toString().trim();
  }
}
````

And adapt the factory

````java
    private DashboardForTest dashboardForTest = new DashboardForTest(); 

    private ControlSystem generateControlSystemUsing(PowerUnitFactory powerUnitFactory) {
        return new ControlSystem(
                powerUnitFactory,
                dashboardForTest);
    }
````

All tests are green!

### Stop throwing exceptions.

We want to remove the exception, let's see how to do it with monadic structure.

We can use Either from the vavr library.

Let's start by adding a missing test and see how it would look when asserting the message.

````java
    @Test
    void shouldNotDescendIfSystemIsNotOn() {
        var controlSystem = generateControlSystemUsing(
                new BestMagicalPerformancePowerUnitFactory(
                        getAvailableReindeer(),
                        getAvailableAmplifiers())
        );

        controlSystem.descend()
                .peek(v -> Assertions.fail("should not succeed"))
                .peekLeft(e -> {
                    assertTrue(controlSystem.isOff());
                    assertEquals(e, "Sleigh not started");
                });
    }
````

Let's adapt the code in the control system and add a method to wrap the condition of failure.

````java
    public Either<String, Float> descend() {
        return whenSystemIsOn(() -> {
            dashboard.displayStatus("Descending...");
            action = SleighAction.HOVERING;

            return Either.right(controlMagicPower);
        });
    }

    private <T> Either<String, T> whenSystemIsOn(Supplier<Either<String, T>> actionSupplier) {
        if (isOn()) {
            return actionSupplier.get();
        } else {
            return Either.left("Sleigh not started");
        }
    }
````

Here for now, we are returning the magic power left but we can figure out later exactly the relevant type of information.

Let's now change all our tests and code as well as the main application.

````java
public static void main(String[] args) {
        ControlSystem controlSystem = new ControlSystem(
                new BestMagicalPerformancePowerUnitFactory(
                        magicStable.getAllReindeers(),
                        getAvailableAmplifiers()),
                new ConsoleDashboard()
        );
        controlSystem.startSystem();

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ");
            String command = scanner.nextLine();

            switch (command) {
                case "ascend": case "a":
                    controlSystem.ascend()
                            .peek(v -> System.out.println("Magic power left: " + v))
                            .peekLeft(System.out::println);
                    break;
                case "descend":  case "d":
                    controlSystem.descend()
                            .peek(v -> System.out.println("Magic power left: " + v))
                            .peekLeft(System.out::println);
                    break;
                case "park": case "p":
                    controlSystem.park()
                            .peek(v -> System.out.println("Magic power left: " + v))
                            .peekLeft(System.out::println);
                    break;
                case "quit": case "q":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
        controlSystem.stopSystem();
    }
````

### Bonus Refactoring

#### Leveraging the monadic structure of the control system code to pipeline the code.

Using the Either monad, we can go further and add a series of control along the way to have a code that looks like a pipeline.

Here is a look at a possible solution using a pipeline approach.

```java
public Either<ActionFailure, ActionSuccess> startSystem() {
    return when(sleigh::engineIsOff,
            this::checkEnoughPowerToStart,
            SystemErrors.SLEIGH_ALREADY_STARTED);
}

private Either<ActionFailure, ActionSuccess> checkEnoughPowerToStart() {
    return when(magicConcentrator.hasEnoughPowerToReach(SystemConfig.XMAS_SPIRIT),
            this::safeStart,
            SystemErrors.NOT_ENOUGH_MAGICAL_POWER);
}

private Either<ActionFailure, ActionSuccess> safeStart() {
    logout("Starting the sleigh...");

    return sleigh.turnOn()
            .flatMap(this::checkSystemIsOn)
            .mapLeft(e -> e);
}

private Either<ActionFailure, ActionSuccess> checkSystemIsOn(ActionSuccess actionResult) {
    return when(sleigh::engineIsOn,
            sendConfirmationAfterSuccess(actionResult, SystemMessage.SLEIGH_HAS_STARTED),
            SystemErrors.SLEIGH_DID_NOT_START);
}

private Either<ActionFailure, ActionSuccess> when(BooleanSupplier condition, Supplier<Either<ActionFailure, ActionSuccess>> action, String errorMessage) {
    return condition.getAsBoolean()
            ? action.get()
            : ActionFailure.because(errorMessage);
}
```

This can highlight the control made to avoid all the bad states. 
The system has to have enough power to start, after turn on the system, we check the sleigh is indeed on and return a success message.

We can use the pipeline approach to handle all the bad states.

### Time to reflect

- What was your approach to the high-pressure bug fixing situation?
- How could you improve your everyday approach?
- How could we have avoided this situation?
- Which tools you found useful since the beginning of the journey in this challenge?
