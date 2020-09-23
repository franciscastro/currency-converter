package com.skillsoft.currencyconverter

import org.junit.Assert.*
import org.junit.Test
import org.junit.Before

var validUserID = "bob@email.com"
var invalidUserID = "xyz.com"
var validDOB = "20/01/1996"
var invalidDOB = "20-01-1996"

class SignUpPageUtilsTest {

    private lateinit var utils: SignUpUtils

    @Before
    fun setup() {  // Will be executed just prior to each of the tests
        utils = SignUpUtils()
    }

    @Test
    fun validEmail() {
        assert(utils.isValidEmail(validUserID))  // assert Accepts Boolean value
    }

    @Test
    fun invalidEmail() {
        assert(!utils.isValidEmail(invalidUserID))
    }

    @Test
    fun validDOB() {
        assert(utils.isValidDOB(validDOB))
    }

    @Test
    fun invalidDOB() {
        assert(!utils.isValidDOB(invalidDOB))
    }

    @Test
    fun localPartLength() {
        assertEquals(3, utils.getLocalPartLength(validUserID))
    }

}