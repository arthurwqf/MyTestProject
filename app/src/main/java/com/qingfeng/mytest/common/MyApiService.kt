package com.qingfeng.mytest.common

import com.qingfeng.mytest.bean.ModelMovie

import io.reactivex.Observable
import okhttp3.Response
import retrofit2.http.GET

/**
 * Created by WangQF on 2017/12/20 0020.
 */

interface MyApiService {
    @GET("/v2/movie/top250")
    fun requestGetData(): Observable<Response>
}
