package ba.etf.rma23.projekat

data class UserReview(
    override val userName: String,
    override val timestamp: Long,
    val review: String
): UserImpression()
