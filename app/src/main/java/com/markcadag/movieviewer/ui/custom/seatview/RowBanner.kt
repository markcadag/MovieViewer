package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.markcadag.movieviewer.R

/**
 * Created by markcadag on 4/8/18.
 */
open class RowBanner(context: Context?) : TextView(context) {

    init {
        this.text = context?.getString(R.string.movie_screen)
        gravity = Gravity.CENTER

        val padding = context?.resources?.getDimensionPixelSize(R.dimen._12sdp)
        padding?.let {
            setPadding(it, it, it, it)
        }

        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setBackgroundResource(R.drawable.grey_rect_o)

    }
}