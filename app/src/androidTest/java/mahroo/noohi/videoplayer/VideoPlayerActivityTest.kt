package mahroo.noohi.videoplayer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoPlayerActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<VideoPlayerActivity>()

    @Test
    fun testVideoPlayerLoads() {

        composeTestRule.onNodeWithTag("playerView").assertIsDisplayed()

        composeTestRule.onNodeWithTag("playerBox").performClick()
        composeTestRule.onNodeWithTag("videoTitle").assertIsDisplayed()


        composeTestRule.onNodeWithTag("videoTitle").assertTextEquals(passedTitle)
    }

    @Test
    fun testFullScreenButton() {

        composeTestRule.onNodeWithTag("playerView").performClick()

    }
}
