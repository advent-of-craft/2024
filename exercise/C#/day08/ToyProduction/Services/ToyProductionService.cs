using ToyProduction.Domain;

namespace ToyProduction.Services
{
    public class ToyProductionService(IToyRepository repository)
    {
        public void AssignToyToElf(string toyName)
        {
            var toy = repository.FindByName(toyName);
            if (toy is {State: State.Unassigned})
            {
                toy.State = State.InProduction;
                repository.Save(toy);
            }
        }
    }
}