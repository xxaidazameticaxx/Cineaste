package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.*


object AccountGamesRepository {

    var games =ArrayList<Game>()
    lateinit var game: Game
    var aid: String = "5a13938a-1932-4ba9-b8cf-b23b22dca53a"
    var age: Int = 21


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

    /*
    suspend fun getSavedGames():List<Game> {

        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getSavedGames()

            val gameSwaggerResponses = response.body()

            var games = ArrayList<Game>()

                for ( i in 0 until gameSwaggerResponses?.size!!){

                    search(gameSwaggerResponses.get(i).name, gameSwaggerResponses.get(i).igdb_id)
                    games.add(game)

            }
            return@withContext games

        }

    }

     */

    suspend fun getSavedGames(): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getSavedGames()
            val gameResponses = response.body()

            val updatedGames = mutableListOf<Game>() // Create a new list

            gameResponses?.let { responses ->
                for (gameResponse in responses) {
                    val game = GamesRepository.getGameById(gameResponse.igdb_id)
                    updatedGames.add(game)
                }
            }

            games.clear() // Clear the existing list
            games.addAll(updatedGames) // Add the updated games to the list

            return@withContext games
        }
    }


    fun search(query: String, igdbId: Int) {
        //println("MAGIJA: $query") // Print the value of game.id to the terminal
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            try {
                val result = GamesRepository.getGamesByName(query)
                onSuccess(result,igdbId)
            } catch (e: Exception) {
                throw Exception("Nije dobro pronadjen game po id!")
            }
        }
    }


    fun onSuccess(gamesPar: List<Game>, igdbId: Int){
        //Find the game from the bundle by comparing the id from the list of games searched by name and the bundle id sent from home fragment
        gamesPar.forEach { gameIt ->
            if (gameIt.id == igdbId) {
                this.game =gameIt
                games.add(game)
            }
        }
    }



}