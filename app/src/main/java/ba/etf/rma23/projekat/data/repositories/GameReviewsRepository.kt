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
                /*gameReview.online=false;
                val db = AppDatabase.getInstance(context)
                db.gameReviewDao().insertAll(gameReview)
                return@withContext false

                 */
                try{
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

                var body = "";
                if(gameReview.review!=null) {
                     body = "{\n" +
                            "  \"review\": \"" + gameReview.review + "\",\n" +
                            "  \"rating\":" + gameReview.rating + "\n" +
                            "}"
                }
                else {
                    body = "{\n" +
                            "  \"rating\":" + gameReview.rating + "\n" +
                            "}"
                }


                        AccountApiConfig.retrofit.sendReview(
                        AccountGamesRepository.getHash(),
                        gameReview.igdb_id,
                        body.toRequestBody("application/json".toMediaType())

                    )
                }
                catch (e: Exception){
                    gameReview.online=false;
                    val db = AppDatabase.getInstance(context)
                    db.gameReviewDao().insertAll(gameReview)
                    return@withContext false
                }
                return@withContext true

            }
        }

        suspend fun getOfflineReviews(context:Context):List<GameReview>{
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                return@withContext db.gameReviewDao().getAllNotSent();
            }
        }

        suspend fun sendOfflineReviews(context:Context):Int{
            return withContext(Dispatchers.IO) {
                var reviewCount = 0
                val db = AppDatabase.getInstance(context)
                val reviewsNotSent = db.gameReviewDao().getAllNotSent();
                for(review in reviewsNotSent){
                    if(sendReview(context,GameReview(review.rating,review.review,review.igdb_id,true,"",""))){
                        db.gameReviewDao().updateOnline(review.igdb_id)
                        reviewCount++;
                    }
                }
                return@withContext reviewCount
            }
        }


        suspend fun getReviewsForGame(igdb_id:Int): List<GameReview> {
            return withContext(Dispatchers.IO) {
                val response = AccountApiConfig.retrofit.getReviewsForGame(igdb_id)
                val gameResponses = response.body()
                return@withContext gameResponses!!
            }
        }
    }
}

