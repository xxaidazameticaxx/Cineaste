package ba.etf.rma23.projekat

import java.io.Serializable

data class Game(
    var id:Int,
    val title: String,
    val platform: String?,
    val releaseDate: String?,
    val rating: Double?,
    val coverImage: String?,
    val esrbRating: String?,
    val developer: String?,
    val publisher: String?,
    val genre: String?,
    val description: String?,
    val userImpressions: List<UserImpression>?,
): Serializable

