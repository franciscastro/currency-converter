package com.skillsoft.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_login_page.*
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.Task  // Allows running an asynchronous Task
import org.w3c.dom.Text

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show()

        // Create a listener for the sign up button to redirect to sign up Activity
        sign_up_button.setOnClickListener {
            val signUpIntent = Intent(this, SignUpPage::class.java)
            startActivity(signUpIntent)
        }

        // Create a listener for the login button to log the user in using credentials
        login_button.setOnClickListener {
            loginUserAccount()
        }
    }

    // Executed when user taps the back button on their device
    override fun onBackPressed() {
        // Usually, this just takes user back to the previous screen they visited
        // Here, we customize this behavior to display a Toast instead
        Toast.makeText(this, "Please log in", Toast.LENGTH_LONG).show()

        // Ensure that the user stays on the Login Activity/screen
        return
    }

    // Executed when user taps the login button
    private fun loginUserAccount() {

        // Extract the information entered by user in the login fields
        val userId = userID.text.toString()
        val password = password.text.toString()

        // Check if the username and password fields are empty
        if (TextUtils.isEmpty(userId)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        // Create an instance of the Firebase authentication service
        val mAuth = FirebaseAuth.getInstance()

        // Sign in the user with the authentication service instance (mAuth)
        // signInWithEmailAndPassword returns a Task object with authentication result (AuthResult)
        // The Task object returned is passed to the lambda in addOnCompleteListener
        mAuth.signInWithEmailAndPassword(userId, password).addOnCompleteListener { task : Task<AuthResult> ->

            // Check if the authentication with signInWithEmailAndPassword is a success
            if (task.isSuccessful) {

                // Create an Intent to redirect user to HomePage Activity
                val homeIntent = Intent(this, HomePage::class.java)
                Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()

                // Start up the HomePage Activity with the Intent (homeIntent)
                startActivity(homeIntent)
            }
            else {
                Toast.makeText(this, "Login failed. Please try again later.", Toast.LENGTH_LONG).show()
            }

        }

    }
}