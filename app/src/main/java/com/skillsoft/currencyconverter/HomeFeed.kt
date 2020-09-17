/*
* Author: Francisco Castro, PhD
* */

package com.skillsoft.currencyconverter

import com.google.gson.JsonObject

// Contains all the forex rates and base currency from fixer.io
// Note the fields: these match the relevant field names from the fixer.io JSON
class HomeFeed (val rates: JsonObject, val base: String)

// Declaration of instance of the HomeFeed class
// lateinit = late initialization; initialized when fixer.io data is retrieved
lateinit var homeFeed : HomeFeed