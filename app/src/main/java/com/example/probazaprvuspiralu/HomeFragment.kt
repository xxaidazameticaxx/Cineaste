package com.example.probazaprvuspiralu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {
    companion object{
       var lastGameClicked: Game? = null
    }
    private lateinit var gameViewer: RecyclerView
    private lateinit var gameViewerAdapter:GameListAdapter
    private var gameList = GameData.getAll()
    private var isGameClicked = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.home_fragment, container, false)

        gameViewer = view.findViewById(R.id.game_list)
        gameViewer.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameViewerAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        gameViewer.adapter = gameViewerAdapter
        gameViewerAdapter.updateGames(gameList)

        return view;
    }
    private fun showGameDetails(game: Game) {

        val navController = findNavController()
        val bundle = Bundle()
        bundle.putString("game_title", game.title)
        navController.navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
        //navController.navigate(R.id.gameDetailsItem, bundle)
        lastGameClicked = game
        isGameClicked = true
    }


}