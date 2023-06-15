package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "rating") @SerializedName("rating")   var rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review")  var review: String?,
    @ColumnInfo(name = "igdb_id") @SerializedName("GameId")  var igdb_id: Int,
    @ColumnInfo(name = "online") @SerializedName("online")   var online: Boolean,
    @SerializedName("student")   var student: String?

){
    constructor(igdb_id: Int,rating: Int?, review: String?) :
            this(0, rating, review, igdb_id,true,null)
}