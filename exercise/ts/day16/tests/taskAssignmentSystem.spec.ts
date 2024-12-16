import {TaskAssignmentSystem} from "../src/taskAssignmentSystem";
import {Elf} from "../src/elf";

describe('TaskAssignmentSystem', () => {
    let system: TaskAssignmentSystem;

    beforeEach(() => {
        const elves = [new Elf(1, 5), new Elf(2, 10), new Elf(3, 20)];
        system = new TaskAssignmentSystem(elves);
    });

    test('reportTaskCompletion increases total tasks completed', () => {
        expect(system.reportTaskCompletion(1)).toBeTruthy();
        expect(system.getTotalTasksCompleted()).toBe(1);
    });

    test('getElfWithHighestSkill returns the correct elf', () => {
        const highestSkillElf = system.getElfWithHighestSkill();
        expect(highestSkillElf?.id).toBe(3);
    });

    test('assignTask assigns an elf based on skill level', () => {
        expect(system.assignTask(8)).toStrictEqual(new Elf(2, 10));
    });

    test('increaseSkillLevel updates elf skill correctly', () => {
        system.increaseSkillLevel(1, 3);
        const elf = system.assignTask(7);
        expect(elf?.id).toBe(1);
    });

    test('decreaseSkillLevel updates elf skill correctly', () => {
        system.decreaseSkillLevel(1, 3);
        system.decreaseSkillLevel(2, 5);

        const elf = system.assignTask(4);
        expect(elf?.id).toBe(2);
        expect(elf?.skillLevel).toBe(5);
    });

    test('assignTaskBasedOnAvailability assigns an available elf', () => {
        const elf = system.assignTaskBasedOnAvailability(10);
        expect(elf).not.toBeNull();
    });

    test('reassignTask changes assignment correctly', () => {
        system.reassignTask(3, 1);
        const elf = system.assignTask(19);
        expect(elf?.id).toBe(1);
    });

    test('assignTask fails when skills required is too high', () => {
        expect(system.assignTask(50)).toBeUndefined();
    });

    test('listElvesBySkillDescending returns elves in correct order', () => {
        const sortedElves = system.listElvesBySkillDescending();
        expect(sortedElves.map(elf => elf.id)).toEqual([3, 2, 1]);
    });

    test('resetAllSkillsToBaseline resets all elves skills to a specified baseline', () => {
        system.resetAllSkillsToBaseline(10);
        const elves = system.listElvesBySkillDescending();
        elves.forEach(elf => expect(elf.skillLevel).toBe(10));
    });

    test('decreaseSkillLevel updates elf skill and do not allow negative values', () => {
        system.decreaseSkillLevel(1, 10);
        const elf = system.assignTask(4);
        expect(elf?.id).toBe(1);
        expect(elf?.skillLevel).toBe(5);
    });
});