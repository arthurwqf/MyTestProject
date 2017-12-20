package com.qingfeng.mytest.step;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.qingfeng.mytest.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 计步
 */
public class ActivityStep extends AppCompatActivity {
    private TextView mtvStepCount;
    private IMyStepInterface myStepManager;
    private boolean mBound = false;

    private ServiceConnection mSConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            myStepManager = IMyStepInterface.Stub.asInterface(service);
            if (myStepManager != null) {
                startStep();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mtvStepCount = (TextView) findViewById(R.id.tv_step);
    }

    @Override
    protected void onStart() {
        super.onStart();
        attemptToBindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound)
            unbindService(mSConnection);
    }

    private void attemptToBindService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, mSConnection, Context.BIND_AUTO_CREATE);
    }

    private void startStep() {
        Observable.interval(0, 10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("test", "accept: " + myStepManager.getStepCount());
                        mtvStepCount.setText(myStepManager.getStepCount() + "");
                    }
                });
    }
}
