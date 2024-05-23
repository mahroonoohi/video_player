package mahroo.noohi.videoplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import mahroo.noohi.videoplayer.includes.Tools.parseJsonFromAssets
import mahroo.noohi.videoplayer.model.MovieModel
import mahroo.noohi.videoplayer.ui.theme.VideoPlayerTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme() {
                ListViewContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoPlayerTheme() {
        ListViewContent()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewContent() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF3E5F5))
                        .fillMaxWidth()
                        .height(150.dp),
                        contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "Home",color = Color(0xFF01204E), fontWeight = FontWeight.Bold) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFFF72798)
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "YouTube", color = Color(0xFF01204E), fontWeight = FontWeight.Bold) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "YouTube",
                            tint = Color(0xFFF72798)
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        val intent = Intent(context, YouTubeActivity::class.java) // Use context here
                        context.startActivity(intent)
                    })
                NavigationDrawerItem(label = { Text(text = "Logout", color = Color(0xFF01204E), fontWeight = FontWeight.Bold) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color(0xFFF72798)
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
                        (context as? ComponentActivity)?.finish() // Ensure correct activity context
                        System.exit(0)
                    })
            }
        },
    ) {
        Scaffold(
            topBar = {
                val coroutineScopes = rememberCoroutineScope()
                Box(
                    modifier = Modifier
                        .border(2.dp,  Color(0xFF596FB7))
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Video Player",
                                color = Color(0xFF01204E),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFF3E5F5)
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScopes.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    Icons.Rounded.Menu, contentDescription = "MenuButton", tint = Color.Black
                                )
                            }
                        },
                    )
                }
            },
            content = { paddingValues -> VerticalListView(paddingValues) }
        )
    }
}

@Composable
fun VerticalListView(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val COLUMN_COUNT = 2
    val GRID_SPACING = 8.dp
    val itemList = remember { parseJsonFromAssets(context, "data.json") }
    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMN_COUNT),
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            start = GRID_SPACING,
            end = GRID_SPACING,
        ),
        horizontalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterHorizontally),
        content = {
            items(itemList.size) { index ->
                val movie = itemList[index]
                MovieItemCard(movie, Modifier.width(170.dp))
                ListItemDivider()
            }
        })
}

@Composable
fun MovieItemCard(item: MovieModel?, modifier: Modifier) {
    val context = LocalContext.current
    val backgroundColor = Color(0xFFF3E5F5)
    val borderColor = Color(0xFF01204E)
    val borderWidth = 5.dp
    Card(
        modifier = Modifier
            .padding(10.dp)
            .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
            .clickable {
                val intent = Intent(context, VideoDescriptionActivity::class.java)
                intent.putExtra("Key", item!!.trailer_url)
                intent.putExtra("Title", item!!.title)
                intent.putExtra("Sub", item!!.subtitle)
                intent.putExtra("Bio", item!!.bio)
                intent.putExtra("thumbnail", item!!.thumbnail)
                intent.putExtra("Year", item!!.year)
                intent.putExtra("Genres", item!!.genres)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item!!.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                onError = { ex ->
                    Log.e("TAG_IMAGE_ERROR", "MovieItemCard: " + ex.result.request.error)
                },
                error = {
                    painterResource(R.drawable.ic_error)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                loading = {
                    painterResource(R.drawable.placeholder_image)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.title,
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center, fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ListItemDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}
