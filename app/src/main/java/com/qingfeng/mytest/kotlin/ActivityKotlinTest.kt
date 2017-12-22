package com.qingfeng.mytest.kotlin

import android.graphics.Color
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.qingfeng.mytest.BR
import com.qingfeng.mytest.R
import com.qingfeng.mytest.bean.ModelMovie
import com.qingfeng.mytest.common.CommonMethod
import com.qingfeng.mytest.common.CommonRecyclerViewAdapter
import com.qingfeng.mytest.common.MyApiService
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
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
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val params = toolbar.layoutParams as FrameLayout.LayoutParams
        params.topMargin = CommonMethod.getStatusBarHeight(mContext)
        toolbar.layoutParams = params

        setSupportActionBar(toolbar)
        val actBar = supportActionBar
        if (actBar != null) {
            actBar.setDisplayHomeAsUpEnabled(true)
            actBar.setDisplayShowTitleEnabled(false)
        }

        toolbar.title = "Movie"
        ctl_move.setCollapsedTitleTextColor(Color.WHITE)
        ctl_move.setExpandedTitleColor(Color.WHITE)
        mAdapter = CommonRecyclerViewAdapter(mContext, mData, R.layout.item_movie_waterfall, BR.movie_waterfall)
        rv_move.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_move.adapter = mAdapter
        toolbar.setNavigationOnClickListener({
            onBackPressed()
        })

        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_kotlin, menu)
        return true
    }

    /**
     * 获取列表信息
     */
    private fun getData() {
        var retrofit = Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(MyApiService::class.java)
                .requestGetData()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    ll_loading.visibility = View.VISIBLE
                })
                .subscribe({
                    //onNext
                    result ->
                    mData.clear()
                    mData.addAll(result.subjects)
                    mAdapter.notifyDataSetChanged()
                }, {
                    //onError
                    error ->
                    Log.d("test", "error:" + error.toString())
                    ll_loading.visibility = View.GONE
                }, {
                    //onComplete
                    Log.d("test", "complete")
                    ll_loading.visibility = View.GONE
                }, {
                    //onSubscribe
                    disposable ->
                    Log.d("test", "Subscribe")
                })
    }


}
