package com.qingfeng.mytest.kotlin

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.qingfeng.mytest.BR
import com.qingfeng.mytest.R
import com.qingfeng.mytest.bean.ModelMovie
import com.qingfeng.mytest.common.CommonRecyclerViewAdapter
import com.qingfeng.mytest.common.MyApiService
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_kotlin_test.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ActivityKotlinTest : RxAppCompatActivity() {
    private lateinit var mAdapter: CommonRecyclerViewAdapter
    private var mData = arrayListOf<ModelMovie>()
    private var mContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        init()
    }

    private fun init() {
        ctl_move.title = "Movie"
        mAdapter = CommonRecyclerViewAdapter(mContext, mData, R.layout.item_movie, BR.movie)
        rv_move.layoutManager = LinearLayoutManager(mContext)
        rv_move.adapter = mAdapter

        getData()
    }

    private fun getData() {
        var retrofit = Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(MyApiService::class.java)
                .requestGetData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    //onNext
                    result ->
                    Log.d("test", result.toString())
                }, {
                    //onError
                    error ->
                    Log.d("test", error.toString())
                }, {
                    //onComplete

                }, {
                    //onSubscribe
                })
    }
}
