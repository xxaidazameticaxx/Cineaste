package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ba.etf.rma23.projekat.Game

@Dao
interface GameReviewDao{

    @Query("SELECT * FROM gamereview WHERE online = 0")
    suspend fun getAllNotSent(): List<GameReview>
    @Insert
    suspend fun insertAll(vararg gameReview: GameReview)

    @Query("UPDATE gamereview SET online = 1 WHERE :igdb_id")
    suspend fun updateOnline(igdb_id:Int)

}