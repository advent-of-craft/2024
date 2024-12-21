using System.Net;
using System.Net.Http.Json;
using System.Text.RegularExpressions;
using FluentAssertions;
using Reindeer.Web.Service;

namespace Reindeer.Web.Tests
{
    public partial class ReindeerContractTests
    {
        private readonly HttpClient _client;
        private readonly VerifySettings _settings;

        [GeneratedRegex(@"[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")]
        private static partial Regex GuidRegex();

        public ReindeerContractTests()
        {
            var webApplication = new ReindeerWebApplicationFactory();
            _client = webApplication.CreateClient();
            _client.DefaultRequestHeaders.Add("API-KEY", Guid.NewGuid().ToString());

            _settings = new VerifySettings();
            _settings.UseDirectory("Verified");
            _settings.AddScrubber(builder =>
            {
                var modifiedContent = GuidRegex().Replace(builder.ToString(), "GUID_VALUE");
                builder.Clear();
                builder.Append(modifiedContent);
            });
        }

        [Fact]
        public async Task ShouldGetReindeer()
        {
            var response = await _client.GetAsync("reindeer/40F9D24D-D3E0-4596-ADC5-B4936FF84B19");

            response.StatusCode.Should().Be(HttpStatusCode.OK);
            await VerifyJson(response.ExtractContent(), _settings);
        }
        
        [Fact]
        public async Task NotFoundForNotExistingReindeer()
        {
            var nonExistingReindeer = Guid.NewGuid().ToString();
            var response = await _client.GetAsync($"reindeer/{nonExistingReindeer}");

            response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        }

        [Fact]
        public async Task ShouldCreateReindeer()
        {
            var request = new ReindeerToCreateRequest("Paolo", ReindeerColor.Black);
            var response = await _client.PostAsync("reindeer", JsonContent.Create(request));

            response.StatusCode.Should().Be(HttpStatusCode.Created);
            await VerifyJson(response.ExtractContent(), _settings);
        }
        
        [Fact]
        public async Task ConflictWhenTryingToCreateExistingOne()
        {
            var request = new ReindeerToCreateRequest("Petar", ReindeerColor.Purple);
            var response = await _client.PostAsync("reindeer", JsonContent.Create(request));

            response.StatusCode.Should().Be(HttpStatusCode.Conflict);
        }
    }
}