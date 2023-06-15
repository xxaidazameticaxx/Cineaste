package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface GameReviewDao{
    @Insert
    suspend fun insertAll(vararg gameReview: GameReview)
}