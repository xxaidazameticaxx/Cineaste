package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetGameResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platforms: List<Platform>?,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("summary") val summary: String?

)

data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class Cover(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val coverUrl: String
)