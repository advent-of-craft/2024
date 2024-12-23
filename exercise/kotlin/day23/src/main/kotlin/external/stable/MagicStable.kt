package external.stable

import external.deer.Reindeer

class MagicStable {
    //Dasher is sick since yesterday
    private val dasher = Reindeer("Dasher", 4, 10, true)
    private val dancer = Reindeer("Dancer", 2, 8)
    private val prancer = Reindeer("Prancer", 3, 9)
    private val vixen = Reindeer("Vixen", 3, 6)

    // Comet is sick as well
    private val comet = Reindeer("Comet", 4, 9, true)
    private val cupid = Reindeer("Cupid", 4, 6)
    private val donner = Reindeer("Donner", 7, 6)
    private val blitzen = Reindeer("Blitzen", 8, 7)
    private val rudolph = Reindeer("Rudolph", 6, 3)

    val allReindeers: List<Reindeer>
        get() = listOf(dasher, dancer, prancer, vixen, comet, cupid, donner, blitzen, rudolph)
}