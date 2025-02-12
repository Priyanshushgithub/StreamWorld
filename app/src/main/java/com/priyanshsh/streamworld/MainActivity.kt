package com.priyanshsh.streamworld

import MovieAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var toggleButton: ToggleButton
    private var movieList = mutableListOf<Movie>()
    private var isMovies = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        toggleButton = findViewById(R.id.toggleButton)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MovieAdapter(movieList) { movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("movieId", movie.id) // Pass the movie ID
            startActivity(intent)
        }

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            isMovies = !isChecked
            fetchMoviesOrTVShows()
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finishAffinity() // This closes all activities and exits the app
            System.exit(0)
        }





        fetchMoviesOrTVShows()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchMoviesOrTVShows() {
        val type = if (isMovies) "movie" else "tv_series"
        val url = "https://api.watchmode.com/v1/list-titles/?apiKey=API_KEY&types=$type"

        val shimmerLayout = findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()

        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE

                val results = response.getJSONArray("titles")
                movieList.clear()
                for (i in 0 until results.length()) {
                    val item = results.getJSONObject(i)
                    val movie = Movie(
                        id = item.getInt("id"),
                        title = item.getString("title"),
                        releaseYear = item.optString("year", "Unknown")
                    )
                    movieList.add(movie)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            },
            { error ->
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}