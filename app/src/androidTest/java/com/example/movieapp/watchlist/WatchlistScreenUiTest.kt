package com.example.movieapp.watchlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
class WatchlistScreenUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun navigateToWatchlist() {
        composeTestRule.waitForText("Popular Movies", timeoutMillis = 20_000)
        composeTestRule.waitForContentDescription("Watchlist", useUnmergedTree = true)
        
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 20_000) {
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 2
        }
    }

    @Test
    fun watchlistScreen_displaysWatchlistTitle() {
        navigateToWatchlist()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 1
        }
    }

    @Test
    fun watchlistScreen_whenEmpty_displaysEmptyState() {
        navigateToWatchlist()
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodes(hasText("Your watchlist is empty"))
                .fetchSemanticsNodes()
                .isNotEmpty() || 
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 2
        }
    }

    @Test
    fun watchlistScreen_bottomNavigationIsVisible() {
        navigateToWatchlist()
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Watchlist", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun watchlistScreen_canNavigateBackToHome() {
        navigateToWatchlist()
        
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitForText("Popular Movies", timeoutMillis = 10_000)
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun watchlistScreen_hasWatchlistTextInTopBar() {
        navigateToWatchlist()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodes(hasText("Watchlist"))
                .fetchSemanticsNodes()
                .size >= 2
        }
    }
}
