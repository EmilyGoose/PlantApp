package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class PlantSearchActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_search)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Double search prevention
        var lastSearch = ""

        // Grab the SearchView
        val searchView = findViewById<SearchView>(R.id.search_input)
        searchView.isSubmitButtonEnabled = true

        // Initialize the HTTP library
        client = OkHttpClient()

        // Create the event to send the search query
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        //Event fires twice again, stop the search from happening repeatedly
                        if (lastSearch != query) {
                            Log.d("Search", query)
                            lastSearch = query
                            fetchWiki(query)
                        }
                    } else {
                        Log.d("Search", "Empty search.")
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            }
        )

        // Fix to make whole thing clickable
        searchView.setOnClickListener { searchView.onActionViewExpanded() }
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

    fun fetchWiki(query: String) {
        val url =
            "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts|pageimages&exintro&explaintext&redirects=1&titles=$query"

        val request = Request.Builder().url(url).build()

        Log.d("WikiSearch", "Making request...")
        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("WikiSearch", "Request failed. Reason " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                // INCREDIBLY JANK INFO EXTRACTING CODE
                // THIS MAY NOT WORK ON ALL PAGES
                response.body?.let {
                    val resJSON = JSONObject(it.string())
                    val pageListJSON = resJSON.getJSONObject("query").getJSONObject("pages")
                    val pageJSON = pageListJSON.getJSONObject(pageListJSON.names()!!.getString(0))
                    Log.d("WikiSearch", pageJSON.getString("extract"))
                }
            }
        })
    }
}