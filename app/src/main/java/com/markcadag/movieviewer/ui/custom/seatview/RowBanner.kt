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
        textSize = resources.getDimension(R.dimen._3ssp)
        gravity = Gravity.CENTER

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.0f;
        params.gravity = Gravity.TOP

        val space = context?.resources?.getDimensionPixelSize(R.dimen._8sdp)
        space?.let {
            setPadding(it, it, it, it)
            params.setMargins(it, it, it, it)
        }

        this.layoutParams = params
        this.setBackgroundResource(R.drawable.grey_rect_o)

    }
}