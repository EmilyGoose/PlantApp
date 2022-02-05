package com.example.plantapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.adapters.PlantListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    // Firebase auth - Initialized in onCreate
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: PlantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Todo replace this with actual data
        val plantList = arrayOf("Tree", "Leaf", "I know what plants are trust me")
        val plantListView = findViewById<RecyclerView>(R.id.plant_list)

        // Initialize Firebase Auth
        auth = Firebase.auth

        plantListView.layoutManager = LinearLayoutManager(this)
        plantListView.adapter = PlantListAdapter(plantList)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // todo
//        } else {
//            // todo kick the user out to login activity
//        }
    }
}