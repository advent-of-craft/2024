package gift

class SantaService {
  def evaluateRequest(child: Child): Boolean =
    child.behavior == Behavior.NICE && child.giftRequest.isFeasible
}