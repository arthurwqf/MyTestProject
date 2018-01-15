package com.qingfeng.mytest.synctest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qingfeng.mytest.R
import kotlinx.android.synthetic.main.activity_sync_test.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ActivitySyncTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_test)

        val a = ModelTemp()
        val b = ModelTemp()
        val c = ModelTemp()
        val thread1 = MyThread("t1", c, a)
        val thread2 = MyThread("t2", a, b)
        val thread3 = MyThread("t3", b, c)

        Thread(thread1).start()
        Thread(thread2).start()
        Thread(thread3).start()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onHandlerMsg(msg: ModelMyMsg) {
        tv_sync.append("${msg.name}----${msg.count}\r\n")
    }

    class MyThread(val name: String, val a: ModelTemp, private val b: ModelTemp) : Runnable {
        companion object {
            private var i = 0
        }

        override fun run() {
            while (i < 10) {
                synchronized(a) {
                    synchronized(b) {
                        EventBus.getDefault().post(ModelMyMsg(name, i))
                        i++
                        b.notify()
                    }
                    a.wait()
                    Thread.sleep(1000)
                }
            }
        }
    }

    class ModelMyMsg(name: String, count: Int) {
        var name: String
            internal set
        var count: Int
            internal set

        init {
            this.name = name
            this.count = count
        }
    }

    class ModelTemp : java.lang.Object()
}
