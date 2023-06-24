package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.*
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.getOfflineReviews
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.getReviewsForGame
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.sendOfflineReviews
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.Companion.sendReview
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private lateinit var homeNavController:NavController
    private lateinit var detailsNavController:NavController
    private lateinit var navHostFragment:NavHostFragment
    private lateinit var homeNavHostFragment:NavHostFragment
    private lateinit var detailsNavHostFragment: NavHostFragment
    private var gameList = GameData.getAll()
    private lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.home_activity) //the device recognizes which layout to take
            homeNavHostFragment = supportFragmentManager.findFragmentById(R.id.home_fragment) as NavHostFragment //for the home fragment container
            detailsNavHostFragment = supportFragmentManager.findFragmentById(R.id.details_fragment) as NavHostFragment //for the details fragment container
            homeNavController = homeNavHostFragment.navController
            detailsNavController = detailsNavHostFragment.navController

                val bundle = Bundle()
                bundle.putString("game_title", gameList[0].title) //to initialize the first state of the details fragment
                homeNavController.navigate(R.id.homeItem)
                detailsNavController.navigate(R.id.gameDetailsItem, bundle)

        } else {
            setContentView(R.layout.home_activity)
            navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            navView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.homeItem) //every rotation refreshes the view as if we just opened the app


        }
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = GameReviewsRepository.sendReview(this@MainActivity,GameReview(3,"cokolada",4,true,"",""))
        }

    }


}