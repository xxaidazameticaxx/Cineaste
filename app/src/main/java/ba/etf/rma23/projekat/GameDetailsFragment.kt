package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    private var gameId:Int = 0
    private lateinit var impressionViewerAdapter: UserImpressionAdapter
    //private var impressionList =listOf<UserImpression>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_details_fragment, container, false)

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

        impressionViewerAdapter= UserImpressionAdapter(emptyList())
        impressionViewer.adapter=impressionViewerAdapter


        val extras = arguments
        if (extras != null) {
            //println("MAGIJA: ${extras.getInt("game_id")}") // Print the value of game.id to the terminal
            search(extras.getString("game_name",""))
            gameId= extras.getInt("game_id")
        } else {
            activity?.finish()
        }

        //kad smo u portrait ne treba nam nista
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) { //dodano
            val bottomNavigationView:BottomNavigationView=
                requireActivity().findViewById(R.id.bottom_nav)

            bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                false //always disabled from the game details fragment
            bottomNavigationView.menu.findItem(R.id.homeItem).isEnabled =
                true //never disabled but needed to specify because of the home fragment


            val homeButton:BottomNavigationItemView = requireActivity().findViewById(R.id.homeItem)

            homeButton.setOnClickListener {
                val navController = findNavController() //ovo vljd ostaje isto
                val bundle = Bundle()
                bundle.putString("game_title", game.title)
                navController.navigate(
                    R.id.action_gameDetailsItem_to_homeItem,
                    bundle
                ) //sends game title as a bundle to the home fragment easier than saving the game
            }
        }
        return view
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
        impressionViewerAdapter.updateImpressions(emptyList()) //ovo cudo!!


        val context: Context = cover.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        cover.setImageResource(id)

    }

    private fun getGameByTitle(name: Int): Game {
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(GameData.getAll())
        val game = games.find { game -> name == game.id }
        return game?: Game(1,"Test","Test","Test",0.0,"Test","Test","","","","", emptyList())
    }

    fun search(query: String){
        println("MAGIJA: $query") // Print the value of game.id to the terminal
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch{

            try {
                // Make the network call and suspend execution until it finishes
                val result = GamesRepository.getGamesByName(query)

                // Display result of the network request to the user
               onSuccess(result)
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun onSuccess(games: List<Game>){
        games.forEach { gameIt ->
            if (gameIt.id == gameId) {
                this.game =gameIt
            }
        }

        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error while showing game details", Toast.LENGTH_SHORT)
        toast.show()
    }

}