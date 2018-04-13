package com.markcadag.movieviewer.ui.custom.tagview

import android.content.Context
import android.view.View
import com.markcadag.movieviewer.R
import com.plumillonforge.android.chipview.ChipViewAdapter

/**
 * Created by markcadag on 4/12/18.
 */
open class MainChipViewAdapter internal constructor(context: Context) : ChipViewAdapter(context) {

    override fun getLayoutRes(position: Int): Int {
        return R.layout.chip
    }

    override fun getBackgroundColor(position: Int): Int {
        return getColor(android.R.color.holo_red_dark)
    }

    override fun getBackgroundColorSelected(position: Int): Int {
        return 0
    }

    override fun getBackgroundRes(position: Int): Int {
        return 0
    }

    override fun onLayout(view: View, position: Int) {}
}