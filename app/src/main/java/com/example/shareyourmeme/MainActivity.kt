package com.example.shareyourmeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme()
    {

        // From android developers blog
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.



        val jsonObjectRequest  = JsonObjectRequest(
            Request.Method.GET, url, null,

            Response.Listener { response ->
                val url = response.getString("url")
                //We are using glide library to generate image from URL
                //It takes two params
                //1. Context of current file i.e. this

                Glide.with(this).load(url).into(memeImageView)
                              },
            Response.ErrorListener {
                Toast.makeText(this,"Something Went wrong" ,Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest )
    }

    fun shareMeme(view: View) {}
    fun nextMeme(view: View) {}
}