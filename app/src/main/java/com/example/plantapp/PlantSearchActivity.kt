package com.example.plantapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException


class PlantSearchActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var client: OkHttpClient

    val REQUEST_IMAGE_CAPTURE = 1

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

        // Photo search button
        findViewById<Button>(R.id.photo_button).setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }
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

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleCameraImage(result.data)
        }
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap

        // Convert the bitmap to a byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        // Encode the array to b64
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

        // Create the JSON object for the request
        val requestData = JSONObject()
        // REMOVE HARDCODE BEFORE PUSH TO GIT
        requestData.put("api_key", BuildConfig.PLANT_API_KEY)

        // Add image to request
        val images = JSONArray().put(encodedImage)
        requestData.put("images", images)

        // Modifiers https://github.com/flowerchecker/Plant-id-API/wiki/Modifiers
        val modifiers = JSONArray().put("crops_fast")
        requestData.put("modifiers", modifiers)

        // Add language
        requestData.put("plant_language", "en")

        // Plant details https://github.com/flowerchecker/Plant-id-API/wiki/Plant-details
        val plantDetails = JSONArray().put("common_names")
        requestData.put("plant_details", plantDetails)

        // Build the request
        val url = "https://api.plant.id/v2/identify".toHttpUrlOrNull()
        val urlBuilder = url!!.newBuilder()
        val requestBuilder = Request.Builder().url(urlBuilder.build())
        val mediaTypeJson = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = requestData.toString().toRequestBody(mediaTypeJson)
        requestBuilder.post(requestBody)
        val call = client.newCall(requestBuilder.build())

        // Execute and return request
        val response = call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PlantSearch", "Request failed. Reason " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    // Grab the plant name from the request
                    // TODO check for errors
                    val resJSON = JSONObject(it.string())
                    Log.d("PlantSearch", "Got result from API")
                    val suggestions = resJSON.getJSONArray("suggestions")
                    val plantName = suggestions.getJSONObject(0).getString("plant_name")

                    fetchWiki(plantName)
                }
            }
        })
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
                    Log.d("WikiSearch", "Found a result")

                    // Launch the search result activity
                    val intent = Intent(this@PlantSearchActivity, SearchResultActivity::class.java)
                    intent.putExtra("data", pageJSON.toString())
                    startActivity(intent)
                }
            }
        })
    }
}