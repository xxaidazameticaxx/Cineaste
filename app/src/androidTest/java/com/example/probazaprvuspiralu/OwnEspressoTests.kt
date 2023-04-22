package com.example.probazaprvuspiralu

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
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {

    @After
    fun cleanup() {
        homeRule.scenario.close()
    }

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
    Scenarij 1. šta testirate?, zašto? i kako osiguravate da test provjerava navedeno?
    This scenario covers case:
     - the app is opened in portrait mode,
     - device rotates while in home fragment
     - device rotates once again

     */

    @Test
    fun scenario1() {

        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // check if the home fragment is shown and not the details fragment
        onView(withId(R.id.fragment_details_parent)).check(doesNotExist())
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))

        // check if bottom navigation exists and is displayed in portrait orientation
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))

        // check if menu items home and details are disabled when we first open the app
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))

        // rotate device to landscape
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // check if the details fragment is shown
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()))
        // check if the home fragment is shown and is placed left of the details fragment
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))

        // check if bottom navigation doesn't exist in landscape orientation
        onView(withId(R.id.bottom_nav)).check(doesNotExist())


       // rotate device to portrait
       homeRule.scenario.onActivity {
           it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
       }


       // when we go back to portrait mode we should be able to use the app as if we just opened it
       onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))
       onView(withId(R.id.fragment_details_parent)).check(doesNotExist())
       onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
       onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

       // onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))

    }

    /*
    Scenarij 2. šta testirate?, zašto? i kako osiguravate da test provjerava navedeno?
    This scenario covers case:
     - the app is opened in portrait mode
     - clickOn is perfomed on recycle view item
     - clickOn is performed on menu home item
     - clickOn is performed on menu details item
     - device rotates while in details fragment

     */
    @Test
    fun scenario2() {
        // Set the orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // check if bottom navigation exists and is displayed in portrait orientation
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))

        // check if menu items home and details are disabled when we first open the app
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))

        // check the children of the recycle view and perform clickOn action on the first game
        val prvaIgra = GameData.getAll()[0]
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))

        // check if the first game data is displayed
        onView(instanceOf(RecyclerView::class.java)).perform(scrollTo()).check(hasItemCount(prvaIgra.userImpressions.size))
        onView(withText(prvaIgra.description)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.releaseDate)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.platform)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.esrbRating)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))
        onView(withText(prvaIgra.genre)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))

        // check if menu item details is disabled in details fragment
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        // check if menu item home is enabled in details fragment
        onView(withId(R.id.homeItem)).check(matches(isEnabled()))

        // testing the new game_details_fragment
        onView(withId(R.id.cover_imageview)).perform(scrollTo()).check(isCompletelyAbove(withId(R.id.item_title_textview)))
        onView(withId(R.id.description_textview)).perform(scrollTo()).check(isCompletelyBelow(withId(R.id.item_title_textview)))
        onView(withId(R.id.description_text)) .perform(scrollTo()).check(matches(withText("Description")))
        onView(withId(R.id.aboutgame_textview)) .perform(scrollTo()).check(matches(withText("About game")))
        onView(withId(R.id.publisher_textview)) .perform(scrollTo()).check(isCompletelyLeftOf(withId(R.id.genre_textview)))
        onView(withId(R.id.esrb_rating_textview)) .perform(scrollTo()).check(isCompletelyLeftOf(withId(R.id.developer_textview)))
        onView(withId(R.id.platform_textview)) .perform(scrollTo()).check(isCompletelyLeftOf(withId(R.id.release_date_textview)))
        onView(withId(R.id.review_list)).perform(scrollTo()).check(isCompletelyBelow(withId(R.id.release_date_textview)))

        // perform a click on the menu home item that should open the home fragment
        onView(withId(R.id.homeItem)).perform(click())

        // check if the expected fragment is visible
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))

        // menu details item doesn't work from home fragment
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

        // perform clickOn the menu details item
        onView(withId(R.id.gameDetailsItem)).perform(click())

        // check if the last game we opened is displayed
        onView(withText(prvaIgra.description)).perform(scrollTo()).check(matches(isCompletelyDisplayed()))


        // change orientation to landscape
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // bottom navigation should not exist in landscape orientation
        onView(withId(R.id.bottom_nav)).check(doesNotExist())

        // check if the two expected fragments are displayed
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))

        // change device orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }


        // when we go back to portrait mode we should be able to use the app as if we just opened it
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_details_parent)).check(doesNotExist())
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        // onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))


    }

    /*
       Scenarij 3.
    */
    @Test
    fun scenario3() {
        // Set the orientation to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // bottom navigation should not exist in landscape orientation
        onView(withId(R.id.bottom_nav)).check(doesNotExist())

        // check if the two expected fragments are displayed
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))


        val prvaIgra = GameData.getAll()[0]

        // checking the new game_details_fragment layout and checking if the first game of the recycle view is shown in landscape orientation
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
            .check(matches(withText("Description")))
        onView(withId(R.id.aboutgame_textview))
            .check(matches(withText("About game")))
        onView(withId(R.id.review_list))
            .check(isCompletelyBelow(withId(R.id.release_date_textview)))


        // checking if onClick recycle view item opens another game
        val drugaIgra = GameData.getAll()[1]
        onView(withId(R.id.game_list)).perform(scrollToPosition<ViewHolder>(1),click())
        onView(withText(drugaIgra.description))
            .perform(scrollTo())
            .check(matches(isCompletelyDisplayed()))


        // checking if the fragments are still visible and in the right position
        onView(withId(R.id.fragment_details_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed())).check(isCompletelyLeftOf(withId(R.id.fragment_details_parent)))

        // rotate device to portrait
        homeRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // when we go back to portrait mode we should be able to use the app as if we just opened it
        onView(withId(R.id.fragment_home_parent)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_details_parent)).check(doesNotExist())
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        // recycle view onClick still works
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))

    }


}