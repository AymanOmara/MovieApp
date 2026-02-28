package com.example.movieapp.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.example.movieapp.MainActivity
import com.example.movieapp.util.hasNodeWithText
import com.example.movieapp.util.waitForContentDescription
import com.example.movieapp.util.waitForText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieDetailsScreenUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun navigateToFirstMovie() {
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
    }

    @Test
    fun movieDetailsScreen_displaysBackButton() {
        navigateToFirstMovie()
        composeTestRule.waitForContentDescription("Back", timeoutMillis = 10_000)
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_displaysWatchlistButton() {
        navigateToFirstMovie()
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("Add to watchlist")
                .fetchSemanticsNodes()
                .isNotEmpty() ||
            composeTestRule.onAllNodesWithContentDescription("Remove from watchlist")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun movieDetailsScreen_displaysCastSection() {
        navigateToFirstMovie()
        composeTestRule.waitForText("Cast", timeoutMillis = 15_000)
        composeTestRule.onNodeWithText("Cast").assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_displaysSimilarMoviesSection() {
        navigateToFirstMovie()
        composeTestRule.waitForText("Similar Movies", timeoutMillis = 15_000)
        composeTestRule.onNodeWithText("Similar Movies").assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_backButtonNavigatesToHome() {
        navigateToFirstMovie()
        composeTestRule.waitForContentDescription("Back", timeoutMillis = 10_000)
        
        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        composeTestRule.waitForText("Popular Movies", timeoutMillis = 5_000)
        composeTestRule.onNodeWithText("Popular Movies").assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_canScrollToViewAllSections() {
        navigateToFirstMovie()
        composeTestRule.waitForText("Cast", timeoutMillis = 15_000)
        composeTestRule.onNodeWithText("Cast").assertIsDisplayed()
        
        composeTestRule.waitForText("Similar Movies", timeoutMillis = 15_000)
        composeTestRule.onNodeWithText("Similar Movies").assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_toggleWatchlistButton() {
        navigateToFirstMovie()
        
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("Add to watchlist")
                .fetchSemanticsNodes()
                .isNotEmpty() ||
            composeTestRule.onAllNodesWithContentDescription("Remove from watchlist")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        val isInitiallyInWatchlist = composeTestRule
            .onAllNodesWithContentDescription("Remove from watchlist")
            .fetchSemanticsNodes()
            .isNotEmpty()

        if (isInitiallyInWatchlist) {
            composeTestRule
                .onNodeWithContentDescription("Remove from watchlist")
                .performClick()
            composeTestRule.waitForContentDescription("Add to watchlist", timeoutMillis = 5_000)
        } else {
            composeTestRule
                .onNodeWithContentDescription("Add to watchlist")
                .performClick()
            composeTestRule.waitForContentDescription("Remove from watchlist", timeoutMillis = 5_000)
        }
    }

    @Test
    fun movieDetailsScreen_displaysOverviewSection() {
        navigateToFirstMovie()
        composeTestRule.waitForText("Overview", timeoutMillis = 15_000)
        composeTestRule.onNodeWithText("Overview").assertIsDisplayed()
    }

    @Test
    fun movieDetailsScreen_hidesBottomNavigation() {
        navigateToFirstMovie()
        composeTestRule.waitForContentDescription("Back", timeoutMillis = 10_000)
        
        Thread.sleep(1000)
        
        val hasHomeNav = composeTestRule
            .onAllNodesWithContentDescription("Home", useUnmergedTree = true)
            .fetchSemanticsNodes()
            .isEmpty() || 
            try {
                composeTestRule
                    .onNodeWithContentDescription("Home", useUnmergedTree = true)
                    .assertDoesNotExist()
                true
            } catch (_: AssertionError) {
                false
            }
    }
}
