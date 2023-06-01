package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


object GamesRepository {

    suspend fun getGamesByName(name: String): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(name)

            val gameResponses = response.body()

            val games = gameResponses?.map { gameResponse ->
                Game(
                    id = gameResponse.id,
                    title = gameResponse.title,
                    releaseDate = gameResponse.releaseDates?.get(0)?.human,
                    platform = gameResponse.platforms?.get(0)?.name,
                    rating = gameResponse.rating,
                    description = gameResponse.summary,
                    coverImage = gameResponse.cover?.coverUrl,
                    esrbRating = help(gameResponse.esrbRating),
                    developer = gameResponse.involvedCompanies?.get(0)?.company?.name,
                    publisher = gameResponse.involvedCompanies?.get(0)?.company?.name,
                    genre = gameResponse.genres?.get(0)?.name,
                    userImpressions = null

                )

            }
            return@withContext games!!

        }
    }


    fun help(esrbRating: List<AgeRating>?): String? {
        val matchingAgeRating = esrbRating?.find { it.category == 2 } ?: esrbRating?.find { it.category == 1 }
        return matchingAgeRating?.ratings?.toString()
    }

    suspend fun getGameById(id: Int):Game {
        return withContext(Dispatchers.IO) {

            val body = "fields id,name,platforms.name,involved_companies.company.name,age_ratings.rating,age_ratings.category,release_dates.human,rating,cover.url,genres.name,summary;"+
                    "where id = "+ id +";"
            val response= IGDBApiConfig.retrofit.getGameById(body.toRequestBody("text/plain".toMediaType()))


            val gameResponse = response.body()
            val game = gameResponse?.get(0)?.let { Response ->
                Game(
                    id = Response.id,
                    title = Response.title,
                    releaseDate = Response.releaseDates?.get(0)?.human,
                    platform = Response.platforms?.get(0)?.name,
                    rating = Response.rating,
                    description = Response.summary,
                    coverImage = Response.cover?.coverUrl,
                    esrbRating = help(Response.esrbRating),
                    developer = Response.involvedCompanies?.get(0)?.company?.name,
                    publisher = Response.involvedCompanies?.get(0)?.company?.name,
                    genre = Response.genres?.get(0)?.name,
                    userImpressions = null
                )
            }

            return@withContext game!!

        }
    }



}