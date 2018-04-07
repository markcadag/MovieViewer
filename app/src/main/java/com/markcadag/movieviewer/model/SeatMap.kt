package com.markcadag.movieviewer.model
import com.google.gson.annotations.SerializedName


/**
 * Created by markcadag on 4/7/18.
 */

data class SeatMap(
		@SerializedName("seatmap") var seatmap: List<List<String?>?>? = listOf(),
		@SerializedName("available") var available: Available? = Available()
)

data class Available(
		@SerializedName("seats") var seats: List<String?>? = listOf(),
		@SerializedName("seat_count") var seatCount: Int? = 0 //419
)