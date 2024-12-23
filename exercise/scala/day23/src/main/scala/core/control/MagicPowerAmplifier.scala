package core.control

class MagicPowerAmplifier(private val amplifierType: AmplifierType.Value) {
  def amplify(magicPower: Float): Float =
    if (magicPower > 0) magicPower * amplifierType.id else magicPower
}