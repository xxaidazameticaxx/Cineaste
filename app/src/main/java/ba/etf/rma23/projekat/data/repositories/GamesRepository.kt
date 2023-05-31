package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.UserImpression
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//IZBRISI UPITNIKE!!

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


object GamesRepository {

    suspend fun getGamesByName(name: String): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(name)

            val gameResponses = response.body()

            val games = gameResponses?.map { gameResponse ->
                Game(
                    id = gameResponse.id,
                    title = gameResponse.title,
                    releaseDate = gameResponse.releaseDate.toString(),
                    platform = gameResponse.platforms?.get(0)?.name,
                    rating = gameResponse.rating,
                    description = gameResponse.summary,
                    coverImage = gameResponse.cover?.coverUrl,
                    esrbRating = null,
                    developer = null,
                    publisher = null,
                    genre = null,
                    userImpressions = null

                )

            }
            return@withContext games!!

        }
    }

    /*
    suspend fun getGameById(id: Int): Game {
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGameById(id)

            val gameResponse = response.body()

            val game =
                gameResponse?.let {
                    Game(
                        id = it.id,
                        title = gameResponse.title,
                        releaseDate = gameResponse.releaseDate.toString(),
                        platform = gameResponse.platforms?.get(0)?.name,
                        rating = gameResponse.rating,
                        description = gameResponse.summary,
                        coverImage = gameResponse.cover?.coverUrl,
                        esrbRating = null,
                        developer = null,
                        publisher = null,
                        genre = null,
                        userImpressions = null

                    )
                }


            return@withContext game!!

        }
    }

     */

    suspend fun getGameById(id: Int): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGameById(id)

            val gameResponses = response.body()

            val games1 = gameResponses?.map { gameResponse ->
                Game(
                    id = gameResponse.id,
                    title = gameResponse.title,
                    releaseDate = gameResponse.releaseDate.toString(),
                    platform = gameResponse.platforms?.get(0)?.name,
                    rating = gameResponse.rating,
                    description = gameResponse.summary,
                    coverImage = gameResponse.cover?.coverUrl,
                    esrbRating = null,
                    developer = null,
                    publisher = null,
                    genre = null,
                    userImpressions = null

                )

            }
            return@withContext games1!!

        }
    }


    /*


    suspend fun getGamesByName(name: String): List<Game>?{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName( name)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

     */

    suspend fun getGamesSafe(name:String):List<Game>?{
        return null
    }
    suspend fun sortGames():List<Game>?{
        return null
    }


}