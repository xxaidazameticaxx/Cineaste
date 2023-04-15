package com.example.probazaprvuspiralu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.probazaprvuspiralu.GameData.Companion.getAll
import java.util.*


class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var cover : ImageView
    private lateinit var platform : TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher : TextView
    private lateinit var description : TextView
    private lateinit var title : TextView
    private lateinit var genre:TextView
    private lateinit var homeButton : ImageButton
    private lateinit var backButton:ImageButton
    private lateinit var impressionViewer:RecyclerView
    private lateinit var impressionViewerAdapter:UserImpressionAdapter
    private var impressionList =listOf<UserImpression>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        title = findViewById(R.id.item_title_textview)
        cover = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre= findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        homeButton=findViewById(R.id.home_button)
        backButton=findViewById(R.id.details_button)


        impressionViewer = findViewById(R.id.review_list)
        impressionViewer.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        impressionViewerAdapter=UserImpressionAdapter(impressionList)
        impressionViewer.adapter=impressionViewerAdapter

        val extras = intent.extras
        if (extras != null) {
            game = getGameByTitle(extras.getString("game_title",""))
            populateDetails()
        } else {
            finish()
        }

        homeButton.setOnClickListener{
            showHomePage()
        }

        backButton.isEnabled=false;


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
        games.addAll(getAll())
        val game = games.find { game -> name == game.title }
        return game?:Game("Test","Test","Test",0.0,"Test","Test","","","","", emptyList())
    }

    private fun showHomePage(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }


}