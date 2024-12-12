package gifts

class Child(val name: String, val behavior: String) {
    private var wishlist: List<Toy> ? = null

    fun getWishlist(): List<Toy> ? {
        return wishlist
    }

    fun setWishList(firstChoice: Toy, secondChoice: Toy, thirdChoice: Toy) {
        wishlist = listOf(firstChoice, secondChoice, thirdChoice)
    }
}
