using System.Collections;
using ControlSystem.External;

namespace ControlSystem.Core;

public class System
{
    private const int XmasSpirit = 40;
    private readonly Dashboard _dashboard;
    private readonly MagicStable _magicStable;
    private readonly List<ReindeerPowerUnit> _reindeerPowerUnits;
    public SleighEngineStatus Status { get; set; }
    public SleighAction Action { get; set; }
    private float _controlMagicPower = 0;

    private readonly Amplifiers _amplifiers = Amplifiers.Build();
    
    public System(MagicStable magicStable)
    {
        _magicStable = magicStable;
        _dashboard = new Dashboard();
        _reindeerPowerUnits = BringAllReindeers();
    }

    private List<ReindeerPowerUnit> BringAllReindeers() =>
        _magicStable
            .GetAllReindeers()
            .OrderByDescending(r => r.GetMagicPower())
            .Select(reindeer => new ReindeerPowerUnit(reindeer,_amplifiers.GetNext()))
            .ToList();

    public void StartSystem()
    {
        _dashboard.DisplayStatus("Starting the sleigh...");
        Status = SleighEngineStatus.On;
        _dashboard.DisplayStatus("System ready.");
    }

    public void Ascend()
    {
        if (Status != SleighEngineStatus.On)
        {
            throw new SleighNotStartedException();
        }
        
        foreach (var reindeerPowerUnit in _reindeerPowerUnits)
        {
            _controlMagicPower += reindeerPowerUnit.HarnessMagicPower();
        }

        if (CheckReindeerStatus())
        {
            _dashboard.DisplayStatus("Ascending...");
            Action = SleighAction.Flying;
            _controlMagicPower = 0;
        }
        else throw new ReindeersNeedRestException();
    }

    public void Descend()
    {
        if (Status == SleighEngineStatus.On)
        {
            _dashboard.DisplayStatus("Descending...");
            Action = SleighAction.Hovering;
        }
        else throw new SleighNotStartedException();
    }

    public void Park()
    {
        if (Status != SleighEngineStatus.On) throw new SleighNotStartedException();
        
        _dashboard.DisplayStatus("Parking...");

        foreach (var reindeerPowerUnit in _reindeerPowerUnits)
        {
            reindeerPowerUnit.Reindeer.TimesHarnessing = 0;
        }

        Action = SleighAction.Parked;
    }

    public void StopSystem()
    {
        _dashboard.DisplayStatus("Stopping the sleigh...");
        Status = SleighEngineStatus.Off;
        _dashboard.DisplayStatus("System shutdown.");
    }

    private bool CheckReindeerStatus()
    {
        return _controlMagicPower >= XmasSpirit;
    }
}