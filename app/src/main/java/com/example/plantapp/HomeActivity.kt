package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.adapters.PlantListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    // Firebase auth and database
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var adapter: PlantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Firebase Auth
        auth = Firebase.auth
        database = Firebase.database.reference

        database.child("users").child(auth.currentUser?.uid.toString()).get().addOnSuccessListener {
            val userData = JSONObject(it.value as Map<*, *>)

            try {

                // Initialize the RecyclerView and create the adapter
                val plantListView = findViewById<RecyclerView>(R.id.plant_list)
                plantListView.layoutManager = LinearLayoutManager(this)

                // Create an ArrayList<JSONObject> to hold all the plants individually
                val plantKeysIterator = userData.getJSONObject("Plants").keys()
                val plantList = ArrayList<JSONObject>()
                // Iterate over keys and add corresponding values to list
                while (plantKeysIterator.hasNext()) {
                    plantList.add(
                        userData.getJSONObject("Plants").getJSONObject(plantKeysIterator.next())
                    )
                }
                plantListView.adapter = PlantListAdapter(plantList)
            } catch (e: Exception) {
                Log.e("HomeActivity", "Error rendering plants. User probably has none")
            }

            // Hi, user!
            findViewById<TextView>(R.id.greet_user).text =
                getString(R.string.title_greeting, userData.getString("Name"))

        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Kick unauthed users to home screen
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}