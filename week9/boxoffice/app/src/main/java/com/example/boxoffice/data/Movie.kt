package com.example.boxoffice.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("boxOfficeResult")
    var boxofficeResult:BoxOfficeResult
)

data class BoxOfficeResult(
    @SerializedName("boxofficeType")
    var boxofficeType: String,
    @SerializedName("showRange")
    var showRang: String,
    @SerializedName("yearWeekTime")
    var yearWeekTime:String,
    @SerializedName("weeklyBoxOfficeList")
    var weeklyBoxOfficeList : List<WeeklyBoxOfficeList> = arrayListOf()
)

data class WeeklyBoxOfficeList (
    @SerializedName("rnum")
    var rnum: String?, //순번
    @SerializedName("rank")
    var rank : String?, //박스오피스 랭킹
    @SerializedName("movieNm")
    var movieNm: String?, //영화이름
    @SerializedName("openDt")
    var openDt: String?, //개봉일
    @SerializedName("audiAcc")
    var audiAcc: String?, //누적 관객수
): Serializable {

}