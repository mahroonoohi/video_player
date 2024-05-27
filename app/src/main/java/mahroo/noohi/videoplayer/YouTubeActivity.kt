package mahroo.noohi.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import mahroo.noohi.videoplayer.ui.theme.VideoPlayerTheme


/**
 * Activity responsible for displaying YouTube videos within the application.
 * Extends [ComponentActivity] and sets its content to the main screen of the video player.
 * Author: Mahroo Noohi
 */
class YouTubeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                MainScreen()
            }
        }
    }
}


/**
 * Composable function representing the main screen of the video player application.
 * This screen allows users to enter YouTube video URLs, add them to the playlist, and view the videos.
 *
 * @Author Mahroo Noohi
 */
@Composable
fun MainScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    var videoUrl by remember { mutableStateOf("") }
    val videoIds = remember { mutableStateListOf<String>() }
    val backgroundColor = Color(0xFFF3E5F5) // Light purple to pink background color
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = videoUrl,
            onValueChange = { videoUrl = it },
            label = { androidx.compose.material.Text("Enter YouTube Video URL") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    val videoId = extractVideoId(videoUrl)
                    if (videoId != null) {
                        videoIds.add(videoId)
                        videoUrl = ""
                    }
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val videoId = extractVideoId(videoUrl)
                if (videoId != null) {
                    videoIds.add(videoId)
                    videoUrl = ""
                }
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color(0xFFBA68C8),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(text = "Add Video", fontSize = 18.sp, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(videoIds) { videoId ->
                YoutubePlayer(youtubeVideoId = videoId, lifecycleOwner = lifecycleOwner)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


/**
 * Composable function for displaying a YouTube player to play videos.
 *
 * This function creates a YouTubePlayerView with the provided YouTube video ID and attaches it to the lifecycle owner.
 *
 * @param youtubeVideoId The ID of the YouTube video to be played.
 * @param lifecycleOwner The LifecycleOwner that controls the lifecycle of the YouTube player.
 *
 * @Author Mahroo Noohi
 */
@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp)),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        }
    )
}


/**
 * Extracts the YouTube video ID from the provided YouTube video URL.
 *
 * This function uses a regular expression to extract the video ID from various YouTube video URL formats.
 * If a valid video ID is found in the URL, it returns the ID; otherwise, it returns null.
 *
 * @param url The YouTube video URL from which to extract the video ID.
 * @return The extracted YouTube video ID, or null if no valid ID is found.
 *
 * @Author Mahroo Noohi
 */
fun extractVideoId(url: String): String? {
    val regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|\\/v%2F)[^#\\&\\?\\n]*".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.value
}
