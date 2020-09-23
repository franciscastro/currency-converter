package com.skillsoft.currencyconverter

class SignUpUtils {

    // Check if the email address has an "@" symbol
    fun isValidEmail(email: String): Boolean {
        return email.indexOf("a") > -1
    }

    fun isValidDOB(DOB: String): Boolean {
        return DOB.indexOf("/") > -1
    }

    // Get the email content before the "@" symbol
    fun getLocalPartLength(email: String): Int {
        val end = email.indexOf("@")
        val localPart = email.substring(0, end)
        return localPart.length
    }
}