package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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


    private fun help(esrbRating: List<AgeRating>?): String? {
        val matchingAgeRating = esrbRating?.find { it.category == 2 } ?: esrbRating?.find { it.category == 1 }
        return matchingAgeRating?.ratings?.toString()
    }


}