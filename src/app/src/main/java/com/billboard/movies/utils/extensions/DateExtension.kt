package com.billboard.movies.utils.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Date.toString(format:String = "yyyy-MM-dd") : String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}

fun String.toDate(format:String = "yyyy-MM-dd") : Date {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.parse(this)
}

fun String.parse(inputFormat:String = "yyyy-MM-dd", outputFormat:String = "yyyy-MM-dd") : String? {
    try {
        return this.toDate(inputFormat).toString(outputFormat)
    }catch (e: Exception){

    }
    return null
}