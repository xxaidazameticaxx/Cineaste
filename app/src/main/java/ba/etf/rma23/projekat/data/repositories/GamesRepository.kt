package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert


class GamesRepository {

    companion object{

        var currentGames = ArrayList<Game>()

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
                currentGames= games as ArrayList<Game>
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
                        rating = roundRating(Response.rating),
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

        private fun roundRating(rating: Double?): Double? {
            val ratingString = rating?.toString()
            return ratingString?.take(4)?.toDoubleOrNull()
        }

        suspend fun getGamesSafe(name: String): List<Game> {
            return withContext(Dispatchers.IO) {
                val savedGames = getGamesByName(name)
                val gamesSearchResult= ArrayList<Game>()
                for (game in savedGames) {
                    val ageRating = game.esrbRating?.toInt()
                    if (ageRating != null) {
                        if(AccountGamesRepository.getAge()!! >= 18)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() == 17 && ageRating!= 12 && ageRating!=5)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() == 16 && ageRating!= 12 && ageRating!=5 && ageRating!=11)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() in 13..15 && ageRating!= 12 && ageRating!=5 && ageRating!=11 && ageRating!=4)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() == 12 && ageRating!= 12 && ageRating!=5 && ageRating!=11 && ageRating!=4 && ageRating!=10)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() in 10.. 11 && ageRating!= 12 && ageRating!=5 && ageRating!=11 && ageRating!=4 && ageRating!=10 && ageRating!=3)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() in 7.. 9 && ageRating!= 12 && ageRating!=5 && ageRating!=11 && ageRating!=4 && ageRating!=10 && ageRating!=3 && ageRating!=9)
                            gamesSearchResult.add(game)
                        else if(AccountGamesRepository.getAge() in 3.. 6 && ageRating!= 12 && ageRating!=5 && ageRating!=11 && ageRating!=4 && ageRating!=10 && ageRating!=3 && ageRating!=9 && ageRating!=2)
                            gamesSearchResult.add(game)
                    }
                    else gamesSearchResult.clear()
                }
                currentGames = gamesSearchResult
                return@withContext gamesSearchResult
            }

        }

        suspend fun sortGames(): List<Game> {

            return withContext(Dispatchers.IO) {
                val savedGames = AccountGamesRepository.getSavedGames()
                val sortedCurrentGames = currentGames.sortedByDescending { it.title }
                val sortedSavedGames = savedGames.sortedByDescending { it.title }

                val matchingGames = mutableListOf<Game>()

                sortedCurrentGames.forEach { currentGame ->
                    val matchingGame = sortedSavedGames.find { savedGame ->
                        currentGame.title == savedGame.title
                    }
                    if (matchingGame == null) {
                        matchingGames.add(currentGame)
                    }
                }

                sortedSavedGames.forEach { savedGame ->
                    val matchingGame = sortedCurrentGames.find { currentGame ->
                        currentGame.title == savedGame.title
                    }
                    if (matchingGame != null) {
                        matchingGames.add(savedGame)
                    }
                }

                return@withContext matchingGames.reversed()
            }



        }


    }




}