package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.markcadag.movieviewer.R

/**
 * Created by markcadag on 4/8/18.
 */
open class RowItem(context: Context?) : TextView(context) {
    init {
        gravity = Gravity.CENTER

        val margin = resources.getDimensionPixelSize(R.dimen._1sdp)
        val params = LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f )
        params.setMargins(margin, margin, margin, margin)

        textSize = resources.getDimension(R.dimen._2ssp)

        layoutParams = params
    }
}