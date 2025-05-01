/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
instrumented test for UI
 */

package dev.gearturner.wallit

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WallItNavigationTest {
    @get:Rule
    val navTestRule = createAndroidComposeRule<MainActivity>()

    //helper function that opens the navigation drawer
    private fun openNavDrawer() {
        navTestRule.onNodeWithTag("drawer_open").performClick()
    }

    /*
    the below functions:
    - open the navigation drawer
    - navigate to each entry
    - check if the text in the top bar is what is expected
    the tests pass if and only if this procedure succeeds
     */

    @Test
    fun favsTest() {
        openNavDrawer()
        navTestRule.onNodeWithTag("drawer_favs").performClick()
        navTestRule.onNodeWithTag("screen_title").assertTextEquals("Favorites")
    }

    @Test
    fun settingsTest() {
        openNavDrawer()
        navTestRule.onNodeWithTag("drawer_settings").performClick()
        navTestRule.onNodeWithTag("screen_title").assertTextEquals("Settings")
    }

    @Test
    fun infoTest() {
        openNavDrawer()
        navTestRule.onNodeWithTag("drawer_info").performClick()
        navTestRule.onNodeWithTag("screen_title").assertTextEquals("Info")
    }

    @Test
    fun homeTest() {
        openNavDrawer()
        navTestRule.onNodeWithTag("drawer_home").performClick()
        navTestRule.onNodeWithTag("screen_title").assertTextEquals("WALLit")
    }
}