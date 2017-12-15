package com.qingfeng.mytest.algorithm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingfeng.mytest.R;

public class ActivitySort extends AppCompatActivity implements View.OnClickListener {
    private static final int[] arr = new int[]{12, 45, 2, 10, 34, 23, 77, 20, 4, 1, 90, 3, 22};
    private int[] tempArr;
    private TextView tvOriginal;
    private LinearLayout llLog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        mContext = this;
        initView();
        tempArr = arr.clone();
        tvOriginal.setText("原数组:" + getStringByArr(arr));
    }

    private void initView() {
        tvOriginal = (TextView) findViewById(R.id.tv_original);
        llLog = (LinearLayout) findViewById(R.id.ll_log);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tempArr = arr.clone();
        llLog.removeAllViews();
        switch (v.getId()) {
            case R.id.btn1:
                //冒泡排序
                bubbleSort();
                break;
            case R.id.btn2:
                //选择排序
                selectionSort();
                break;
            case R.id.btn3:
                insertSort();
                break;
            case R.id.btn4:
                shellSort();
                break;
        }
    }

    private String getStringByArr(int[] ar) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            builder.append(ar[i]);
            if (i < ar.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private void addLog(String text) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        llLog.addView(textView);
    }

    private void bubbleSort() {
        int temp;
        boolean isChanged = false;
        for (int i = 0; i < tempArr.length; i++) {
            for (int j = tempArr.length - 1; j > i; j--) {
                if (tempArr[j] < tempArr[j - 1]) {
                    temp = tempArr[j];
                    tempArr[j] = tempArr[j - 1];
                    tempArr[j - 1] = temp;
                    isChanged = true;
                }
            }
            addLog(getStringByArr(tempArr));
            if (!isChanged) {
                break;
            }
        }
    }

    private void selectionSort() {
        int minIndex;
        for (int i = 0; i < tempArr.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < tempArr.length; j++) {
                if (tempArr[j] < tempArr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = tempArr[i];
                tempArr[i] = tempArr[minIndex];
                tempArr[minIndex] = temp;
            }
            addLog(getStringByArr(tempArr));
        }
    }

    private void insertSort() {
        int temp;
        for (int i = 0; i < tempArr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (tempArr[j] < tempArr[j - 1]) {
                    temp = tempArr[j - 1];
                    tempArr[j - 1] = tempArr[j];
                    tempArr[j] = temp;
                } else {
                    break;
                }
            }
            addLog(getStringByArr(tempArr));
        }

    }

    private void shellSort() {
        int temp;
        int incre = tempArr.length;
        while (true) {
            incre = incre / 2;
            for (int i = 0; i < incre; i++) {
                for (int j = i + incre; j < tempArr.length; j += incre) {
                    for (int k = j; k > i; k -= incre) {
                        if (tempArr[k] < tempArr[k - incre]) {
                            temp = tempArr[k - incre];
                            tempArr[k - incre] = tempArr[k];
                            tempArr[k] = temp;
                        } else {
                            break;
                        }
                    }
                }
                addLog(getStringByArr(tempArr));
            }
            if (incre == 1) {
                break;
            }
        }
    }
}
