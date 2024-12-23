using ControlSystem.External;

namespace ControlSystem.Core;

public class StartedSystem
{
    private Dashboard _dashboard;
    private const int XmasSpirit = 40;
    private readonly List<ReindeerPowerUnit> _reindeerPowerUnits;
    private readonly MagicStable _magicStable;
    private readonly XmasTownAmplifiers _xmasTownAmplifiers = XmasTownAmplifiers.Build();
    public SleighAction Action { get; set; } = SleighAction.Flying;
    private float _controlMagicPower = 0;
    
    private List<ReindeerPowerUnit> BringAllReindeers() =>
        _magicStable
            .GetAllReindeers()
            .OrderByDescending(r => r.GetMagicPower())
            .Select(reindeer => new ReindeerPowerUnit(reindeer,_xmasTownAmplifiers.GetNext()))
            .ToList();

    public StartedSystem(Dashboard dashboard, MagicStable magicStable)
    {
        _dashboard = dashboard;
        _magicStable = magicStable;
        
        _dashboard.DisplayStatus("Starting the sleigh...");
        _reindeerPowerUnits = BringAllReindeers();
        _dashboard.DisplayStatus("System ready.");
    }
    
    public void Ascend()
    {
        if (!HasEnoughMagicPower()) throw new ReindeersNeedRestException();
        
        _dashboard.DisplayStatus("Ascending...");
        Action = SleighAction.Flying;
    }
    
    public void Descend()
    {   
        _dashboard.DisplayStatus("Descending...");
        Action = SleighAction.Hovering;
    }

    public void Park()
    {
        _dashboard.DisplayStatus("Parking...");

        foreach (var reindeerPowerUnit in _reindeerPowerUnits)
        {
            reindeerPowerUnit.Reindeer.TimesHarnessing = 0;
        }

        Action = SleighAction.Parked;
    }

    private bool HasEnoughMagicPower()
    {
        foreach (var reindeerPowerUnit in _reindeerPowerUnits)
        {
            _controlMagicPower += reindeerPowerUnit.HarnessMagicPower();
        }
        bool status = _controlMagicPower >= XmasSpirit;

        _controlMagicPower = 0;
        return status;
    }
    
    public void StopSystem()
    {
        _dashboard.DisplayStatus("Stopping the sleigh...");
        _dashboard.DisplayStatus("System shutdown.");
    }
}