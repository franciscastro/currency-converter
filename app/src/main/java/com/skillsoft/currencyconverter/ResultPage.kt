/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout

// Import the properties from the layout for this Activity
import kotlinx.android.synthetic.main.activity_result_page.*

class ResultPage : NavigationPane() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        // Top level component is a drawer layout
        val mDrawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)

        // This is from the NavigationPane class
        onCreateDrawer(mDrawerLayout)

        // Retrieve currency conversion value from Intent passed from HomePage Activity
        val result = intent.getStringExtra("key")

        // Set result text
        result_page.text = result
    }
}