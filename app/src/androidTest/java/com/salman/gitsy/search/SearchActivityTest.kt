package com.salman.gitsy.search

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.salman.gitsy.R
import com.salman.gitsy.view.search.SearchActivity
import com.salman.gitsy.view.search.SearchAdapter
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Salman Saifi on 19/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchActivityTest {

    @get: Rule
    val searchScenario = ActivityScenarioRule(SearchActivity::class.java)


    @Test
    fun is_search_view_visible() {
        onView(withId(R.id.searchInput)).check(matches(isDisplayed()))
    }

    @Test
    fun is_users_list_visible() {
        onView(withId(R.id.users)).check(matches(isDisplayed()))
    }

    @Test
    fun check_search_input() {

        onView(withId(R.id.searchInput))
            .perform(typeText("jake"), closeSoftKeyboard())

        onView(isRoot()).perform(waitFor(3000))

        onView(withId(R.id.searchInput)).check(matches(withText("jake")))

        onView(withId(R.id.users)).perform(
            actionOnItemAtPosition<SearchAdapter.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.username)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.users)).check(matches(isDisplayed()))


    }


    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}