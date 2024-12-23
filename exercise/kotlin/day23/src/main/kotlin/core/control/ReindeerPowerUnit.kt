package core.control

import external.deer.Reindeer

class ReindeerPowerUnit(val reindeer: Reindeer) {
    private val amplifier = MagicPowerAmplifier(AmplifierType.BASIC)

    fun harnessMagicPower(): Float {
        return if (!reindeer.needsRest()) {
            reindeer.timesHarnessing++
            amplifier.amplify(reindeer.magicPower)
        } else {
            0f
        }
    }

    fun checkMagicPower() = reindeer.magicPower
}
