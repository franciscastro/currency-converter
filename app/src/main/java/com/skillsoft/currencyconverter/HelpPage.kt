/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_help_page.*

// This class extends NavigationPane
class HelpPage : NavigationPane() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_page)

        // Retrieve DrawerLayout and setup drawer through onCreateDrawer from NavigationPane
        val mDrawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        onCreateDrawer(mDrawerLayout)

        // Set answer field to the size (number of symbols/items) of the keyset of the homeFeed.rates
        help_page_ans_numcurr.text = homeFeed.rates.keySet().size.toString()

        // Set answer field to extracted value from homeFeed.base
        help_page_ans_basecurr.text = homeFeed.base
    }

}