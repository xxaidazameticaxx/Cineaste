
package ba.etf.rma23.projekat.data.repositories
import ba.etf.rma23.projekat.Game
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @Headers("Client-ID: x6cj1oyj882vhu0oa34j8bgg4gd5q9","Authorization:Bearer pb9vj3svlebnrkg9g7je38ioobdvo2","Content-Type: application/json")

    @GET("movie/name")
    suspend fun getGamesByName(
        @Query("fields") apiKey: String = "name",
        @Query("search") search:String
    ): Response<List<Game>>

}