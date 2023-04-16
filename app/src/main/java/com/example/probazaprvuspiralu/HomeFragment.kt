package com.example.probazaprvuspiralu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.probazaprvuspiralu.GameData.Companion.getAll
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    companion object{
        var isGameClicked = false;
    }

    private lateinit var gameViewer: RecyclerView
    private lateinit var gameViewerAdapter:GameListAdapter
    private var gameList = getAll()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        gameViewer = view.findViewById(R.id.game_list)
        gameViewer.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameViewerAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        gameViewer.adapter = gameViewerAdapter
        gameViewerAdapter.updateGames(gameList)


        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.menu.findItem(R.id.homeItem).isEnabled=false //always disabled from the home fragment
        bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled=false //disabled only the first time we open the app

        if(isGameClicked)
            bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled=true


        //find the detailsButton from the activity
        val detailsButton = requireActivity().findViewById<BottomNavigationItemView>(R.id.gameDetailsItem)

        detailsButton.setOnClickListener{
            val navController = findNavController()
            //Bundle was set as the arguments for the HomeFragment
            val gameTitle = arguments?.getString("game_title") ?: ""
            val bundle = Bundle().apply {
                putString("game_title", gameTitle)
            }
            navController.navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
        }

        return view
    }

    private fun showGameDetails(game: Game) {

        val navController = findNavController()
        val bundle = Bundle()
        bundle.putString("game_title", game.title)
        navController.navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
        isGameClicked=true; //when we click on recycle view item it changes the companion object and stays the same

    }


}