
package ba.etf.rma23.projekat.data.repositories
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.Companion.getHash
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @Headers("Client-ID: x6cj1oyj882vhu0oa34j8bgg4gd5q9","Authorization:Bearer pb9vj3svlebnrkg9g7je38ioobdvo2","Content-Type: application/json")

    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name:String,
        @Query("fields") fields: String = "id,name,platforms.name,involved_companies.company.name,age_ratings.rating,age_ratings.category,release_dates.human,rating,cover.url,genres.name,summary"
    ): Response<List<GetGameResponse>>


    @Headers("Client-ID: x6cj1oyj882vhu0oa34j8bgg4gd5q9","Authorization:Bearer pb9vj3svlebnrkg9g7je38ioobdvo2","Content-Type: application/json")

    @POST("games")
    suspend fun getGameById(
        @Body body : RequestBody
    ): Response<List<GetGameResponse>>

    @Headers("Content-Type: application/json")

    @GET("/account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid:String = getHash()
    ): Response<List<GetSwaggerGameResponse>>


    @Headers("Content-Type: application/json")

    @POST("/account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") aid:String,
        @Body body : ResponseHelp
    ): Response<GetSwaggerGameResponse>

    @Headers("Content-Type: application/json")

    @DELETE("/account/{aid}/game/{gid}/")
    suspend fun removeGame(
        @Path("aid") aid:String,
        @Path("gid") gid:Int
    )

    @Headers("Content-Type: application/json")

    @POST("/account/{aid}/game/{gid}/gamereview")
    suspend fun sendReview(
        @Path("aid") aid:String,
        @Path("gid") gid:Int,
        @Body body : RequestBody
    ): Response<GameReviewResponse>

    @Headers("Content-Type: application/json")

    @GET("/game/{gid}/gamereviews")
    suspend fun getReviewsForGame(
        @Path("gid") gid:Int,
    ): Response<List<GameReviewResponse>>
}