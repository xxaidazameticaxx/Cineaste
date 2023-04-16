package com.example.probazaprvuspiralu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.ArrayList

class GameDetailsFragment : Fragment() {

    private lateinit var game: Game
    private lateinit var cover : ImageView
    private lateinit var platform : TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher : TextView
    private lateinit var description : TextView
    private lateinit var title : TextView
    private lateinit var genre: TextView
    private lateinit var impressionViewer: RecyclerView
    private lateinit var impressionViewerAdapter:UserImpressionAdapter
    private var impressionList =listOf<UserImpression>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.game_details_fragment, container, false)


        title = view.findViewById(R.id.item_title_textview)
        cover = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)


        impressionViewer = view.findViewById(R.id.review_list)
        impressionViewer.layoutManager= LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        impressionViewerAdapter=UserImpressionAdapter(impressionList)
        impressionViewer.adapter=impressionViewerAdapter


        val extras = arguments
        if (extras != null) {
            game = getGameByTitle(extras.getString("game_title",""))
            populateDetails()
        } else {
            activity?.finish()
        }


        return view;
    }

    private fun populateDetails() {
        title.text=game.title
        releaseDate.text=game.releaseDate
        platform.text=game.platform
        developer.text=game.developer
        esrbRating.text=game.esrbRating
        publisher.text=game.publisher
        genre.text=game.genre
        description.text=game.description
        impressionList=game.userImpressions.sortedByDescending { it.timestamp }
        impressionViewerAdapter.updateImpressions(impressionList) //ovo cudo!!


        val context: Context = cover.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        cover.setImageResource(id)

    }

    private fun getGameByTitle(name:String): Game {
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(GameData.getAll())
        val game = games.find { game -> name == game.title }
        return game?:Game("Test","Test","Test",0.0,"Test","Test","","","","", emptyList())
    }

}