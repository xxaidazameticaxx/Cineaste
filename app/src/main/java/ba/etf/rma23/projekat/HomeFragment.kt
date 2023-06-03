package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var gameViewer: RecyclerView
    private lateinit var bottomNavigationView:BottomNavigationView
    private lateinit var detailsButton:BottomNavigationItemView
    private lateinit var searchButton: ImageButton
    private lateinit var searchText:EditText
    private lateinit var favoriteCheckBox:CheckBox
    private lateinit var removeSafeButton:Button
    private lateinit var sortButton:ImageView
    private lateinit var ageInputText:EditText
    private lateinit var gameViewerAdapter: GameListAdapter

    companion object{

        var isGameClicked = false

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        searchButton = view.findViewById(R.id.search_button)
        searchText=view.findViewById(R.id.search_query_edittext)
        favoriteCheckBox=view.findViewById(R.id.favourites_checkBox)
        removeSafeButton=view.findViewById(R.id.remove_safe_button)
        sortButton=view.findViewById(R.id.sort_button)
        ageInputText=view.findViewById(R.id.age_edit_text)

        gameViewer = view.findViewById(R.id.game_list)
        gameViewer.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        gameViewerAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        gameViewer.adapter = gameViewerAdapter


        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            bottomNavigationView =
                requireActivity().findViewById(R.id.bottom_nav)
            bottomNavigationView.menu.findItem(R.id.homeItem).isEnabled =
                false //always disabled from the home fragment
            bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                false //disabled only the first time we open the app

            if (isGameClicked)
                bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled = true


            //find the detailsButton from the activity
            detailsButton =
                requireActivity().findViewById(R.id.gameDetailsItem)

            detailsButton.setOnClickListener {
                val navController = findNavController()
                val gameId = arguments?.getInt("game_id") ?: 0
                val bundle = Bundle().apply {
                    putInt("game_id", gameId)
                }
                navController.navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
            }

        }

        searchFavouriteGames()

        searchButton.setOnClickListener {
            if (favoriteCheckBox.isChecked) {
                searchContainingString(searchText.text.toString())
            }
            else {

                if (ageInputText.text.isNotEmpty()) {
                    val age = ageInputText.text.toString().toInt()
                    if (AccountGamesRepository.setAge(age)) {
                        searchAgeSafeGames(searchText.text.toString())
                    }
                    else{
                        val toast = Toast.makeText(context, "Insert age between 3 and 100", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
                else{
                    search(searchText.text.toString())
                }


            }
        }

        removeSafeButton.setOnClickListener{

            if (ageInputText.text.isNotEmpty()) {
                val age = ageInputText.text.toString().toInt()
                if (AccountGamesRepository.setAge(age)) {
                    removeNonSafeGames()
                }
                else{
                    val toast = Toast.makeText(context, "Insert age between 3 and 100", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }

        sortButton.setOnClickListener{


            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch{

                val result = GamesRepository.sortGames()
                gameViewerAdapter.updateGames(result)

            }

        }

        return view
    }

    private fun removeNonSafeGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{

            val result = AccountGamesRepository.removeNonSafeHelp()
            gameViewerAdapter.updateGames(result)
            GamesRepository.currentGames= result as ArrayList<Game>

        }
    }

    private fun showGameDetails(game: Game) {

        val bundle = Bundle()
        bundle.putInt("game_id", game.id)
        //println("CIRIBU: ${game.id}") // Print the value of game.id to the terminal
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE){
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.action_homeItem_to_gameDetailsItem, bundle)
            isGameClicked =true //when we click on recycle view item it changes the companion object and stays the same
        }
        else{
            val detailsNavController = requireActivity().findNavController(R.id.details_fragment)
            detailsNavController.navigate(R.id.gameDetailsItem,bundle)
        }
    }

    fun searchContainingString(name: String) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch{
                val result = AccountGamesRepository.getGamesContainingString(name)
                onSuccess(result)
        }
    }

    fun searchAgeSafeGames(name: String) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch{
            val result = GamesRepository.getGamesSafe(name)
            onSuccess(result)
        }
    }


    fun search(query: String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        scope.launch{
            try {
                val result = GamesRepository.getGamesByName(query)
                onSuccess(result)
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun searchFavouriteGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{

                val result = AccountGamesRepository.getSavedGames()
                gameViewerAdapter.updateGames(result)
                GamesRepository.currentGames= result as ArrayList<Game>

        }
    }

    fun onSuccess(result: List<Game>) {
        val toast = Toast.makeText(context, "Searched games found", Toast.LENGTH_SHORT)
        toast.show()
        gameViewerAdapter.updateGames(result)
        GamesRepository.currentGames= result as ArrayList<Game>
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }


}