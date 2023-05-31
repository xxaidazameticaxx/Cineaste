
package ba.etf.rma23.projekat.data.repositories
import ba.etf.rma23.projekat.Game
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @Headers("Client-ID: x6cj1oyj882vhu0oa34j8bgg4gd5q9","Authorization:Bearer pb9vj3svlebnrkg9g7je38ioobdvo2","Content-Type: application/json")

    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name:String,
        @Query("fields") fields: String = "id,name,platforms.name,first_release_date,rating,cover.url,genres.name,summary"
    ): Response<List<GetGameResponse>>

    @GET("games")
    suspend fun getGameById(
        @Query("id") id:Int,
        @Query("fields") fields: String = "id,name,platforms.name,first_release_date,rating,cover.url,genres.name,summary"
    ): Response<List<GetGameResponse>>


}