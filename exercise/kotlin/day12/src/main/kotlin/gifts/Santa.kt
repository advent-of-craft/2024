package gifts

class Santa {

    private val childrenRepository = mutableListOf<Child>()

    fun chooseToyForChild(childName: String): Toy {
        var found: Child?=null
        for (i in childrenRepository.indices) {
            val currentChild: Child = childrenRepository[i]
            if (currentChild.name == childName) {
                found = currentChild
            }
        }
        if(null == found){
            throw NoSuchElementException("Cannot ind child $childName")
        }
        val child: Child = found

        val wishlist = child.getWishlist()!!
        if ("naughty" == child.behavior) return wishlist[wishlist.size - 1]
        if ("nice" == child.behavior) return wishlist[1]
        return if ("very nice" == child.behavior) wishlist[0] else throw IllegalStateException("unknown child behavior $child")
    }

    fun addChild(child: Child) {
        childrenRepository.add(child)
    }
}
