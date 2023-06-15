package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class GameReviewsRepository {
    companion object{
        suspend fun sendReview(context: Context, gameReview:GameReview):Boolean{
            return withContext(Dispatchers.IO) {

                var gameSaved= false;
                val savedGames = AccountGamesRepository.getSavedGames()

                for (game in savedGames) {
                    if (gameReview.igdb_id==game.id) {
                        gameSaved=true;
                    }
                }

                if(!gameSaved){
                    AccountGamesRepository.saveGame(Game(gameReview.igdb_id,"game_title",null,null,null,null,null,null,null,null,null,null))
                }

                val body = "{\n" +
                        "  \"review\": \""+ gameReview.review +"\",\n" +
                        "  \"rating\":"+ gameReview.rating +"\n" +
                        "}"
                try {
                        AccountApiConfig.retrofit.sendReview(
                        AccountGamesRepository.getHash(),
                        gameReview.igdb_id,
                        body.toRequestBody("application/json".toMediaType())

                    )
                }
                catch (e: Exception){
                    val db = AppDatabase.getInstance(context)
                    db.gameReviewDao().insertAll(gameReview)
                    return@withContext false
                }
                return@withContext true

            }
        }

        suspend fun getReviewsForGame(igdb_id:Int): List<GameReviewResponse> {
            return withContext(Dispatchers.IO) {
                val response = AccountApiConfig.retrofit.getReviewsForGame(igdb_id)
                val gameResponses = response.body()

                return@withContext gameResponses!!
            }
        }
    }
}

