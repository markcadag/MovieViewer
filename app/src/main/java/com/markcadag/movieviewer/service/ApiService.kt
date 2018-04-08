package com.markcadag.movieviewer.service

import com.markcadag.movieviewer.model.Movie
import com.markcadag.movieviewer.model.ScheduleResp
import com.markcadag.movieviewer.model.SeatMap
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by markcadag on 4/7/18.
 */
interface ApiService {

    @GET("/movie.json")
    fun  getMovieDetails(): Observable<Response<Movie>>

    @GET("/seatmap.json")
    fun  getSeatMap(): Observable<Response<SeatMap>>

    @GET("/schedule.json")
    fun  getSchedule(): Observable<Response<ScheduleResp>>
}