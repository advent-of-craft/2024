import {Elf} from "./elf";

export class TaskAssignmentSystem {
    private readonly elves: Elf[] = [];
    private tasksCompleted: number = 0;

    constructor(elves: Elf[]) {
        this.elves = elves.sort((a, b) => a.skillLevel - b.skillLevel);
    }

    assignTask(taskSkillRequired: number): Elf | null {
        return this.elves.find(elf => elf.skillLevel >= taskSkillRequired);
    }

    reassignTask(fromElfId: number, toElfId: number): boolean {
        const fromElf = this.getElfById(fromElfId);
        const toElf = this.getElfById(toElfId);

        if (fromElf && toElf && fromElf.skillLevel > toElf.skillLevel) {
            toElf.skillLevel = fromElf.skillLevel;
            return true;
        }
        return false;
    }

    reportTaskCompletion(elfId: number): boolean {
        if (this.getElfById(elfId)) {
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

    increaseSkillLevel(elfId: number, increment: number): void {
        const elf = this.getElfById(elfId);
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

    private getElfById(fromElfId: number) {
        return this.elves.find(e => e.id === fromElfId);
    }

    listElvesBySkillDescending(): Elf[] {
        return this.elves
            .slice()
            .sort((a, b) => b.skillLevel - a.skillLevel);
    }

    resetAllSkillsToBaseline(baseline: number): void {
        this.elves.forEach(elf => {
            elf.skillLevel = baseline;
        });
    }
}