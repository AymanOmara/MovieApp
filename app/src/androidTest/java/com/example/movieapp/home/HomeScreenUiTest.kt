package com.example.movieapp.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.movieapp.MainActivity
import com.example.movieapp.util.waitForContentDescription
import com.example.movieapp.util.waitForText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_displaysPopularMoviesSection() {
        composeTestRule.waitForText("Popular Movies")
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displays2024MoviesSection() {
        composeTestRule.waitForText("2024 Movies")
        composeTestRule.onNodeWithText("2024 Movies").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysBottomNavigationWithHomeAndWatchlist() {
        composeTestRule.waitForContentDescription("Home", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_homeNavigationIconIsVisible() {
        composeTestRule.waitForContentDescription("Home", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_watchlistNavigationIconIsVisible() {
        composeTestRule.waitForContentDescription("Watchlist", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_popularMoviesSectionLoadsMovies() {
        composeTestRule.waitForText("Popular Movies")
        composeTestRule.waitUntil(timeoutMillis = 20_000) {
            composeTestRule.onAllNodes(hasScrollAction())
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun homeScreen_canScrollToSeeMonthSections() {
        composeTestRule.waitForText("2024 Movies")
        composeTestRule.onNodeWithText("2024 Movies").assertIsDisplayed()
    }
}
