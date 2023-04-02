import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.probazaprvuspiralu.R
import com.example.probazaprvuspiralu.UserReview

class GameReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textUserName: TextView = itemView.findViewById(R.id.username_textview)
    private val textReview: TextView = itemView.findViewById(R.id.review_textview)

    fun bind(review: UserReview) {
        textUserName.text = review.userName
        textReview.text = review.review
    }
}
