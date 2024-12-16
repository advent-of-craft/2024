package system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskAssignment {
    private final List<Elf> elves = new ArrayList<>();
    private int tasksCompleted = 0;

    public TaskAssignment(List<Elf> elves) {
        this.elves.addAll(elves);
    }

    public boolean reportTaskCompletion(int elfId) {
        var elf = elves.stream()
                .filter(e -> e.getId() == elfId)
                .findFirst();

        if (elf.isPresent()) {
            tasksCompleted++;
            return true;
        }
        return false;
    }

    public int getTotalTasksCompleted() {
        return tasksCompleted;
    }

    public Elf getElfWithHighestSkill() {
        return elves.stream()
                .max(Comparator.comparingInt(Elf::getSkillLevel))
                .orElse(null);
    }

    public Elf assignTask(int taskSkillRequired) {
        return elves.stream()
                .filter(elf -> elf.getSkillLevel() >= taskSkillRequired + 1)
                .findFirst()
                .orElse(null);
    }

    public void increaseSkillLevel(int elfId, int increment) {
        elves.stream()
                .filter(e -> e.getId() == elfId)
                .findFirst()
                .ifPresent(elf -> elf.setSkillLevel(elf.getSkillLevel() + increment));
    }

    public void decreaseSkillLevel(int elfId, int decrement) {
        elves.stream()
                .filter(e -> e.getId() == elfId)
                .findFirst()
                .ifPresent(elf -> {
                    if (elf.getSkillLevel() - decrement > 0) {
                        elf.setSkillLevel(elf.getSkillLevel() - decrement);
                    }
                });
    }

    // Ignore this method and use assignTask instead
    public Elf assignTaskBasedOnAvailability(int taskSkillRequired) {
        var availableElves = new ArrayList<Elf>();
        for (Elf elf : elves) {
            if (elf.getSkillLevel() >= taskSkillRequired) {
                availableElves.add(elf);
            }
        }
        if (!availableElves.isEmpty()) {
            var randomIndex = (int) (Math.random() * availableElves.size());
            return availableElves.get(randomIndex);
        }
        return null;
    }

    public boolean reassignTask(int fromElfId, int toElfId) {
        var fromElf = elves.stream().filter(e -> e.getId() == fromElfId).findFirst();
        var toElf = elves.stream().filter(e -> e.getId() == toElfId).findFirst();

        if (fromElf.isPresent() && toElf.isPresent() && fromElf.get().getSkillLevel() > toElf.get().getSkillLevel()) {
            toElf.get().setSkillLevel(fromElf.get().getSkillLevel());
            return true;
        }
        return false;
    }

    public List<Elf> listElvesBySkillDescending() {
        elves.sort(Comparator.comparingInt(Elf::getSkillLevel).reversed());
        return new ArrayList<>(elves);
    }

    public void resetAllSkillsToBaseline(int baseline) {
        for (Elf elf : elves) {
            elf.setSkillLevel(baseline);
        }
    }
}
