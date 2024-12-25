using System.Text;
using TimeCapsule;

var timeCapsule = new Capsule();

Console.OutputEncoding = Encoding.UTF8;
Console.WriteLine("🕰️ Welcome to the Time Capsule 🎅");

if (timeCapsule.HasPastMessage)
{
    Console.WriteLine("\n📜 Message from your past self:");
    Console.WriteLine($"Written on: {timeCapsule.Timestamp}");
    Console.WriteLine($"💌 Message: {timeCapsule.PastMessage}\n");
}
else Console.WriteLine("\n📜 No message from your past self yet.");

Console.Write("✍️  Enter a message for your future self: ");

timeCapsule.SaveMessage(Console.ReadLine() ?? string.Empty);

Console.WriteLine("\n🎉 Your message has been saved and added to the Time Capsule!");
Console.WriteLine("Opening the Time Capsule in your browser...\n");

Browser.Open(Capsule.FilePath);

Console.WriteLine("🌟 Thank you for participating in the Craft Advent Calendar! 🌟");