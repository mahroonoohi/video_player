package mahroo.noohi.videoplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import mahroo.noohi.videoplayer.ui.theme.VideoPlayerTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row


var movie_image: String = ""
var movie_url: String = ""
var movie_title: String = ""
var movie_sub: String = ""
var movie_bio: String = ""
var movie_year: String = ""
var movie_genre: String = ""

/**
 * Activity to display the details of a selected movie.
 *
 * This activity retrieves movie details from the intent extras and displays them using a Compose UI.
 * The details include the movie's thumbnail URL, trailer URL, title, subtitle, biography, release year, and genres.
 * The activity sets the content to a custom theme and layout defined in Compose.
 *
 * Author: Mahroo Noohi
 */

class VideoDescriptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    movie_image = intent.getStringExtra("thumbnail").toString()
                    movie_url = intent.getStringExtra("Key").toString()
                    movie_title = intent.getStringExtra("Title").toString()
                    movie_sub = intent.getStringExtra("Sub").toString()
                    movie_bio = intent.getStringExtra("Bio").toString()
                    movie_year = intent.getStringExtra("Year").toString()
                    movie_genre = intent.getStringExtra("Genres").toString()
                    MovieDetailPage()
                }
            }
        }
    }
}



/**
 * A composable function to display detailed information about a movie.
 *
 * This function creates a layout to display various details of a movie, including its image, title,
 * biography, release year, original language, genres, and a button to play the movie.
 *
 * @param movie_image The URL of the movie's thumbnail image.
 * @param movie_title The title of the movie.
 * @param movie_bio The biography or description of the movie.
 * @param movie_year The release year of the movie.
 * @param movie_genre The genres of the movie.
 * @param movie_url The URL of the movie's trailer or video content.
 *
 * Author: Mahroo Noohi & Kiana Mahdian
 */
@Preview
@Composable
fun MovieDetailPage() {
    val context = LocalContext.current

    val backgroundColor = Color(0xFFF3E5F5) // Light purple to pink background color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie_image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            onError = { ex ->
                Log.e("TAG_IMAGE_ERROR", "MovieItemCard: " + ex.result.request.error)
            },
            error = {
                painterResource(R.drawable.ic_error)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .border(
                    width = 4.dp,
                    color = Color(0xFF495371),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie_title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie_bio,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Year: $movie_year",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Original Language: English",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Genres: $movie_genre",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            color = Color(0xFFBA68C8), // Button background color

            shape = RoundedCornerShape(10.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, VideoPlayerActivity::class.java)
                    intent.putExtra("Key", movie_url)
                    intent.putExtra("Title", movie_title)
                    intent.putExtra("Sub", movie_sub)
                    intent.putExtra("Bio", movie_bio)
                    intent.putExtra("thumbnail", movie_image)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 16.dp)

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Play", fontSize = 18.sp)
                }
            }
        }
    }
}
