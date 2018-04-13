package com.markcadag.movieviewer.ui.custom.tagview

import com.plumillonforge.android.chipview.Chip

/**
 * Created by markcadag on 4/12/18.
 */
class SeatChip(private val mName: String) : Chip {
    override fun getText(): String {
        return mName
    }
}