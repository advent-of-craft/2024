package domain.core

object Extensions {
  extension [T](obj: T)
    def let(sideEffect: T => Unit): T = {
      sideEffect(obj)
      obj
    }
}