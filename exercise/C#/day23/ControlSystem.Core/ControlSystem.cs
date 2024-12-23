using System.Collections;
using System.Net;
using ControlSystem.External;

namespace ControlSystem.Core;

public class System(MagicStable magicStable)
{
    private readonly Dashboard _dashboard = new();

    public StartedSystem StartSystem()
    {
        return new StartedSystem(_dashboard, magicStable);
    }
}