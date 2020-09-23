package com.skillsoft.currencyconverter

import android.widget.AdapterView  // Allows us to access the values within the searchable spinners
import androidx.test.espresso.Espresso.onData  // Allows us to interact with the data in the searchable spinners
import androidx.test.espresso.Espresso.onView  // Interact with Views
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches  // To make sure buttons are clickable
import androidx.test.espresso.contrib.DrawerActions  // To interact with elements in a DrawerLayout
import androidx.test.espresso.matcher.RootMatchers.withDecorView  // Check for Toasts
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.*

import org.junit.*
import java.lang.AssertionError

class HomePageTest {

    // Initialize ActivityTestRule
    // We start with LoginPage instead of HomePage because this is the entry point for the HomePage
    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(LoginPage::class.java)
    private var mActivity:LoginPage? = null

    // Executed before each test case
    @Before
    fun setUp() {

        // Use the LoginPageActivity
        mActivity = mActivityRule.activity

        // Type in user ID and password fields
        onView(withId(R.id.userID)).perform(typeText("test@test.com"))
        onView(withId(R.id.password)).perform(typeText("testpassword"), closeSoftKeyboard())

        // Click the login button to log the user in with the above credentials
        onView(withId(R.id.login_button)).check(matches(isClickable())).perform(click())
    }

    @Test
    fun drawerTest() {

        // Make the app sleep for 5 secs so all elements and JSON data get loaded
        Thread.sleep(5000)

        // Bring up navigation pane
        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.open())

        // Check if market_status item is in the menu, then click on it
        onView(withText(R.string.market_status))
            .check(matches(isDisplayed()))
            .perform(click())

        // Access base_currency field and check that it's not null
        val baseCurrField = onView(withId(R.id.base_currency))
        Assert.assertNotNull(baseCurrField)

        // Bring up navigation pane again
        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.open())

        // Check if help item is in the menu, then click on it
        onView(withText(R.string.help))
            .check(matches(isDisplayed()))
            .perform(click())

        // Check that help_page_ans_basecurr is populated
        val baseCurr = onData(withText(R.id.help_page_ans_basecurr))
        Assert.assertNotNull(baseCurr)

        // Check that help_page_ans_numcurr is populated
        val totalCurr = onView(withId(R.id.help_page_ans_numcurr))
        Assert.assertNotNull(totalCurr)

        // Bring up navigation pane again
        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.open())

        // Check that contact_us item is displayed, then click on it
        onView(withText(R.string.contact_us))
            .check(matches(isDisplayed()))
            .perform(click())

        // Check that Toast text is displayed on screen
        onView(withText("Visit franciscastro.github.io"))
            .inRoot(withDecorView(not(mActivity!!.window.decorView)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun homePageTest() {

        // Make the app sleep for 5 secs so all elements and JSON data get loaded
        Thread.sleep(5000)

        // Access the fromCurr field, check if it's clickable, then click it
        onView(withId(R.id.fromCurr))
            .check(matches(isClickable()))
            .perform(click())

        // Search for "USD" within the spinner, then click it to select it
        // allof - search for given value which matches all of the given conditions
        // .inAdapterView allows us to interact with the selected data
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("USD")))
            .inAdapterView(allOf(isAssignableFrom(AdapterView::class.java)))
            .perform(click())

        // Access the toCurr field, check if it's clickable, then click it
        onView(withId(R.id.toCurr))
            .check(matches(isClickable()))
            .perform(click())

        // Search for "INR" within the spinner, then click it to select it
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("INR")))
            .inAdapterView(allOf(isAssignableFrom(AdapterView::class.java)))
            .perform(click())

        // Access enterAmt View, type "50" in it, close keyboard, check that enterAmt is not null
        val editAmt = onView(withId(R.id.enterAmt))
                        .perform(typeText("50"), closeSoftKeyboard())
        Assert.assertNotNull(editAmt)

        // Access the convertBtn View, check that it's clickable, then click it
        onView(withId(R.id.convertBtn))
            .check(matches(isClickable()))
            .perform(click())

        // Access the result_page Activity, check that it's displayed
        onView(withId(R.id.result_page))
            .check(matches(isDisplayed()))
    }


    // Executed after each test case
    @After
    fun tearDown() {
        // We don't need the LoginPage now so, null it
        mActivity = null
    }
}