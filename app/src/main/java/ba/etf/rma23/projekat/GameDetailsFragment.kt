package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.*
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.getReviewsForGame
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.sendReview
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
    private lateinit var reviewButton: AppCompatButton
    private lateinit var reviewText: EditText
    private lateinit var ratingBar:RatingBar
    private lateinit var impressionViewerAdapter: UserImpressionAdapter
    private var list = ArrayList<UserImpression>()
    private lateinit var result :List<GameReview>
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
        ratingBar = view.findViewById(R.id.rating_bar)
        reviewButton=view.findViewById(R.id.review_button)
        reviewText=view.findViewById(R.id.text_review)
        description = view.findViewById(R.id.description_textview)
        saveButton = view.findViewById(R.id.save_button)
        deleteButton = view.findViewById(R.id.delete_button)



        impressionViewer = view.findViewById(R.id.review_list)
        impressionViewer.layoutManager= LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        val extras = arguments
        if (extras != null) {
            gameId= extras.getInt("game_id")
            searchById(gameId)

        } else {
            activity?.finish()
        }

        updateReviews()



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

        deleteButton.setOnClickListener {
            onClickDeleteButton()
        }

        reviewButton.setOnClickListener{

            if (reviewText.text.isNotEmpty() && ratingBar.rating > 0.0) {
                        sendUserReview(GameReview( ratingBar.rating.toInt(), reviewText.text.toString(),gameId,true,"",""))
            }
            else if (reviewText.text.isEmpty() && ratingBar.rating > 0.0) {
                        sendUserReview(GameReview(ratingBar.rating.toInt(), null,gameId,true, "",""))
            }
            else if(reviewText.text.isNotEmpty()){
                sendUserReview(GameReview( null, reviewText.text.toString(),gameId,true,"",""))
            }
            else{
                val toast = Toast.makeText(context, "No input was found!", Toast.LENGTH_SHORT)
                toast.show()
            }


        }
        return view
    }

    private fun sendUserReview(gameReview: GameReview) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            sendReview(
                requireContext(),
                gameReview
            )

        }
        val toast = Toast.makeText(context, "Your review is sent!", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun updateReviews() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            result = getReviewsForGame(gameId)
            if (result.isNotEmpty()) {
                for (x in result) {
                    if (x.review != null) {
                        list.add(UserReview(x.student!!, 0, x.review!!))
                    }
                    if (x.rating != null) {
                        list.add(UserRating(x.student!!, 0, x.rating!!))
                    }
                }
            }
            impressionViewerAdapter = UserImpressionAdapter(list)
            impressionViewer.adapter = impressionViewerAdapter
        }
    }


    private fun onClickDeleteButton() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{

                AccountGamesRepository.removeGame(game.id)
                onSuccessRemoved()
        }
    }

    private fun onClickSaveButton(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            try {
                AccountGamesRepository.saveGame(game)
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
        val toast = Toast.makeText(context, "Landscape no game details", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onErrorSaved() {
        val toast = Toast.makeText(context, "Error while saving game", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun onSuccessRemoved() {
        val toast = Toast.makeText(context, "Game removed from favourites", Toast.LENGTH_SHORT)
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

        val context: Context = cover.context
        Glide.with(context)
            .load("https://"+game.coverImage)
            .placeholder(R.drawable.picture1) // Placeholder image while loading
            .error(R.drawable.picture1) // Image to display on error
            .into(cover)

    }

}