package com.markcadag.movieviewer.model
import com.google.gson.annotations.SerializedName


/**
 * Created by markcadag on 4/8/18.
 */

data class Schedule(
		@SerializedName("dates") var dates: List<Date?>? = listOf(),
		@SerializedName("cinemas") var cinemas: List<CinemaRoot?>? = listOf(),
		@SerializedName("times") var times: List<TimeRoot?>? = listOf()
)

data class CinemaRoot(
		@SerializedName("parent") var parent: String? = "", //20170405
		@SerializedName("cinemas") var cinemas: List<Cinema?>? = listOf()
)

data class Cinema(
		@SerializedName("id") var id: String? = "", //20170405-3
		@SerializedName("cinema_id") var cinemaId: String? = "", //3
		@SerializedName("label") var label: String? = "" //Cinema 3
)

data class TimeRoot(
		@SerializedName("parent") var parent: String? = "", //20170405-3
		@SerializedName("times") var times: List<Time?>? = listOf()
)

data class Time(
		@SerializedName("id") var id: String? = "", //12:15
		@SerializedName("label") var label: String? = "", //12:15 PM
		@SerializedName("schedule_id") var scheduleId: String? = "", //3::20170405,Rl5c058UQWGJAJO87G5xuA,None
		@SerializedName("popcorn_price") var popcornPrice: String? = "", //0
		@SerializedName("popcorn_label") var popcornLabel: String? = "", //Popcorn Bucket
		@SerializedName("seating_type") var seatingType: String? = "", //Reserved Seating
		@SerializedName("price") var price: String? = "", //280
		@SerializedName("variant") var variant: Any? = Any() //null
)

data class Date(
		@SerializedName("id") var id: String? = "", //20170405
		@SerializedName("label") var label: String? = "", //Apr 05, Wed
		@SerializedName("date") var date: String? = "" //2017-04-05
)