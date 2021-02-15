package com.example.shareyourmeme

import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //We need to share the image url , so taking it a separate var
    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme()
    {

        // From android developers blog
        // Instantiate the RequestQueue.
        //Making progress bar visible
        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.



        val jsonObjectRequest  = JsonObjectRequest(
            Request.Method.GET, url, null,

            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                //We are using glide library to generate image from URL
                //It takes two params
                //1. Context of current file i.e. this

                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                            progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)
                //Creating a handler method which will hide the progress bar when glide would have been downloaded the image


                              },
            Response.ErrorListener {
                Toast.makeText(this,"Something Went wrong" ,Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        //Using Singleton method
        MySingleton(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        //Giving the type of thing which we want to share
        intent.type = "text/plain"
        //We are giving a extra text to the intent
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this meme $currentImageUrl")
        // Creating the chooser which will show which app to use for sharing
        val chooser = Intent.createChooser(intent,"Share this meme using.. ")
        startActivity(chooser)

    }



    fun nextMeme(view: View) {
        loadMeme()
    }
}