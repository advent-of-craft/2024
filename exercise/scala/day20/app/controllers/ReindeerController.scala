package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{Format, JsValue, Json, OFormat}
import play.api.mvc._
import reindeer._

import java.util.UUID

@Singleton
class ReindeerController @Inject()(val controllerComponents: ControllerComponents, reindeerService: ReindeerService) extends BaseController {
  implicit val reindeerColorFormat: Format[ReindeerColor.Value] = Json.formatEnum(ReindeerColor)
  implicit val reindeerFormat: OFormat[Reindeer] = Json.format[Reindeer]
  implicit val reindeerResultFormat: OFormat[ReindeerResult] = Json.format[ReindeerResult]
  implicit val reindeerToCreateRequestFormat: OFormat[ReindeerToCreateRequest] = Json.format[ReindeerToCreateRequest]

  def getReindeer(id: String): Action[AnyContent] = Action {
    val uuid = UUID.fromString(id)
    reindeerService.get(uuid) match {
      case Right(reindeer) => Ok(Json.toJson(ReindeerResult.from(reindeer)))
      case Left(_) => NotFound
    }
  }

  def createReindeer(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[ReindeerToCreateRequest].map { reindeerToCreateRequest =>
      reindeerService.create(reindeerToCreateRequest.toDomain) match {
        case Right(reindeer) => Created(Json.toJson(reindeer))
        case Left(ReindeerErrorCode.AlreadyExist) => Conflict
        case _ => BadRequest
      }
    }.getOrElse {
      BadRequest("Invalid JSON")
    }
  }
}
