package com.skillsoft.currencyconverter

import androidx.test.espresso.Espresso.onView  // Allows testing UI of app
import androidx.test.espresso.action.ViewActions.*  // Library of functions for user actions in UI
import androidx.test.espresso.assertion.ViewAssertions.matches  // Allows assertions on View elements
import androidx.test.espresso.matcher.ViewMatchers.*  // Functions for user actions in UI
import androidx.test.rule.ActivityTestRule  // Allows functional testing on singular Activity

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LoginPageTest {

    // Initialize an ActivityTestRule instance to launch an Activity on which tests can be executed
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginPage::class.java)
    var mActivity:LoginPage? = null

    @Test
    fun loginTest() {

        // Extract Activity from teh ActivityTestRule instance
        mActivity = mActivityTestRule.activity

        // Enter a value in the userID field of the LoginPage Activity
        // onView creates a View interaction for a given View
        // .perform allows to execute an action on a View
        val viewUserId = onView(withId(R.id.userID)).perform(typeText("test@test.com"))
        assertNotNull(viewUserId)  // Make sure that the typed text is not null

        // Same as above, but use closeSoftKeyboard to close down the keyboard
        val viewPwd = onView(withId(R.id.password)).perform(typeText("testpassword"), closeSoftKeyboard())
        assertNotNull(viewPwd)

        // .check takes a View assertion (matches) to see whether login button is clickable
        // Use .perform to click() the button
        onView(withId(R.id.login_button)).check(matches(isClickable())).perform(click())

        // Make the app sleep for 2 secs so all elements and/or JSON data get loaded
        Thread.sleep(2000)

        // At this point, we no longer need the LoginPage Activity
        mActivity = null
    }

}