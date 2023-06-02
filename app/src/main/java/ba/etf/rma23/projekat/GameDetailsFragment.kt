package ba.etf.rma23.projekat

import android.accounts.Account
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
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
    private lateinit var saveButton:ImageButton
    private lateinit var deleteButton:ImageButton
    private lateinit var impressionViewerAdapter: UserImpressionAdapter
    private var gameId:Int = 0 //spirala 3

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
        saveButton = view.findViewById(R.id.save_button)
        deleteButton = view.findViewById(R.id.delete_button)


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
            gameId= extras.getInt("game_id")
            searchById(gameId)

        } else {
            activity?.finish()
        }

        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            val bottomNavigationView:BottomNavigationView=
                requireActivity().findViewById(R.id.bottom_nav)

            bottomNavigationView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                false //always disabled from the game details fragment
            bottomNavigationView.menu.findItem(R.id.homeItem).isEnabled =
                true //never disabled but needed to specify because of the home fragment


            val homeButton:BottomNavigationItemView = requireActivity().findViewById(R.id.homeItem)

            homeButton.setOnClickListener {
                val navController = findNavController()
                val bundle = Bundle()
                bundle.putString("game_title", game.title)
                bundle.putInt("game_id", game.id)
                navController.navigate(
                    R.id.action_gameDetailsItem_to_homeItem,
                    bundle
                ) //sends game title as a bundle to the home fragment easier than saving the game
            }
        }

        saveButton.setOnClickListener {
            onClickSaveButton()
        }
        return view
    }

    private fun onClickSaveButton(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            try {
                AccountGamesRepository.saveGame(game)!!
                onSuccessSaved()
            } catch (e: Exception) {
                onErrorSaved()
            }

        }
    }

    fun searchById(query: Int){
        //println("MAGIJA: $query") // Print the value of game.id to the terminal
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            try {
                val result = GamesRepository.getGameById(query)
                onSuccess(result)
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun onSuccessSaved(){
        val toast = Toast.makeText(context, "Game added to favourites", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess(game: Game){
        this.game = game
        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error while showing game details", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onErrorSaved() {
        val toast = Toast.makeText(context, "Error while saving game", Toast.LENGTH_SHORT)
        toast.show()
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
        impressionViewerAdapter.updateImpressions(emptyList())

        val context: Context = cover.context
        Glide.with(context)
            .load("https://"+game.coverImage)
            .placeholder(R.drawable.picture1) // Placeholder image while loading
            .error(R.drawable.picture1) // Image to display on error
            .into(cover)

    }

}