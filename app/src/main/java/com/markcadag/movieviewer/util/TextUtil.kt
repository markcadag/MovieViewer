package com.markcadag.movieviewer.util

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

}