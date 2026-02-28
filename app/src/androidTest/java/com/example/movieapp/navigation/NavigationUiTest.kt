package com.example.movieapp.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.movieapp.MainActivity
import com.example.movieapp.util.waitForContentDescription
import com.example.movieapp.util.waitForText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun waitForWatchlistScreen() {
        composeTestRule.waitUntil(timeoutMillis = 20_000) {
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 2
        }
    }

    @Test
    fun navigation_startDestinationIsHome() {
        composeTestRule.waitForText("Popular Movies")
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun navigation_homeToWatchlist() {
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 20_000)
        composeTestRule.waitForContentDescription("Watchlist", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()

        waitForWatchlistScreen()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 2
        }
    }

    @Test
    fun navigation_watchlistToHome() {
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 20_000)
        composeTestRule.waitForContentDescription("Watchlist", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()

        waitForWatchlistScreen()

        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitForText("Popular Movies", timeoutMillis = 10_000)
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun navigation_homeToDetailsAndBack() {
        composeTestRule.waitForText("Popular Movies")
        composeTestRule.waitUntil(timeoutMillis = 20_000) {
            composeTestRule
                .onAllNodes(hasClickAction())
                .fetchSemanticsNodes()
                .size > 5
        }

        Thread.sleep(2000)

        composeTestRule
            .onAllNodes(hasClickAction())
            .onFirst()
            .performClick()

        composeTestRule.waitForContentDescription("Back", timeoutMillis = 10_000)
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        composeTestRule.waitForText("Popular Movies", timeoutMillis = 10_000)
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun navigation_bottomNavIsVisibleOnHome() {
        composeTestRule.waitForContentDescription("Home", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun navigation_bottomNavIsVisibleOnWatchlist() {
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 20_000)
        composeTestRule.waitForContentDescription("Watchlist", useUnmergedTree = true)
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()

        waitForWatchlistScreen()

        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun navigation_multipleNavigationsBetweenHomeAndWatchlist() {
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 20_000)

        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()
        waitForWatchlistScreen()

        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .performClick()
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 10_000)

        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()
        waitForWatchlistScreen()

        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .performClick()
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 10_000)

        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }
}
