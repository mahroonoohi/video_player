package mahroo.noohi.videoplayer.model

import com.google.gson.annotations.SerializedName

data class MovieResponse (

    @SerializedName("result") var result : ArrayList<MovieModel>

)