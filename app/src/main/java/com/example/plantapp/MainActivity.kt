package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // Declare later i promise
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val myToast = makeText(this, "You clicked a button!", Toast.LENGTH_SHORT)
            myToast.show()

            findViewById<Button>(R.id.login_button).setOnClickListener {
                val intent = Intent(this@MainActivity, loginregister::class.java)
                val loginreg = "login"
                intent.putExtra("logintype", loginreg)
                // start activity for login
                startActivity(intent)
            }

            findViewById<Button>(R.id.register_button).setOnClickListener {
                val intent = Intent(this@MainActivity, loginregister::class.java)
                val loginreg = "register"
                intent.putExtra("logintype", loginreg)
                // start activity for registration
                startActivity(intent)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Change login button accordingly
            findViewById<Button>(R.id.login_button).setText(R.string.prompt_logout)
            // Greet the user ("hello, user!")
            val text = getString(R.string.status_login_true, currentUser.displayName)
            findViewById<TextView>(R.id.login_status).text = text
        } else {
            // Change login button accordingly
            findViewById<Button>(R.id.login_button).setText(R.string.prompt_login)
            // Set text to show not logged in
            findViewById<TextView>(R.id.login_status).setText(R.string.status_login_false)
        }
    }
}

