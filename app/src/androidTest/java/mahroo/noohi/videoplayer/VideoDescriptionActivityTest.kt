
package mahroo.noohi.videoplayer

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import mahroo.noohi.videoplayer.ui.theme.VideoPlayerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class VideoDescriptionActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<VideoDescriptionActivity>()

    @Before
    fun setup() {
        Intents.init()

        val intent = Intent().apply {
            putExtra("thumbnail", "https://example.com/image.jpg")
            putExtra("Key", "video_url")
            putExtra("Title", "Sample Title")
            putExtra("Sub", "Sample Subtitle")
            putExtra("Bio", "Sample Bio")
            putExtra("Year", "2023")
            putExtra("Genres", "Drama, Action")
        }
        ActivityScenario.launch<VideoDescriptionActivity>(intent)
    }

    @Test
    fun movieDetailsAreDisplayed() {
        composeTestRule.setContent {
            VideoPlayerTheme {
                MovieDetailPage()
            }
        }

        composeTestRule.onNodeWithText("Sample Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sample Bio").assertIsDisplayed()
        composeTestRule.onNodeWithText("Year: 2023").assertIsDisplayed()
        composeTestRule.onNodeWithText("Genres: Drama, Action").assertIsDisplayed()
    }

    @Test
    fun playButtonOpensVideoPlayerActivity() {
        composeTestRule.setContent {
            VideoPlayerTheme {
                MovieDetailPage()
            }
        }

        composeTestRule.onNodeWithText("Play").performClick()
        Intents.intended(IntentMatchers.hasComponent(VideoPlayerActivity::class.java.name))

        Intents.release()
    }
}
