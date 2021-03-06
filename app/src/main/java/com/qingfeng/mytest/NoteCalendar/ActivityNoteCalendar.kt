package com.qingfeng.mytest.NoteCalendar

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qingfeng.mytest.BR
import com.qingfeng.mytest.R
import com.qingfeng.mytest.common.CommonRecyclerViewAdapter
import com.qingfeng.mytest.databinding.ActivityNoteCalendarBinding
import kotlinx.android.synthetic.main.activity_note_calendar.*
import java.util.*
import kotlin.collections.ArrayList

class ActivityNoteCalendar : AppCompatActivity() {

    private var mYear = 0
    private val mNewNote = ModelNote()
    private var mNotes = ArrayList<ModelNote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNoteCalendarBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_calendar)
        binding.setVariable(BR.noteItem, mNewNote)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        val calView = calendarView
        tv_month_day.text = "${calView.curMonth} 月${calView.curDay}日"
        tv_month_day.setOnClickListener({
            if (calendarLayout.isExpand) {
                calView.showSelectLayout(mYear)
                return@setOnClickListener
            }
            calView.showSelectLayout(mYear)
            tv_lunar.visibility = View.GONE
            tv_month_day.text = "$mYear"
        })

        fl_current.setOnClickListener({
            calView.scrollToCurrent()
        })

        calView.setOnDateSelectedListener({ calendar, isClick ->
            tv_lunar.visibility = View.VISIBLE
            tv_month_day.text = "${calendar.month}月${calendar.day}日"
            tv_lunar.text = calendar.lunar
            mYear = calendar.year
        })
    }

    private fun initRecyclerView() {
        rvNotes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvNotes.adapter = CommonRecyclerViewAdapter(this, mNotes, R.layout.item_note, BR.noteItem)
    }
}
