package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context

/**
 * Created by markcadag on 4/8/18.
 */
class SeatView(context: Context?) : RowItem(context) {

    var name: String? = "";
    var seatStatus : SeatStatus? = SeatStatus.Available

    enum class SeatStatus {
        Available, Reserved, Selected
    }

}