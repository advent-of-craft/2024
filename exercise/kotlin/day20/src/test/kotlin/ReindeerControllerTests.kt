package reindeer.web

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import reindeer.ReindeerColor
import reindeer.ReindeerToCreateRequest
import reindeer.module
import java.util.*

class ReindeerContractTests : StringSpec({
    "GET existing reindeer" {
        testApplication {
            application {
                module()
            }
            client.get("/reindeer/40f9d24d-d3e0-4596-adc5-b4936ff84b19").status shouldBe HttpStatusCode.OK
        }
    }

    "NOT FOUND for non existing reindeer" {
        testApplication {
            application {
                module()
            }

            val nonExistingReindeer = UUID.randomUUID().toString()
            client.get("/reindeer/$nonExistingReindeer").status shouldBe HttpStatusCode.NotFound
        }
    }

    "CONFLICT when trying to create existing reindeer" {
        testApplication {
            application {
                module()
            }

            val request = ReindeerToCreateRequest("Petar", ReindeerColor.PURPLE)

            client.post("/reindeer") {
                contentType(Application.Json)
                setBody(Json.encodeToString<ReindeerToCreateRequest>(request))
            }.status shouldBe HttpStatusCode.Conflict
        }
    }
})