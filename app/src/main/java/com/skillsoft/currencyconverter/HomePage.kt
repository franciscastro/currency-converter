/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.widget.ArrayAdapter  // Helps map a collection of objects to a spinner
import androidx.drawerlayout.widget.DrawerLayout  // For the navigation pane
import com.google.gson.GsonBuilder  // Use to pass the JSON data from fixer.io

import kotlinx.android.synthetic.main.activity_home_page.*  // To access elements from the activity_home_page layout

// Used to send HTTP requests, receive, and pass JSON responses that come in
import okhttp3.*
import java.io.IOException  // In case the HTTP requests return an exception

// NavigationPane enables access to navigation pane through the action bar
class HomePage : NavigationPane() {

    private var mDelayHandler: Handler? = null
    private val splashDelay: Long = 1000  // milliseconds


    // Execute a runnable task
    private val mRunnable = Runnable {

        // Maps to a resource (spinner item), which would contain objects (list of currency symbols from HomeFeed)
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, homeFeed.rates.keySet().toList())

        // Make the currency symbols accessible as a drop-down view
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Initialize each of the spinners in the homepage with the currency symbols mapped to aa
        with(fromCurr) {

            // Set the adapter property of toCurr to the aa ArrayAdapter
            adapter = aa

            // Initialize the spinner with the first element in its list of values
            setSelection(0, true)

            // Define where the spinner will be placed
            gravity = android.view.Gravity.CENTER
        }

        with(toCurr) {

            // Set the adapter property of toCurr to the aa ArrayAdapter
            adapter = aa

            // Initialize the spinner with the first element in its list of values
            setSelection(0, true)

            // Define where the spinner will be placed
            gravity = android.view.Gravity.CENTER
        }
    }


    private fun fetchJson() {

        println("Attempting")

        val url = "http://data.fixer.io/api/latest?access_key=37a113b14338340500171bbfd16922bc"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        // enqueue() allows to make an asynchronous call
        client.newCall(request).enqueue(object : Callback {

            // Define how the response will be processed in a Callback
            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {

                // Extract the body of the response; ? checks for null values
                val body = response?.body()?.string()

                // Initialize gson instance
                val gson = GsonBuilder().create()

                // Extract the JSON from the body and return a JSON object
                homeFeed = gson.fromJson(body, HomeFeed::class.java)
            }

            // If there are any problems handling the HTTP request
            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Retrieve the drawer layout
        val mDrawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)

        // This is defined in our NavigationPane class --
        // initialize the navigation drawer, attach it to the action bar, and set up menu items
        onCreateDrawer(mDrawerLayout)

        // Retrieve the JSON data from fixer.io
        fetchJson()

        // Set up a listener for the convert button
        convertBtn.setOnClickListener {

            // Initialize an intent for the result page
            val resultIntent = Intent(this, ResultPage::class.java)

            // Check whether the user has entered a value in the enter amount field
            if (enterAmt.text != null) {

                // Extract the text from the field
                val amount = enterAmt.text.toString()

                // Convert the input according the selected currencies in spinners
                val finalValue = conversion(fromCurr.selectedItem.toString(), toCurr.selectedItem.toString(), amount)

                // Put into the resultIntent
                resultIntent.putExtra("key", finalValue.toString())

                // Start up the result page Activity with the result intent
                startActivity(resultIntent)
            }
        }

        // Initialize the handler to execute the runnable instance after a delay
        // mRunnable is where the data is set up for the spinners
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }


    private fun conversion(from: String, to: String, amount: String) : Float {

        val fromCurrRate = homeFeed.rates[from].toString()
        val toCurrRate = homeFeed.rates[to].toString()

        return ((amount.toFloat() * toCurrRate.toFloat()) / fromCurrRate.toFloat())
    }
}