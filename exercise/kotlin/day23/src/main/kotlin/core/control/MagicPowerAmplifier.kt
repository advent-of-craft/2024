package core.control

class MagicPowerAmplifier(private val amplifierType: AmplifierType) {
    fun amplify(magicPower: Float): Float {
        return if (magicPower > 0) magicPower * amplifierType.multiplier else magicPower
    }
}