package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.*


object AccountGamesRepository {

    private var games =ArrayList<Game>()
    private var aid: String = "5a13938a-1932-4ba9-b8cf-b23b22dca53a"
    private var age: Int = 21


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


}