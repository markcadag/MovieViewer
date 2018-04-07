package com.markcadag.movieviewer.model
import com.google.gson.annotations.SerializedName

/**
 * Created by markcadag on 4/7/18.
 */
data class Movie(
		@SerializedName("movie_id") var movieId: String? = "", //465e5cd3-9f14-4161-8900-93bcec6e71b8
		@SerializedName("advisory_rating") var advisoryRating: String? = "", //PG
		@SerializedName("canonical_title") var canonicalTitle: String? = "", //Ghost In The Shell
		@SerializedName("cast") var cast: List<String?>? = listOf(),
		@SerializedName("genre") var genre: String? = "", //Action, Crime, Drama
		@SerializedName("has_schedules") var hasSchedules: Int? = 0, //1
		@SerializedName("is_inactive") var isInactive: Int? = 0, //0
		@SerializedName("is_showing") var isShowing: Int? = 0, //1
		@SerializedName("link_name") var linkName: String? = "", //ghost-shell
		@SerializedName("poster") var poster: String? = "", //http://lh3.googleusercontent.com/hkpMC-4LxMoR07ZAbpKgu_6haxlP8WTvp7t5I8eusOHLAqVeEaGA14Wom9hhikRaQUo0zy1xHQzhQf81Xq-G_BURkak
		@SerializedName("poster_landscape") var posterLandscape: String? = "", //http://lh3.googleusercontent.com/7kEQdTRBBqkSM3MIDmnmf5xi02NpAe-fb1RIxiiaj4hbvQUwVXHGfnFhn_gNL4rPudhTKy84eva7EJwCeaAkoJklpg
		@SerializedName("ratings") var ratings: Ratings? = Ratings(),
		@SerializedName("release_date") var releaseDate: String? = "", //2017-03-29
		@SerializedName("runtime_mins") var runtimeMins: String? = "", //150.00
		@SerializedName("synopsis") var synopsis: String? = "", //A cyborg policewoman attempts to bring down a nefarious computer hacker.
		@SerializedName("trailer") var trailer: String? = "", //tRkb1X9ovI4
		@SerializedName("average_rating") var averageRating: Any? = Any(), //null
		@SerializedName("total_reviews") var totalReviews: Any? = Any(), //null
		@SerializedName("variants") var variants: List<String?>? = listOf(),
		@SerializedName("theater") var theater: String? = "", //Glorietta 4
		@SerializedName("order") var order: Int? = 0, //0
		@SerializedName("is_featured") var isFeatured: Int? = 0, //0
		@SerializedName("watch_list") var watchList: Boolean? = false, //false
		@SerializedName("your_rating") var yourRating: Int? = 0 //0
)

//TODO dunno what to add or is just an empty class?
class Ratings