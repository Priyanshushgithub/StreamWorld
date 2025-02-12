package com.priyanshsh.streamworld

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import android.widget.Toast

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        } else {
            Toast.makeText(this, "Invalid Movie ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()  // Goes back to the previous activity
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun fetchMovieDetails(movieId: Int) {
        val url = "https://api.watchmode.com/v1/title/$movieId/details/?apiKey=API_KEY"

        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val posterUrl = response.optString("poster", "")
                val title = response.getString("title")
                val releaseYear = response.optString("year", "Unknown")
                val description = response.optString("plot_overview", "No description available")

                // Update UI
                findViewById<TextView>(R.id.titleTextView).text = title
                findViewById<TextView>(R.id.releaseYearTextView).text = releaseYear
                findViewById<TextView>(R.id.descriptionTextView).text = description

                if (posterUrl.isNotEmpty()) {
                    Glide.with(this)
                        .load(posterUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_image)
                        .into(findViewById(R.id.posterImageView))
                } else {
                    findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.error_image)
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching details: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}