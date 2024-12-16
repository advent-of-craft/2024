package system;

public class Elf {
    private final int id;
    private int skillLevel;

    public Elf(int id, int skillLevel) {
        this.id = id;
        this.skillLevel = skillLevel;
    }

    public int getId() {
        return id;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}