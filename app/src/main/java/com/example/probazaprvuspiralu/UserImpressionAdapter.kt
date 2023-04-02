package com.example.probazaprvuspiralu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserImpressionAdapter (
    private var impressions: List<UserImpression>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_REVIEW = 1
    private val VIEW_TYPE_RATING = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_REVIEW -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_review, parent, false)
                ReviewViewHolder(view)
            }
            VIEW_TYPE_RATING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_rating, parent, false)
                RatingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_REVIEW -> {
                val reviewHolder = holder as ReviewViewHolder
                val review = impressions[position] as UserReview
                reviewHolder.userName.text = review.userName
                reviewHolder.reviewText.text = review.review
            }
            VIEW_TYPE_RATING -> {
                val ratingHolder = holder as RatingViewHolder
                val rating = impressions[position] as UserRating
                ratingHolder.userName.text = rating.userName
                ratingHolder.ratingBar.rating = rating.rating.toFloat()
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount(): Int = impressions.size

    override fun getItemViewType(position: Int): Int {
        return when (impressions[position]) {
            is UserReview -> VIEW_TYPE_REVIEW
            is UserRating -> VIEW_TYPE_RATING
            else -> throw IllegalArgumentException("Invalid impression type")
        }
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val reviewText: TextView = itemView.findViewById(R.id.review_textview)
    }

    inner class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }

    fun updateImpressions(impressions: List<UserImpression>) {
        this.impressions = impressions
        notifyDataSetChanged()
    }


}
