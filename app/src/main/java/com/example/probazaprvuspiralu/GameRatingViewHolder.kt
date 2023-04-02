import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.probazaprvuspiralu.R
import com.example.probazaprvuspiralu.UserRating

class GameRatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textUserName: TextView = itemView.findViewById(R.id.username_textview)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)

    fun bind(rating: UserRating) {
        textUserName.text = rating.userName
        ratingBar.rating = rating.rating.toFloat()
    }
}
