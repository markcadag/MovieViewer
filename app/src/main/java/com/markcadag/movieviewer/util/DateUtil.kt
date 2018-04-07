package com.markcadag.movieviewer.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by markcadag on 4/8/18.
 */
object DateUtil {
    /**
     * Converts string to given date/time format
     * @param fromPattern pattern from response
     * @param toPattern pattern output
     */
    fun toFormattedDateTime(dateValue : String?, fromPattern: String,toPattern: String) : String {
        try {
            var sdf = SimpleDateFormat(fromPattern, Locale.getDefault())
            val dt = sdf.parse(dateValue)
            sdf = SimpleDateFormat(toPattern, Locale.getDefault());
            return sdf.format(dt)
        } catch (e : Exception) {
            return "Unknwon time"
        }
    }
}