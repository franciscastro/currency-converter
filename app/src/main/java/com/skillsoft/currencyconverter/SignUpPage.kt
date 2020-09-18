package com.skillsoft.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils  // Used for performing user-input field validations
import android.widget.Toast

// Allows asynchronous operations
// Used to create instance of user in Firebase real-time database
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth  // Entry-point of Firebase authentication service
import com.google.firebase.auth.AuthResult  // Allows to retrieve the ID for newly-created user
import com.google.firebase.database.FirebaseDatabase  // For accessing database
import kotlinx.android.synthetic.main.activity_sign_up_page.*

class SignUpPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        // Add listener to submit button (see xml)
        submit.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {

        // First extract user-inputs in the fields
        val firstName = first_name.text.toString()
        val lastName = last_name.text.toString()
        val mailAddr = email.text.toString()
        val dob = DOB.text.toString()
        val pwd = password.text.toString()
        val confirmPWD = confirm_password.text.toString()

        // Check if the user has filled in data for each field; check matching passwords
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter First Name", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter Last Name", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(mailAddr)) {
            Toast.makeText(this,"Please enter E-mail", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(dob)) {
            Toast.makeText(this, "Please enter Date of Birth", Toast.LENGTH_LONG).show()
            return
        }

        if (pwd != confirmPWD) {
            Toast.makeText(this, "Password did not match", Toast.LENGTH_LONG).show()
            return
        }

        // Once validations have passed, register user with the authentication service and
        // store details in the database

        // Create an instance of the Firebase authentication service
        val mAuth = FirebaseAuth.getInstance()

        // Initialize an instance of the real-time Firebase database
        val database = FirebaseDatabase.getInstance()

        // To read/write to the Firebase database, need access to a database reference
        // Data is stored as a tree structure (a JSON tree)
        // User info is stored in a Users object accessed through reference.child
        // databaseRef is an overall Users object containing data for all users
        val databaseRef = database.reference.child("Users")

        // Register user with the authentication service
        // This returns a Task object with an AuthResult instance
        mAuth.createUserWithEmailAndPassword(mailAddr, pwd).addOnCompleteListener { task: Task<AuthResult> ->

            // Check if the Task returned with call to createUserWithEmailAndPassword is successful
            if (task.isSuccessful) {

                // Extract the current user's id (unique identifier for any user signed in
                // through Firebase authentication service)
                val userID = mAuth.currentUser!!.uid

                // Create a new child object in databaseRef representing the user that just registered
                val currentUser = databaseRef.child(userID)

                // Within currentUser, add children (fields) with the user info
                currentUser.child("firstName").setValue(firstName)
                currentUser.child("lastName").setValue(lastName)
                currentUser.child("dob").setValue(dob)
                currentUser.child("email").setValue(mailAddr)

                // Notify of successful sign-up
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_LONG).show()

                // Direct user to LoginPage Activity
                val loginIntent = Intent(this, LoginPage::class.java)
                startActivity(loginIntent)
            }
            else {
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_LONG).show()
            }

        }

    }

}