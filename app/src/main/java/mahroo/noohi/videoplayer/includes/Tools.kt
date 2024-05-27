package mahroo.noohi.videoplayer.includes

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import mahroo.noohi.videoplayer.model.MovieModel
import mahroo.noohi.videoplayer.model.MovieResponse
import java.io.IOException
import java.io.InputStream

/**
 * Video Data.
 * Author: Zahra Amirinezhad
 */

object Tools {
    private val gsonConvertor by lazy { Gson() }



    fun parseJsonFromAssets(context: Context,fileName: String): List<MovieModel>{

        // Read the JSON file from assets
        val jsonString = try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

        // Use Gson to convert the JSON string into a list of movies
        return try {
            val moviesResponse = Gson().fromJson(jsonString, MovieResponse::class.java)
            moviesResponse.result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}