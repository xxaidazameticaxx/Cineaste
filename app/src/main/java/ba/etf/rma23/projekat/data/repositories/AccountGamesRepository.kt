package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.*

class AccountGamesRepository {

    companion object{
        private var games =ArrayList<Game>()
        private var aid: String = "5a13938a-1932-4ba9-b8cf-b23b22dca53a"
        private var age: Int? = null


        fun setHash(acHash: String): Boolean {
            this.aid= acHash
            return true
        }


        fun getHash(): String {
            return aid
        }

        fun setAge(age: Int): Boolean {
            if (age in 3..100) {
                this.age = age
                return true
            }
            return false
        }

        fun getAge(): Int? {
            return age
        }

        suspend fun getSavedGames(): List<Game> {
            return withContext(Dispatchers.IO) {
                val response = AccountApiConfig.retrofit.getSavedGames()
                val gameResponses = response.body()

                val updatedGames = mutableListOf<Game>() // Create a new list

                gameResponses?.let { responses ->
                    for (gameResponse in responses) {
                        val game = GamesRepository.getGameById(gameResponse.igdb_id!!)
                        updatedGames.add(game)
                    }
                }

                games.clear() // Clear the existing list
                games.addAll(updatedGames) // Add the updated games to the list

                return@withContext games
            }
        }

        suspend fun getGamesContainingString(query:String):List<Game> {
            return withContext(Dispatchers.IO) {
                val savedGames = getSavedGames()
                val gamesContainingString = ArrayList<Game>()
                for (game in savedGames) {
                    if (game.title.contains(query, ignoreCase = true)) {
                        gamesContainingString.add(game)
                    }
                }
                return@withContext gamesContainingString
            }

        }


        suspend fun saveGame(game: Game) : Game{
            return withContext(Dispatchers.IO) {

                val response = AccountApiConfig.retrofit.saveGame(getHash(),ResponseHelp(GetSwaggerGameResponse(game.id,game.title)))
                val gameResponse = response.body()


                return@withContext GamesRepository.getGameById(gameResponse?.igdb_id!!)


            }
        }

        suspend fun removeGame(game: Game): Boolean {
            return withContext(Dispatchers.IO) {

                AccountApiConfig.retrofit.removeGame(getHash(),game.id)

                return@withContext true


            }
        }

        suspend fun removeNonSafe():Boolean{

            return withContext(Dispatchers.IO) {

                val savedGames = getSavedGames()

                val age = getAge()

                for (game in savedGames) {
                    val ageRating = game.esrbRating?.toInt()
                    if (ageRating != null) {
                        if(age == 17 && (ageRating== 12 || ageRating==5))
                            removeGame(game)
                        else if(age == 16 && (ageRating!= 12 && ageRating!=5 && ageRating!=11))
                            removeGame(game)
                        else if(age in 13..15 && (ageRating == 12 || ageRating==5 || ageRating==11 || ageRating==4))
                            removeGame(game)
                        else if(age == 12 && (ageRating== 12 || ageRating==5 || ageRating==11 || ageRating==4 || ageRating==10))
                            removeGame(game)
                        else if(age in 10.. 11 && (ageRating==12 || ageRating==5 || ageRating==11 || ageRating==4 || ageRating==10 || ageRating==3))
                            removeGame(game)
                        else if(age in 7.. 9 && (ageRating== 12 || ageRating==5 || ageRating==11 || ageRating==4 || ageRating==10 || ageRating==3 || ageRating==9))
                            removeGame(game)
                        else if(age in 3.. 6 && (ageRating== 12 || ageRating==5 || ageRating==11 || ageRating==4 || ageRating==10 || ageRating==3 || ageRating==9 || ageRating==2))
                            removeGame(game)
                    }

                }

                return@withContext true
            }

        }

        suspend fun removeNonSafeHelp(): List<Game> {

            return withContext(Dispatchers.IO) {

                removeNonSafe()
                return@withContext getSavedGames()
            }

        }
    }



}