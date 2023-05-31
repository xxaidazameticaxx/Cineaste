package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName



data class GetGameResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platforms: List<Platform>?,
    @SerializedName("first_release_date") val releaseDate: Long?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("cover") val cover: Cover?,
    //@SerializedName("age_ratings") val esrbResponse: List<Esrb>?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("involved_companies") val involvedCompanies:List<InvolvedCompanies>?,
    @SerializedName("summary") val summary: String?

)


data class Genre (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class InvolvedCompanies (
    @SerializedName("id") val id: Int,
    @SerializedName("company") val company: Company
)

data class Company (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class Cover(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val coverUrl: String
)