package mahroo.noohi.videoplayer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class YouTubeActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<YouTubeActivity>()

    @Test
    fun testAddVideo() {
        val validYouTubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"


        composeTestRule.onNodeWithTag("videoInputField").assertIsDisplayed()


        composeTestRule.onNodeWithTag("videoInputField").performTextInput(validYouTubeUrl)


        composeTestRule.onNodeWithTag("addButton").performClick()


        composeTestRule.onNodeWithTag("youtubePlayer").assertIsDisplayed()
    }
}
