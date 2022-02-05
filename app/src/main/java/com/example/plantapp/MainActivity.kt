package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
            val intent = Intent(this@MainActivity, LoginRegisterActivity::class.java)
            val loginMode = "login"
            intent.putExtra("login_mode", loginMode)
            // start activity for login
            startActivity(intent)
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val intent = Intent(this@MainActivity, LoginRegisterActivity::class.java)
            val loginMode = "register"
            intent.putExtra("login_mode", loginMode)
            // start activity for registration
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // You're already logged in, go home
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}

