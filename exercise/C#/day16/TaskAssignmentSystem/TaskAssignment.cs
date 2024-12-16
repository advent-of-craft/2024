namespace TaskAssignmentSystem
{
    public class TaskAssignment(IEnumerable<Elf> elves)
    {
        public bool ReportTaskCompletion(int elfId)
        {
            var elf = elves.FirstOrDefault(e => e.Id == elfId);
            if (elf != null)
            {
                TotalTasksCompleted++;
                return true;
            }

            return false;
        }

        public int TotalTasksCompleted { get; private set; }

        public Elf ElfWithHighestSkill()
            => elves.Aggregate((prev, current) => prev.SkillLevel > current.SkillLevel ? prev : current);

        public Elf AssignTask(int taskSkillRequired)
            => elves.FirstOrDefault(elf => elf.SkillLevel >= taskSkillRequired + 1);

        public void IncreaseSkillLevel(int elfId, int increment)
        {
            var elf = elves.FirstOrDefault(e => e.Id == elfId);
            if (elf != null)
            {
                elf.SkillLevel += increment;
            }
        }

        public void DecreaseSkillLevel(int elfId, int decrement)
        {
            var elf = elves.FirstOrDefault(e => e.Id == elfId);
            if (elf != null && elf.SkillLevel - decrement > 0)
            {
                elf.SkillLevel -= decrement;
            }
        }

        // Ignore this function and use AssignTask instead
        public Elf AssignTaskBasedOnAvailability(int taskSkillRequired)
        {
            var availableElves = elves.Where(elf => elf.SkillLevel >= taskSkillRequired).ToList();
            if (availableElves.Any())
            {
                var random = new Random();
                return availableElves[random.Next(availableElves.Count)];
            }

            return null;
        }

        public bool ReassignTask(int fromElfId, int toElfId)
        {
            var fromElf = elves.FirstOrDefault(e => e.Id == fromElfId);
            var toElf = elves.FirstOrDefault(e => e.Id == toElfId);

            if (fromElf != null && toElf != null && fromElf.SkillLevel > toElf.SkillLevel)
            {
                toElf.SkillLevel = fromElf.SkillLevel;
                return true;
            }

            return false;
        }

        public List<Elf> ElvesBySkillDescending() => elves.OrderByDescending(e => e.SkillLevel).ToList();

        public void ResetAllSkillsToBaseline(int baseline)
        {
            foreach (var elf in elves)
            {
                elf.SkillLevel = baseline;
            }
        }
    }
}