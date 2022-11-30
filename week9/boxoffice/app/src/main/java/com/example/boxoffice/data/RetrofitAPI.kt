package com.example.boxoffice.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json")
    fun getMovie(
        @Query("key")key:String,
        @Query("weekGb")weekGb:Int,
        @Query("targetDt")targetDt:Int,
        @Query("itemPerPage")itemPerPage:Int
    ) : Call<Movie>
}