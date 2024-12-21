using System.Text.Encodings.Web;
using System.Text.Json;

namespace Reindeer.Web.Tests;

public static class HttpExtensions
{
    public static async Task<string> ExtractContent(this HttpResponseMessage response)
    {
        var responseContent = response.RequestMessage?.Content is null
            ? ""
            : await response.RequestMessage.Content!.ReadAsStringAsync();

        var content = new
        {
            Request = new
            {
                response.RequestMessage!.Headers,
                response.RequestMessage.Method,
                RequestUri = response.RequestMessage.RequestUri!.PathAndQuery,
                response.RequestMessage.Version,
                Content = responseContent
            },
            Response = new
            {
                response.StatusCode,
                Content = await response.Content.ReadAsStringAsync()
            }
        };

        return JsonSerializer.Serialize(
            content,
            options: new JsonSerializerOptions
                {WriteIndented = true, Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping}
        );
    }
}