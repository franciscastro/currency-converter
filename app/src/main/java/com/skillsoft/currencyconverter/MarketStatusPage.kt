/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_market_status_page.*

class MarketStatusPage : NavigationPane() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_status_page)

        // Set the navigation drawer for this Activity
        val mDrawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        // Set the base currency from the JSON through homeFeed
        base_currency.text = homeFeed.base

        // Create an ArrayList of formatted strings
        val arrayAdapter: ArrayAdapter<*>
        val array = ArrayList<String>()

        // Iterate over the available currencies in the homeFeed.rates object
        // Add in the formatted string at each iteration
        for (i in homeFeed.rates.keySet()) {
            array.add("$i : ${homeFeed.rates[i]}")  // reference variables with $, {} to evaluate expression
        }

        // Retrieve the list view
        val mListView = findViewById<ListView>(R.id.userlist)

        // Initialize ArrayAdapter and set the list view to this ArrayAdapter
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        mListView.adapter = arrayAdapter
    }
}