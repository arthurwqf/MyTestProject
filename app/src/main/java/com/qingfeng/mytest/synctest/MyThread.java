package com.qingfeng.mytest.synctest;

import android.util.Log;

/**
 * Created by WangQF on 2018/1/9 0009.
 */

public class MyThread implements Runnable {
    private ModelTemp a, b;
    private static int i = 0;
    private String name;

    public MyThread(String name, ModelTemp a, ModelTemp b) {
        this.name = name;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        while (i < 10) {
            synchronized (a) {
                synchronized (b) {
                    Log.d("test", "run: " + name + "***" + i);
                    i++;
                    b.notify();
                }
                try {
                    a.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
