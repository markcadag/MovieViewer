package com.markcadag.movieviewer.util

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import com.markcadag.movieviewer.ui.custom.tagview.RoundedBackgroundSpan
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Created by markcadag on 4/8/18.
 */
object TextUtil {

    /**
     * Format double value to readable price text
     */
    fun toFormattedPrice(price : Double) : String {
        /**
         * Set default value of price
         */
        if(price == 0.00) {
            return "Php 0.00"
        }
        var bd = BigDecimal(price)
        val f = DecimalFormat("###,###.00")
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
        return "Php ${f.format(bd)}"
    }

    /**
     * Converts List of string to tagview using spannable approach
     */
    fun toTagView(context: Context,strList: ArrayList<String>) : SpannableStringBuilder {
        val stringBuilder = SpannableStringBuilder()

        var between = ""
        strList.forEach {
            val roundedBackgroundSpan = RoundedBackgroundSpan(context)
            stringBuilder.append(between)
            if (between.length == 0) between = "  "
            val thisTag = "  ${it}  "
            stringBuilder.append("  ${it}  ")
            stringBuilder.setSpan(roundedBackgroundSpan, stringBuilder.length - thisTag.length,
                    stringBuilder.length - thisTag.length + thisTag.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        }
        return stringBuilder
    }
}