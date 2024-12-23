package core.control

import external.deer.Reindeer
import external.stable.MagicStable

class ControlSystem {
  // The Xmas spirit is 40 magic power units
  private val xmasSpirit = 40
  private val dashboard = new Dashboard()
  private val magicStable = new MagicStable()
  private val reindeerPowerUnits = bringAllReindeers()
  var status: SleighEngineStatus.Value = SleighEngineStatus.OFF
  var action: SleighAction.Value = SleighAction.PARKED
  private var controlMagicPower = 0f

  def startSystem(): Unit = {
    dashboard.displayStatus("Starting the sleigh...")
    status = SleighEngineStatus.ON
    dashboard.displayStatus("System ready.")
  }

  @throws[ReindeersNeedRestException]
  @throws[SleighNotStartedException]
  def ascend(): Unit = {
    if (status == SleighEngineStatus.ON) {
      reindeerPowerUnits.foreach(reindeerPowerUnit => {
        controlMagicPower += reindeerPowerUnit.harnessMagicPower()
      })

      if (checkReindeerStatus()) {
        dashboard.displayStatus("Ascending...")
        action = SleighAction.FLYING
        controlMagicPower = 0
      } else throw new ReindeersNeedRestException()
    } else {
      throw new SleighNotStartedException()
    }
  }

  private def checkReindeerStatus(): Boolean = controlMagicPower >= xmasSpirit

  @throws[SleighNotStartedException]
  def descend(): Unit = {
    if (status == SleighEngineStatus.ON) {
      dashboard.displayStatus("Descending...")
      action = SleighAction.HOVERING
    } else throw new SleighNotStartedException()
  }

  @throws[SleighNotStartedException]
  def park(): Unit = {
    if (status == SleighEngineStatus.ON) {
      dashboard.displayStatus("Parking...")

      reindeerPowerUnits.foreach(reindeerPowerUnit => {
        reindeerPowerUnit.reindeer.timesHarnessing = 0
      })

      action = SleighAction.PARKED
    } else throw new SleighNotStartedException()
  }

  def stopSystem(): Unit = {
    dashboard.displayStatus("Stopping the sleigh...")
    status = SleighEngineStatus.OFF
    dashboard.displayStatus("System shutdown.")
  }

  private def bringAllReindeers(): List[ReindeerPowerUnit] = {
    magicStable.getAllReindeers.map(attachPowerUnit)
  }

  def attachPowerUnit(reindeer: Reindeer): ReindeerPowerUnit = new ReindeerPowerUnit(reindeer)
}