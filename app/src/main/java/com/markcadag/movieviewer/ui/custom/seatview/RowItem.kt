package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by markcadag on 4/8/18.
 */
open class RowItem(context: Context?) : TextView(context) {
    init {
        gravity = Gravity.CENTER
        this.layoutParams = LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f )
    }
}