package external.stable

import external.deer.Reindeer

class MagicStable {
  // Dasher is sick since yesterday
  private val dasher = new Reindeer("Dasher", 4, 10, true)
  private val dancer = new Reindeer("Dancer", 2, 8)
  private val prancer = new Reindeer("Prancer", 3, 9)
  private val vixen = new Reindeer("Vixen", 3, 6)
  // Comet is sick as well
  private val comet = new Reindeer("Comet", 4, 9, true)
  private val cupid = new Reindeer("Cupid", 4, 6)
  private val donner = new Reindeer("Donner", 7, 6)
  private val blitzen = new Reindeer("Blitzen", 8, 7)
  private val rudolph = new Reindeer("Rudolph", 6, 3)

  def getAllReindeers: List[Reindeer] = {
    List(dasher, dancer, prancer, vixen, comet, cupid, donner, blitzen, rudolph)
  }
}