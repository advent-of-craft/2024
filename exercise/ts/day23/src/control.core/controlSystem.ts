import {Dashboard} from "./dashboard";
import {MagicStable} from "../external/stable/magicStable";
import {ReindeerPowerUnit} from "./reindeerPowerUnit";
import {SleighAction, SleighEngineStatus} from "./enums";
import {Reindeer} from "../external/deer/reindeer";
import {ReindeersNeedRestException, SleighNotStartedException} from "./exceptions";

export class ControlSystem {
    private XmasSpirit = 40;
    private dashboard: Dashboard;
    private magicStable = new MagicStable();
    private reindeerPowerUnits: ReindeerPowerUnit[];
    public status: SleighEngineStatus;
    public action: SleighAction;
    private controlMagicPower = 0;

    constructor() {
        this.dashboard = new Dashboard();
        this.reindeerPowerUnits = this.bringAllReindeers();
    }

    private bringAllReindeers(): ReindeerPowerUnit[] {
        return this.magicStable.getAllReindeers().map(reindeer => this.attachPowerUnit(reindeer));
    }

    public attachPowerUnit(reindeer: Reindeer): ReindeerPowerUnit {
        return new ReindeerPowerUnit(reindeer);
    }

    public startSystem() {
        this.dashboard.displayStatus("Starting the sleigh...");
        this.status = SleighEngineStatus.ON;
        this.dashboard.displayStatus("System ready.");
    }

    public ascend() {
        if (this.status === SleighEngineStatus.ON) {
            this.reindeerPowerUnits.forEach(unit => {
                this.controlMagicPower += unit.harnessMagicPower();
            });

            if (this.checkReindeerStatus()) {
                this.dashboard.displayStatus("Ascending...");
                this.action = SleighAction.FLYING;
                this.controlMagicPower = 0;
            } else {
                throw new ReindeersNeedRestException();
            }
        } else {
            throw new SleighNotStartedException();
        }
    }

    public descend() {
        if (this.status === SleighEngineStatus.ON) {
            this.dashboard.displayStatus("Descending...");
            this.action = SleighAction.HOVERING;
        } else {
            throw new SleighNotStartedException();
        }
    }

    public park() {
        if (this.status === SleighEngineStatus.ON) {
            this.dashboard.displayStatus("Parking...");

            this.reindeerPowerUnits.forEach(unit => {
                unit.reindeer.timesHarnessing = 0;
            });

            this.action = SleighAction.PARKED;
        } else {
            throw new SleighNotStartedException();
        }
    }

    public stopSystem() {
        this.dashboard.displayStatus("Stopping the sleigh...");
        this.status = SleighEngineStatus.OFF;
        this.dashboard.displayStatus("System shutdown.");
    }

    private checkReindeerStatus(): boolean {
        return this.controlMagicPower >= this.XmasSpirit;
    }
}