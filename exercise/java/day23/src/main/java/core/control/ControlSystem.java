package core.control;

import external.deer.Reindeer;
import external.stable.MagicStable;

import java.util.List;

public class ControlSystem {
    //The Xmas spirit is 40 magic power unit
    private int XmasSpirit = 40;
    private Dashboard dashboard;
    private MagicStable magicStable = new MagicStable();
    private List<ReindeerPowerUnit> reindeerPowerUnits;
    public SleighEngineStatus status;
    public SleighAction action;
    private float controlMagicPower = 0;

    public ControlSystem() {
        this.dashboard = new Dashboard();
        this.reindeerPowerUnits = bringAllReindeers();
    }

    private List<ReindeerPowerUnit> bringAllReindeers() {
        return magicStable.getAllReindeers().stream().map(this::attachPowerUnit).toList();
    }

    public ReindeerPowerUnit attachPowerUnit(Reindeer reindeer) {
        return new ReindeerPowerUnit(reindeer);
    }

    public void startSystem() {
        dashboard.displayStatus("Starting the sleigh...");

        status = SleighEngineStatus.ON;
        dashboard.displayStatus("System ready.");
    }

    public void ascend() throws ReindeersNeedRestException, SleighNotStartedException {
        if (status == SleighEngineStatus.ON) {
            for (ReindeerPowerUnit reindeerPowerUnit : reindeerPowerUnits) {
                controlMagicPower += reindeerPowerUnit.harnessMagicPower();
            }

            if (checkReindeerStatus()) {
                dashboard.displayStatus("Ascending...");
                action = SleighAction.FLYING;
                controlMagicPower = 0;
            } else throw new ReindeersNeedRestException();
        } else {
            throw new SleighNotStartedException();
        }
    }

    public void descend() throws SleighNotStartedException {
        if (status == SleighEngineStatus.ON) {
            dashboard.displayStatus("Descending...");
            action = SleighAction.HOVERING;
        }
        else throw new SleighNotStartedException();
    }

    public void park() throws SleighNotStartedException {
        if (status == SleighEngineStatus.ON) {
            dashboard.displayStatus("Parking...");

            for (ReindeerPowerUnit reindeerPowerUnit : reindeerPowerUnits) {
                // The reindeer rests so the times to harness his magic power resets
                reindeerPowerUnit.reindeer.timesHarnessing = 0;
            }

            action = SleighAction.PARKED;
        }
        else throw new SleighNotStartedException();
    }

    public void stopSystem() {
        dashboard.displayStatus("Stopping the sleigh...");

        status = SleighEngineStatus.OFF;
        dashboard.displayStatus("System shutdown.");
    }

    private boolean checkReindeerStatus() {
        return controlMagicPower >= XmasSpirit;
    }
}

