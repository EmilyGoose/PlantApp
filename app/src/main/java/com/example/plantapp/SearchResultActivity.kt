package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.json.JSONObject




class SearchResultActivity : AppCompatActivity() {

    // Declare later i promise
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Set plant name and title
        val pageData = JSONObject(intent.getStringExtra("data")!!)
        findViewById<TextView>(R.id.plant_title).text = pageData.getString("title")
        findViewById<TextView>(R.id.plant_info).text = pageData.getString("extract")

        // Set plant image
        val imgSrc = pageData.getJSONObject("thumbnail").getString("source")
        Picasso.get().load(imgSrc).into(findViewById<ImageView>(R.id.plant_image));


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Kick unauthed users to home screen
            val intent = Intent(this@SearchResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}