package reindeer

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

object ReindeerService {
    private var reindeer = listOf(
        Reindeer(UUID.fromString("40f9d24d-d3e0-4596-adc5-b4936ff84b19"), "Petar", ReindeerColor.BLACK)
    )

    fun get(id: UUID): Either<ReindeerErrorCode, Reindeer> =
        reindeer.find { it.id == id }?.right() ?: ReindeerErrorCode.NOT_FOUND.left()

    fun create(reindeerToCreate: ReindeerToCreate): Either<ReindeerErrorCode, Reindeer> =
        if (reindeer.any { it.name == reindeerToCreate.name }) {
            ReindeerErrorCode.ALREADY_EXIST.left()
        } else {
            val newReindeer = Reindeer(UUID.randomUUID(), reindeerToCreate.name, reindeerToCreate.color)
            reindeer = reindeer + newReindeer
            newReindeer.right()
        }
}

@Serializable
data class Reindeer(@Contextual val id: UUID, val name: String, val color: ReindeerColor)

@Serializable
enum class ReindeerColor {
    WHITE, BLACK, PURPLE
}

enum class ReindeerErrorCode {
    NOT_FOUND, ALREADY_EXIST
}

@Serializable
data class ReindeerToCreate(@Contextual val id: UUID, val name: String, val color: ReindeerColor)

@Serializable
data class ReindeerToCreateRequest(val name: String, val color: ReindeerColor) {
    fun toDomain() = ReindeerToCreate(UUID.randomUUID(), name, color)
}
