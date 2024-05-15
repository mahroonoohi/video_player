package mahroo.noohi.videoplayer.model

import java.time.Year

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