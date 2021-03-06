package com.example.plantapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.adapters.CommentsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.*


class SearchResultActivity : AppCompatActivity() {

    // Declare later i promise
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userName: String

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
        val imgSrc = pageData.getJSONObject("thumbnail").getString("source")
            .replace(Regex("[0-9]+px"), "256px")

        // Set the views in the page
        findViewById<TextView>(R.id.plant_title).text = title
        findViewById<TextView>(R.id.plant_info).text = extract
        Picasso.get().load(imgSrc).into(findViewById<ImageView>(R.id.plant_image));

        //Check if the user has the plant already
        database.child("users").child(auth.currentUser?.uid.toString()).get().addOnSuccessListener {

            try {
                val userData = JSONObject(it.value as Map<*, *>)

                userName = userData.getString("Name")
                // Create an ArrayList<String> to hold all the plant names
                val plantKeysIterator = userData.getJSONObject("Plants").keys()
                val plantNames = ArrayList<String>()
                // Iterate over keys and add corresponding values to list
                while (plantKeysIterator.hasNext()) {
                    plantNames.add(
                        userData.getJSONObject("Plants").getJSONObject(plantKeysIterator.next())
                            .getString("title")
                    )
                }

                // Check if the user already owns the plant
                if (plantNames.contains(title)) {
                    // "Remove from button" button functionality
                    val gardenButton = findViewById<Button>(R.id.button_add_garden)

                    gardenButton.text = getString(R.string.prompt_delete)

                    gardenButton.setOnClickListener {
                        // Write the plant to the user's saved plants
                        val userId = auth.currentUser?.uid
                        val saveData = database.child("users").child(userId.toString())
                        val plantData = saveData.child("Plants").child(title).removeValue()

                        // Return the user to their list of plants
                        val intent = Intent(this@SearchResultActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    // "Add to garden" button functionality
                    findViewById<Button>(R.id.button_add_garden).setOnClickListener {
                        // Write the plant to the user's saved plants
                        val userId = auth.currentUser?.uid
                        val saveData = database.child("users").child(userId.toString())
                        val plantData = saveData.child("Plants").child(title)
                        // Write title and extract
                        plantData.child("title").setValue(title)
                        plantData.child("extract").setValue(extract)
                        plantData.child("thumbnail").child("source").setValue(imgSrc)

                        // Return the user to their list of plants
                        val intent = Intent(this@SearchResultActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                // Duplicate code but it's 4am who cares
                Log.e("HomeActivity", "Error finding plants. User probably has none")
                Log.e("HomeActivity", e.message!!)
                // "Add to garden" button functionality
                findViewById<Button>(R.id.button_add_garden).setOnClickListener {
                    // Write the plant to the user's saved plants
                    val userId = auth.currentUser?.uid
                    val saveData = database.child("users").child(userId.toString())
                    val plantData = saveData.child("Plants").child(title)
                    // Write title and extract
                    plantData.child("title").setValue(title)
                    plantData.child("extract").setValue(extract)
                    plantData.child("thumbnail").child("source").setValue(imgSrc)

                    // Return the user to their list of plants
                    val intent = Intent(this@SearchResultActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        // Fetch comments
        // You can tell this code is very performant by the incredible number of db calls /s
        database.child("comments").child(title).get().addOnSuccessListener {
            try {

                // Initialize the RecyclerView and create the adapter
                val commentListView = findViewById<RecyclerView>(R.id.comment_list)
                commentListView.layoutManager = LinearLayoutManager(this)

                val commentData = JSONObject(it.value as Map<*, *>)
                val commentsIterator = commentData.keys()
                val comments = ArrayList<JSONObject>()
                // Iterate over comments... you get the idea
                while (commentsIterator.hasNext()) {
                    comments.add(
                        commentData.getJSONObject(commentsIterator.next())
                    )
                }
                // Bind to RecyclerView
                commentListView.adapter = CommentsAdapter(comments)
            } catch (e: Exception) {
                Log.e("CommentFetch", "Error finding comments, it's likely none exist")
                Log.e("CommentFetch", e.message!!)
            }
        }

        // Click listener for comment button
        findViewById<Button>(R.id.button_add_comment).setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Enter a comment")

            // Set up the input
            val input = EditText(this)
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { _, _ ->
                run {
                    val text = input.text.toString()
                    val saveData =
                        database.child("comments").child(title).child(UUID.randomUUID().toString())
                    saveData.child("author").setValue(userName)
                    saveData.child("body").setValue(text)

                    // Refresh the view because I'm too lazy to properly update RecyclerView
                    finish();
                    startActivity(intent.putExtra("data", pageData.toString()));
                }
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog.cancel() }

            builder.show()
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