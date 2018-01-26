package com.qingfeng.mytest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by WangQF on 2018/1/18 0018.
 */

public class NumberTextView extends LinearLayout {
    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.BLACK);
            textView.setText(i + "");
            textView.setTextSize(10);
            addView(textView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
