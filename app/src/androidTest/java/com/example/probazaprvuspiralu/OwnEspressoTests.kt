package com.example.probazaprvuspiralu

import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationView
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                Is(n)
            )
        }
    }

    //ovo vljd ostaje
    @get:Rule
    var homeRule:ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    /*
    Scenarij 1.
     */

    @Test
    fun scenario1() {
        // Set the orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))

        //details dugme ne radi iz home fragment
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        //home dugme ne radi iz home fragment
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))

        //valjda smo sad u details fragmentu

        val prvaIgra = GameData.getAll()[0]
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))

        onView(instanceOf(RecyclerView::class.java)).check(hasItemCount(prvaIgra.userImpressions.size))
        onView(withText(prvaIgra.description)).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.releaseDate)).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.platform)).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.esrbRating)).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.genre)).check(matches(isCompletelyDisplayed()))

        //details dugme ne radi iz details fragment
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        //home dugme radi iz details fragment
        onView(withId(R.id.homeItem)).check(matches(isEnabled()))

        //testiram layout
        onView(withId(R.id.cover_imageview)).check(isCompletelyAbove(withId(R.id.item_title_textview)))
        onView(withId(R.id.description_textview)).check(isCompletelyBelow(withId(R.id.item_title_textview)))
        onView(withId(R.id.description_text)) .check(matches(withText("Description")));
        onView(withId(R.id.aboutgame_textview)) .check(matches(withText("About game")));
        onView(withId(R.id.publisher_textview)) .check(isCompletelyLeftOf(withId(R.id.genre_textview)))
        onView(withId(R.id.esrb_rating_textview)) .check(isCompletelyLeftOf(withId(R.id.developer_textview)))
        onView(withId(R.id.platform_textview)) .check(isCompletelyLeftOf(withId(R.id.release_date_textview)))
        onView(withId(R.id.review_list)).check(isCompletelyBelow(withId(R.id.release_date_textview)))

        // Perform a click on the view that should open the fragment
        onView(withId(R.id.homeItem)).perform(click());

        // Check if the expected fragment is visible
        //onView(withId(R.id.home_fragment)).check(matches(isDisplayed()));

        //details dugme radi sad
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

        onView(withId(R.id.gameDetailsItem)).perform(click());

        //prikazana je posljednja igra
        onView(withText(prvaIgra.description)).check(matches(isCompletelyDisplayed()))


    }

    @Test
    fun scenario2() {
        // Set the orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.bottom_nav)).check(doesNotExist())

        // Check if fragment1 is displayed
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()));

        // Check if fragment2 is displayed
        onView(withId(R.id.details_fragment)).check(matches(isDisplayed()));




    }



}