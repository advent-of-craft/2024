package reindeer

import arrow.core.Either.Left
import arrow.core.Either.Right
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            serializersModule = SerializersModule {
                contextual(UUID::class, UUIDSerializer)
            }
        })
    }

    routing {
        route("/reindeer") {
            post {
                val request = call.receive<ReindeerToCreateRequest>()
                when (val result = ReindeerService.create(request.toDomain())) {
                    is Left -> call.respond(HttpStatusCode.Conflict)
                    is Right -> call.respond(HttpStatusCode.Created, result.value)
                }
            }
            get("/{id}") {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID")
                    return@get
                }
                when (val result = ReindeerService.get(id)) {
                    is Left -> call.respond(HttpStatusCode.NotFound)
                    is Right -> call.respond(result.value)
                }
            }
        }
    }
}