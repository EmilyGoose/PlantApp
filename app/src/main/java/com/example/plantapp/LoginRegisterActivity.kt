package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRegisterActivity : AppCompatActivity() {

    private val TAG = "LoginRegisterActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginregister)
        val loginMode = intent.getStringExtra("login_mode")
        val login = "login"
        val register = "register"

        // Initialize Firebase Auth
        auth = Firebase.auth

        // if trying to log in show log in relevant fields
        if (loginMode == register) {
            findViewById<Button>(R.id.submitCredBtn).setOnClickListener {
                // Update the title
                findViewById<TextView>(R.id.loginTitle).setText(R.string.title_register)

                // Show the name field (store in var so we don't look it up again later)
                val nameField = findViewById<EditText>(R.id.fieldName)
                nameField.isGone = false

                // need listener for fields to create user
                val name = nameField.text.toString().trim()
                val email = findViewById<TextView>(R.id.fieldEmail).text.toString().trim()
                val password = findViewById<TextView>(R.id.fieldPassword).text.toString().trim()

                //firebase create user account
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            // TODO write user's name to db
                            // Send the user to the home activity
                            val intent =
                                Intent(this@LoginRegisterActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed. Try logging in instead.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        // if trying to register show registration relevant fields
        else if (loginMode == login) {
            // Update the title
            findViewById<TextView>(R.id.loginTitle).setText(R.string.title_login)

            // Hide the name field
            findViewById<EditText>(R.id.fieldName).isGone = true

            findViewById<Button>(R.id.submitCredBtn).setOnClickListener {

                // Grab stuff from the text fields
                val email = findViewById<TextView>(R.id.fieldEmail).text.toString().trim()
                val password = findViewById<TextView>(R.id.fieldPassword).text.toString().trim()
                //firebase verify acct
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            // Send the user to the home activity
                            val intent =
                                Intent(this@LoginRegisterActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }


        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // You're already logged in, go home
            val intent = Intent(this@LoginRegisterActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}

