namespace TaskAssignmentSystem;

public class Elf(int id, int skillLevel)
{
    public int Id { get; } = id;
    public int SkillLevel { get; set; } = skillLevel;
}