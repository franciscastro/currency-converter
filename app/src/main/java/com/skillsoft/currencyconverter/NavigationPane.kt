/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

import android.content.Intent
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth  // Needed to retrieve the id of the signed-in user
import com.google.firebase.database.DataSnapshot  // Used to retrieve data from a database location
import com.google.firebase.database.DatabaseError  // For database errors
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener  // Listen for changes in the underlying data

// Originally hardcoded
//var userID:String? = "user@user.com"
//var user_name:String? = "User Person"
// Now: stripped due to added authentication feature
var userID:String? = ""
var user_name:String? = ""

// open keyword: class is not final; can be derived and overriden
open class NavigationPane: AppCompatActivity() {

    // Initialization will take place eventually...
    // ActionBarDrawerToggle allows access to navigation menu via the hamburger icon in action bar
    private lateinit var mToggle: ActionBarDrawerToggle

    fun onCreateDrawer(mDrawerLayout: DrawerLayout) {

        // Set-up navigation pane
        //==================================================

        // Get navigation pane from navigation_pane.xml
        val navigationView:NavigationView = findViewById(R.id.nav_view)

        // Get the header of the navigationView
        val headerView = navigationView.getHeaderView(0)

        // Get the email and name TextViews from the navigationView header
        val emailId: TextView = headerView.findViewById(R.id.email_ID)
        val nameId: TextView = headerView.findViewById(R.id.name_ID)

        // Create an instance of the Firebase authentication service
        val mAuth = FirebaseAuth.getInstance()

        // Initialize instance of database, get reference to Users object in database
        val userRef = FirebaseDatabase.getInstance().reference.child("Users")

        // Use authentication instance to extract the email of the signed-in user
        // then set the emailId TextView to the extracted email
        userID = mAuth.currentUser!!.email
        emailId.text = userID

        // Add a listener to the Users object listen for any changes in the underlying data
        // Note: ValueEventListener is an interface
        userRef.addValueEventListener((object: ValueEventListener {

            // Triggers when the listener fails to connect to the server or
            // encounters any error when accessing the server
            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }

            // Triggers when underlying data changes -- DataSnapshot allows access to data
            // Use DataSnapshot (p0) to extract name of signed-in user
            // mAuth.currentUser!!.uid is the reference to this unique user
            override fun onDataChange(p0: DataSnapshot) {
                val firstName = p0.child(mAuth.currentUser!!.uid).child("firstName").value
                val lastName = p0.child(mAuth.currentUser!!.uid).child("lastName").value
                user_name = "$firstName $lastName"

                // Set the nameId TextView with the retrieved name of the signed-in user
                nameId.text = user_name
            }
        }))

        // Originally: Set hardcoded email and name -- changed after adding authentication feature
        // Set the navigationView TextView elements to the actual values
        //emailId.text = userID
        //nameId.text = user_name

        // Initialize an ActionBarDrawerToggle instance to add the navigation pane to the action bar
        // this: Activity to which this applies
        // mDrawerLayout connected to the action bar
        // R.string.open/close are for Android's accessibility services
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        // So DrawerLayout can listen to taps and ensure mToggle is synced up
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()

        // Allows users to navigate back up to parent Activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //==================================================
        // END: Set-up navigation pane


        // Configure behavior of navigation pane menu items
        //==================================================

        // Add a listener to navigationView which is notified when a menu item is selected
        // menuItem is lambda expression
        navigationView.setNavigationItemSelectedListener { menuItem ->

            // Set the selected menuItem's isChecked to true
            menuItem.isChecked = true

            // Close all currently open drawers
            mDrawerLayout.closeDrawers()

            // Decide what to do with the selected menuItem
            when (menuItem.itemId) {

                R.id.home -> {
                    val homeIntent = Intent(this, HomePage::class.java)
                    startActivity(homeIntent)
                }

                R.id.market_status -> {
                    val marketStatusIntent = Intent(this, MarketStatusPage::class.java)
                    startActivity(marketStatusIntent)
                }

                R.id.help -> {
                    val helpIntent = Intent(this, HelpPage::class.java)
                    startActivity(helpIntent)
                }

                R.id.contact -> {
                    Toast.makeText(this, "Visit franciscastro.github.io", Toast.LENGTH_LONG).show()
                }

                R.id.logout -> {

                    // Terminates the user's session
                    FirebaseAuth.getInstance().signOut()

                    // Direct the user to the LoginPage Activity
                    val loginIntent = Intent(this, LoginPage::class.java)
                    startActivity(loginIntent)
                }
            }
            true
        }

        //==================================================
        // END: Configure behavior of navigation pane menu items

    }

    // Invoked when a MenuItem is selected; invokes an action associated with a MenuItem
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}