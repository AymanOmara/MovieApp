package com.example.movieapp.util

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule

fun ComposeTestRule.waitForText(text: String, timeoutMillis: Long = 15_000) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodes(hasText(text))
            .fetchSemanticsNodes()
            .isNotEmpty()
    }
}

fun ComposeTestRule.waitForContentDescription(
    contentDescription: String,
    useUnmergedTree: Boolean = true,
    timeoutMillis: Long = 10_000
) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodes(hasContentDescription(contentDescription), useUnmergedTree = useUnmergedTree)
            .fetchSemanticsNodes()
            .isNotEmpty()
    }
}

fun ComposeTestRule.waitForTextToDisappear(text: String, timeoutMillis: Long = 10_000) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodes(hasText(text))
            .fetchSemanticsNodes()
            .isEmpty()
    }
}

fun ComposeTestRule.hasNodeWithText(text: String): Boolean {
    return onAllNodes(hasText(text))
        .fetchSemanticsNodes()
        .isNotEmpty()
}

fun ComposeTestRule.hasNodeWithContentDescription(
    contentDescription: String,
    useUnmergedTree: Boolean = true
): Boolean {
    return onAllNodes(hasContentDescription(contentDescription), useUnmergedTree = useUnmergedTree)
        .fetchSemanticsNodes()
        .isNotEmpty()
}
