package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//IZBRISI UPITNIKE!!

class GamesRepository {

    suspend fun getGamesByName(name: String): List<Game>?{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(search = name)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getGamesSafe(name:String):List<Game>?{
return null
    }
    suspend fun sortGames():List<Game>?{
return null
    }


}