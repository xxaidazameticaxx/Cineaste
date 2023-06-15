package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GameReviewResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("student")val student:String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("review") val review: String?,
    @SerializedName("GameId") val igdb_id: Int?

)
