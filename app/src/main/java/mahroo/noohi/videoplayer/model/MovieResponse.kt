package mahroo.noohi.videoplayer.model

import com.google.gson.annotations.SerializedName


/**
 * Data class representing a response containing a list of movies.
 *
 * This data class encapsulates the response structure for retrieving a list of movies.
 * It contains an ArrayList of MovieModel objects as the result.
 *
 * @param result The list of movies retrieved from the response.
 *
 * @Author Mahroo Noohi & Zahra Amirinezhad
 */
data class MovieResponse (

    @SerializedName("result") var result : ArrayList<MovieModel>

)