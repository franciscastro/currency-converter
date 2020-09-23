package com.skillsoft.currencyconverter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not

import org.junit.*
import org.junit.Assert.*

class SignUpPageTest {

    // Rule - pull up the SignUpPage Activity
    @Rule
    @JvmField
    var mActivitySignUpPage = ActivityTestRule(SignUpPage::class.java)
    private var activitySignUpPage: SignUpPage? = null  // Create instance of SignUpPage Activity

    @Before
    fun setUp() {
        // Set activitySignUpPage to the activity pointed to by ActivityTestRule
        activitySignUpPage = mActivitySignUpPage.activity
    }

    @Test
    fun firstNameTest() {

        // Make the app sleep for 1 sec so all elements and JSON data get loaded
        Thread.sleep(1000)

        // Check that the submit button is clickable, then click it
        onView(withId(R.id.submit))
            .check(matches(isClickable()))
            .perform(click())

        Thread.sleep(2000)

        // Check that Toast text is displayed for when first name is not entered
        onView(withText("Please enter First Name"))
            .inRoot(withDecorView(not(activitySignUpPage?.window?.decorView)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun lastNameTest() {

        // Access the first_name field, type a value in it, then check that it's not null
        val editFirstName = onView(withId(R.id.first_name))
            .perform(typeText("test3firstname"), closeSoftKeyboard())
        assertNotNull(editFirstName)

        // Check that the submit button is clickable, then click it
        onView(withId(R.id.submit))
            .check(matches(isClickable()))
            .perform(click())

        Thread.sleep(2000)

        // Check that Toast text is displayed for when last name is not entered
        onView(withText("Please enter Last Name"))
            .inRoot(withDecorView(not(activitySignUpPage?.window?.decorView)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun emailTest() {

        // Access the first_name field, type a value in it, then check that it's not null
        val editFirstName = onView(withId(R.id.first_name))
            .perform(typeText("test3firstname"), closeSoftKeyboard())
        assertNotNull(editFirstName)

        // Access the last_name field, type a value in it, then check that it's not null
        val editLastName = onView(withId(R.id.last_name))
            .perform(typeText("test3lastname"), closeSoftKeyboard())
        assertNotNull(editLastName)

        // Check that the submit button is clickable, then click it
        onView(withId(R.id.submit))
            .check(matches(isClickable()))
            .perform(click())

        Thread.sleep(2000)

        // Check that Toast text is displayed for when email is not entered
        onView(withText("Please enter E-mail"))
            .inRoot(withDecorView(not(activitySignUpPage?.window?.decorView)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun dobTest() {

        // Access the first_name field, type a value in it, then check that it's not null
        val editFirstName = onView(withId(R.id.first_name))
            .perform(typeText("test3firstname"), closeSoftKeyboard())
        assertNotNull(editFirstName)

        // Access the last_name field, type a value in it, then check that it's not null
        val editLastName = onView(withId(R.id.last_name))
            .perform(typeText("test3lastname"), closeSoftKeyboard())
        assertNotNull(editLastName)

        // Access the email field, type a value in it, then check that it's not null
        val editEmail = onView(withId(R.id.email))
            .perform(typeText("test3@test3.com"), closeSoftKeyboard())
        assertNotNull(editEmail)

        // Check that the submit button is clickable, then click it
        onView(withId(R.id.submit))
            .check(matches(isClickable()))
            .perform(click())

        Thread.sleep(2000)

        // Check that Toast text is displayed for when email is not entered
        onView(withText("Please enter Date of Birth"))
            .inRoot(withDecorView(not(activitySignUpPage?.window?.decorView)))
            .check(matches(isDisplayed()))
    }


    @After
    fun tearDown() {
        // We don't need the SignUpPage now so, null it
        activitySignUpPage = null
    }
}