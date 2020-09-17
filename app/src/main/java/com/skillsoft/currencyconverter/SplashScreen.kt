/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler  // Allows task-execution on same thread (thread executing this activity)


class SplashScreen : AppCompatActivity() {

    // Not immutable (var); nullable (?)
    private var mDelayHandler:Handler? = null

    // How long we want the splash screen to appear; 3 seconds; immutable (val)
    private val splashDelay: Long = 3000

    // Runnable instance: task to execute on the main thread
    private val mRunnable: Runnable = Runnable {

        // Intent linked to HomePage activity
        val homeIntent = Intent(this, HomePage::class.java)

        // Terminate current Activity
        finish()

        // Start-up HomePage Activity
        startActivity(homeIntent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize Handler object to non-null
        mDelayHandler = Handler()

        // Ensure to throw a NullPointer exception if mDelayHandler happens to be null (!!)
        // postDelayed: Execute a task in the Runnable instance following a certain delay
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {

            // Remove any pending operations in the mRunnable instance
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
    
}