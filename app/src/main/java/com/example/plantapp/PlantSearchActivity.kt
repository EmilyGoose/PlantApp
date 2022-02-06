package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PlantSearchActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_search)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Grab the SearchView
        val searchView = findViewById<SearchView>(R.id.search_input)
        searchView.isSubmitButtonEnabled = true
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Kick out unauthed users
            val intent = Intent(this@PlantSearchActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}