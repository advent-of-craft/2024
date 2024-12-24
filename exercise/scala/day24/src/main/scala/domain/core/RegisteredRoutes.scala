package domain.core

import java.util.function.Consumer
import scala.collection.mutable

class RegisteredRoutes {
  private val routes: mutable.Map[String, Consumer[Event]] = mutable.Map.empty

  def dispatch(event: Event): Unit = routes.get(event.getClass.getName).foreach(_.accept(event))

  def register(eventType: String, apply: Consumer[Event]): Unit = routes += (eventType -> apply)
}