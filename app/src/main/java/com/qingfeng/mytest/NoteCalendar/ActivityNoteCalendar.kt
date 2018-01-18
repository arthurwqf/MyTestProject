package com.qingfeng.mytest.NoteCalendar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_note_calendar.*

class ActivityNoteCalendar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_calendar)
    }

    fun doShrink(view: View) {
        shrink_layout.setScale(java.lang.Float.parseFloat(et_scale.text.toString()))
    }
}
