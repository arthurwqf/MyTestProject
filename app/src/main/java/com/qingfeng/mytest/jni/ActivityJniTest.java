package com.qingfeng.mytest.jni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qingfeng.mytest.R;

public class ActivityJniTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);

        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(JniMethod.getHello());
    }
}
