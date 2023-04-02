package com.example.probazaprvuspiralu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.probazaprvuspiralu.GameData.Companion.getAll


class HomeActivity : AppCompatActivity() {
    private lateinit var homeButton : ImageButton
    private lateinit var backButton:ImageButton

    private lateinit var gameViewer:RecyclerView
    private lateinit var gameViewerAdapter:GameListAdapter
    private var gameList = getAll()
    /*companion object{
        var lastGameClicked: Game? = null
        var lastActivity: String? = null
    }
    */



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        homeButton = findViewById(R.id.home_button)
        backButton=findViewById(R.id.details_button)

        gameViewer = findViewById(R.id.game_list)
        gameViewer.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameViewerAdapter= GameListAdapter(arrayListOf()){ game -> showGameDetails(game) }
        gameViewer.adapter=gameViewerAdapter
        gameViewerAdapter.updateGames(gameList)
        homeButton.setOnClickListener{
            showHomepage()
        }
       /* backButton.setOnClickListener{
            showLastGameDetails()
        }
        */

    }

    private fun showGameDetails(game: Game) {

        /*lastActivity = "GameDetailsActivity"

        // Set last game clicked to the game that was clicked
        lastGameClicked = game
         */

        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)

        }
        startActivity(intent)
    }

    private fun showHomepage(){
        if(!isTaskRoot) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

   /* private fun showLastGameDetails() {
        // Check if last activity is game detail activity
        if (lastActivity == "GameDetailsActivity") {
            // Start game detail activity with the last game clicked
            lastGameClicked?.let { game ->
                val intent = Intent(this, GameDetailsActivity::class.java).apply {
                    putExtra("game_title", game.title)
                }
                startActivity(intent)
            }
        }
    }
    */


}