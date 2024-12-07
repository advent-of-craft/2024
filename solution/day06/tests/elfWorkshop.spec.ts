import {ElfWorkshop} from "../src/elfWorkshop";

describe('ElfWorkshop Tasks', () => {
    // It is good to declare the system under test here
    // Could you change the tests to use this instance everywhere?
    let system: ElfWorkshop;

    beforeEach(() => {
        system = new ElfWorkshop();
    });

    // It seems like there might have been a little mix-up in naming this test.
    // Could we consider a name that mirrors the action being tested, such as 'addTask should include a new task in the taskList'?
    // This could help future maintainers understand the test's purpose at first glance.
    test('removeTask should add a task', () => {
        const workshop = new ElfWorkshop();
        workshop.addTask("Build toy train");
        expect(workshop.taskList).toContain("Build toy train");
    });

    // This test name feels a bit generic. What do you think about giving it a more descriptive title?
    // Something like 'addTask successfully adds a craft dollhouse task to the taskList' might capture its essence more clearly.
    test('test2 checks for task addition', () => {
        const workshop = new ElfWorkshop();
        workshop.addTask("Craft dollhouse");
        expect(workshop.taskList.includes("Craft dollhouse")).toBeTruthy();
    });

    // I noticed this test seems to be a duplicate with a non-descriptive name.
    // Might it be beneficial to either remove the duplication or clarify the unique aspect this test covers?
    // Clear, descriptive test names are like a beacon for understanding and maintaining our test suites.
    test('test2 checks for task addition', () => {
        const workshop = new ElfWorkshop();
        workshop.addTask("Paint bicycle");
        expect(workshop.taskList.includes("Paint bicycle")).toBeTruthy();
    });

    // This test does a good job of covering a crucial validation case.
    // How about renaming it to something like 'addTask does not add empty tasks to the taskList' for more clarity?
    test('Should handle empty tasks correctly', () => {
        const workshop = new ElfWorkshop();
        workshop.addTask("");
        expect(workshop.taskList.length).toBe(0);
    });

    // The functionality tested here is important, and the test itself is well-constructed.
    // To make its purpose even clearer, might we consider a name that directly states the expected outcome,
    // such as 'completeTask removes the first task and returns it'?
    test('Task removal functionality', () => {
        const workshop = new ElfWorkshop();
        workshop.addTask("Wrap gifts");
        const removedTask = workshop.completeTask();
        expect(removedTask).toBe("Wrap gifts");
        expect(workshop.taskList.length).toBe(0);
    });
});