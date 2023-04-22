package com.example.probazaprvuspiralu

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            val rv: RecyclerView = view as RecyclerView
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

        onView(instanceOf(RecyclerView::class.java)).perform(scrollTo()).check(hasItemCount(prvaIgra.userImpressions.size))
        onView(withText(prvaIgra.description)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.releaseDate)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.platform)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.esrbRating)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.genre)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))

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
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()));

        //details dugme radi sad
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

        onView(withId(R.id.gameDetailsItem)).perform(click());

        //prikazana je posljednja igra
        onView(withText(prvaIgra.description)).check(matches(isCompletelyDisplayed()))


        homeRule.scenario.onActivity { activity ->
            // perform actions and assertions
            activity.finish()
        }
    }

    @Test
    fun scenario2() {
        // Set the orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        //ne treba biti navigacije

        onView(withId(R.id.bottom_nav)).check(doesNotExist())

        // Check if fragment2 is displayed
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()));
        // Check if fragment1 is displayed i da li je home fragment kompletno sa lijeve str od details
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))


        val prvaIgra = GameData.getAll()[0]

        //provjerava da li je prvi put otvorena prva igra i layout kakav je ovo sam ostavila da malo zakomplikujem
        onView(withId(R.id.review_list)).check(hasItemCount(prvaIgra.userImpressions.size))
        onView(allOf(withId(R.id.description_textview),withText(prvaIgra.description)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyBelow(allOf(withId(R.id.item_title_textview),withParent(withId(R.id.roditelj)))))
        onView(allOf(withId(R.id.esrb_rating_textview),withText(prvaIgra.esrbRating)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyLeftOf(withId(R.id.developer_textview)))
        onView(allOf(withId(R.id.genre_textview),withText(prvaIgra.genre)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.release_date_textview),withText(prvaIgra.releaseDate)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.platform_textview),withText(prvaIgra.platform)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyLeftOf(withId(R.id.release_date_textview)))
        onView(allOf(withId(R.id.developer_textview),withText(prvaIgra.developer)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.publisher_textview),withText(prvaIgra.publisher)))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyLeftOf(withId(R.id.genre_textview)))
        onView(allOf(withId(R.id.item_title_textview),withText(prvaIgra.title),withParent(withId(R.id.roditelj))))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.cover_imageview))
            .check(isCompletelyAbove(allOf(withId(R.id.item_title_textview),withParent(withId(R.id.roditelj)))))
        onView(withId(R.id.description_text))
            .check(matches(withText("Description")));
        onView(withId(R.id.aboutgame_textview))
            .check(matches(withText("About game")));
        onView(withId(R.id.review_list))
            .check(isCompletelyBelow(withId(R.id.release_date_textview)))

        //klik neke druge igre

        val drugaIgra = GameData.getAll()[1]
        onView(withId(R.id.game_list)).perform(scrollToPosition<ViewHolder>(1),click())



        //Check if fragment2 is displayed
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()));
        // Check if fragment1 is displayed i da li je home fragment kompletno sa lijeve str od details
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))


        //samo da vidimo jel druga igra prikazana
        onView(withText(drugaIgra.description))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))

        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.fragment_details_parent)).check(doesNotExist())
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))

        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))

        //details dugme ne radi iz home fragment
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        //home dugme ne radi iz home fragment
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))

        //valjda smo sad u details fragmentu

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))


    }

}