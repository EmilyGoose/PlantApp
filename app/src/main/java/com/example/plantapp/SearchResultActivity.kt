package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.json.JSONObject




class SearchResultActivity : AppCompatActivity() {

    // Declare later i promise
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        // Initialize Firebase Auth and Database
        auth = Firebase.auth
        database = Firebase.database.reference

        // Set plant name and title
        val pageData = JSONObject(intent.getStringExtra("data")!!)

        // Grab the info from the json object
        val title = pageData.getString("title")
        val extract = pageData.getString("extract")
        // Get page image at 256px
        val imgSrc = pageData.getJSONObject("thumbnail").getString("source").replace(Regex("[0-9]+px"), "256px")

        // Set the views in the page
        findViewById<TextView>(R.id.plant_title).text = title
        findViewById<TextView>(R.id.plant_info).text = extract
        Picasso.get().load(imgSrc).into(findViewById<ImageView>(R.id.plant_image));

        // "Add to garden" button functionality
        findViewById<Button>(R.id.button_add_garden).setOnClickListener {
            // Write the plant to the user's saved plants
            val userId = auth.currentUser?.uid
            val userData = database.child("users").child(userId.toString())
            val plantData = userData.child("Plants").child(title)
            // Write title and extract
            plantData.child("title").setValue(title)
            plantData.child("extract").setValue(extract)
            plantData.child("thumbnail").child("source").setValue(imgSrc)

            // Return the user to their list of plants
            val intent = Intent(this@SearchResultActivity, HomeActivity::class.java)
            startActivity(intent)
        }
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