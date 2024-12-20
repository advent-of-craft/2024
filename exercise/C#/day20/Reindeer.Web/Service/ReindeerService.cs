using LanguageExt;

namespace Reindeer.Web.Service
{
    public class ReindeerService
    {
        // Reindeer is same for plural
        private Seq<Reindeer> _reindeer = new List<Reindeer>()
            {new(Guid.Parse("40F9D24D-D3E0-4596-ADC5-B4936FF84B19"), "Petar", ReindeerColor.Black)}.ToSeq();

        public Either<ReindeerErrorCode, Reindeer> Get(Guid id)
            => _reindeer.Find(r => r.Id == id)
                .ToEither(ReindeerErrorCode.NotFound);

        public Either<ReindeerErrorCode, Reindeer> Create(ReindeerToCreate reindeerToCreate)
            => _reindeer.Find(r => r.Name == reindeerToCreate.Name)
                .ToEither(() => CreateAndAddReindeer(reindeerToCreate))
                .Swap()
                .MapLeft(_ => ReindeerErrorCode.AlreadyExist);

        private Reindeer CreateAndAddReindeer(ReindeerToCreate reindeerToCreate)
            => new Reindeer(Guid.NewGuid(), reindeerToCreate.Name, reindeerToCreate.Color)
                .Apply(it =>
                {
                    _reindeer = _reindeer.Add(it);
                    return it;
                });
    }
}