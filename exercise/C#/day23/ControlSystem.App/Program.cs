namespace ControlSystem
{
    public static class Program
    {
        static void Main(string[] args)
        {
            var controlSystem = new ControlSystem.Core.System();
            controlSystem.StartSystem();

            var keepRunning = true;

            while (keepRunning)
            {
                Console.WriteLine("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ");
                var command = Console.ReadLine();

                switch (command)
                {
                    case "ascend":
                    case "a":
                        try
                        {
                            controlSystem.Ascend();
                        }
                        catch (ReindeersNeedRestException e)
                        {
                            Console.WriteLine(e.Message);
                        }
                        catch (SleighNotStartedException e)
                        {
                            Console.WriteLine(e.Message);
                        }

                        break;

                    case "descend":
                    case "d":
                        try
                        {
                            controlSystem.Descend();
                        }
                        catch (SleighNotStartedException e)
                        {
                            Console.WriteLine(e.Message);
                        }

                        break;

                    case "park":
                    case "p":
                        try
                        {
                            controlSystem.Park();
                        }
                        catch (SleighNotStartedException e)
                        {
                            Console.WriteLine(e.Message);
                        }

                        break;

                    case "quit":
                    case "q":
                        keepRunning = false;
                        break;

                    default:
                        Console.WriteLine("Invalid command. Please try again.");
                        break;
                }
            }

            controlSystem.StopSystem();
        }
    }
}