package mahroo.noohi.videoplayer.model

import java.time.Year


/**
 * Data class representing a movie item.
 *
 * This data class encapsulates the properties of a movie, including its ID, title, trailer URL, thumbnail URL,
 * banner URL, subtitle, bio, release year, and genres.
 *
 * @param id The unique identifier of the movie.
 * @param title The title of the movie.
 * @param trailer_url The URL of the movie's trailer video.
 * @param thumbnail The URL of the movie's thumbnail image.
 * @param banner The URL of the movie's banner image.
 * @param subtitle The subtitle of the movie.
 * @param bio The biography or description of the movie.
 * @param year The release year of the movie.
 * @param genres The genres of the movie.
 *
 * @Author Mahroo Noohi  & Zahra Amirinezhad
 */
data class MovieModel(
    val id: String = "",
    val title: String = "",
    val trailer_url: String = "",
    val thumbnail: String = "",
    val banner: String  = "",
    val subtitle: String = "",
    val bio: String = "",
    val year: String= "",
    val genres: String =""
)