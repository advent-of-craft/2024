package gift

class SantaService {
    fun evaluateRequest(child: Child): Boolean =
        child.behavior == Behavior.NICE && child.giftRequest.isFeasible
}