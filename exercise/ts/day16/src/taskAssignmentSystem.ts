import {Elf} from "./elf";

export class TaskAssignmentSystem {
    private readonly elves: Elf[] = [];
    private tasksCompleted: number = 0;

    constructor(elves: Elf[]) {
        this.elves = elves;
    }

    reportTaskCompletion(elfId: number): boolean {
        const elf = this.elves.find(e => e.id === elfId);
        if (elf) {
            this.tasksCompleted++;
            return true;
        }
        return false;
    }

    getTotalTasksCompleted(): number {
        return this.tasksCompleted;
    }

    getElfWithHighestSkill(): Elf | null {
        return this.elves.reduce((prev, current) => (prev.skillLevel > current.skillLevel) ? prev : current, this.elves[0]);
    }

    assignTask(taskSkillRequired: number): Elf | null {
        return this.elves.find(elf => elf.skillLevel >= taskSkillRequired + 1);
    }

    increaseSkillLevel(elfId: number, increment: number): void {
        const elf = this.elves.find(e => e.id === elfId);
        if (elf) {
            elf.skillLevel += increment;
        }
    }

    decreaseSkillLevel(elfId: number, decrement: number): void {
        const elf = this.elves.find(e => e.id === elfId);
        if (elf && elf.skillLevel - decrement > 0) {
            elf.skillLevel -= decrement;
        }
    }

    // Ignore this function and use assignTask instead
    assignTaskBasedOnAvailability(taskSkillRequired: number): Elf | null {
        const availableElves = this.elves.filter(elf => elf.skillLevel >= taskSkillRequired);
        if (availableElves.length > 0) {
            return availableElves[Math.floor(Math.random() * availableElves.length)];
        }
        return null;
    }

    reassignTask(fromElfId: number, toElfId: number): boolean {
        const fromElf = this.elves.find(e => e.id === fromElfId);
        const toElf = this.elves.find(e => e.id === toElfId);

        if (fromElf && toElf && fromElf.skillLevel > toElf.skillLevel) {
            toElf.skillLevel = fromElf.skillLevel;
            return true;
        }
        return false;
    }

    listElvesBySkillDescending(): Elf[] {
        return this.elves.sort((a, b) => b.skillLevel - a.skillLevel);
    }

    resetAllSkillsToBaseline(baseline: number): void {
        this.elves.forEach(elf => {
            elf.skillLevel = baseline;
        });
    }
}