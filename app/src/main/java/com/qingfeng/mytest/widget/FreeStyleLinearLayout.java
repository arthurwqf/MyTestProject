package com.qingfeng.mytest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.qingfeng.mytest.R;

/**
 * Created by WangQF on 2018/1/23 0023.
 */

public class FreeStyleLinearLayout extends LinearLayout {
    //    private Paint paint;
    private int width, height;
    private float cornerRadius; //圆角半径
    private Path mPath;
    private float[] mRadius;

    public FreeStyleLinearLayout(Context context) {
        super(context);
        init(null);
    }

    public FreeStyleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FreeStyleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.FreeStyleLinearLayout);
        cornerRadius = array.getDimension(R.styleable.FreeStyleLinearLayout_corner_radius, -1);
        float topLeftRadius = array.getDimension(R.styleable.FreeStyleLinearLayout_top_left_radius, -1);
        float topRightRadius = array.getDimension(R.styleable.FreeStyleLinearLayout_top_tight_radius, -1);
        float bottomLeftRadius = array.getDimension(R.styleable.FreeStyleLinearLayout_bottom_left_radius, -1);
        float bottomRightRadius = array.getDimension(R.styleable.FreeStyleLinearLayout_bottom_right_radius, -1);
        int bgColor = array.getColor(R.styleable.FreeStyleLinearLayout_bg_color, Color.WHITE);
        array.recycle();

        if (cornerRadius >= 0) {
            topLeftRadius = cornerRadius;
            topRightRadius = cornerRadius;
            bottomLeftRadius = cornerRadius;
            bottomRightRadius = cornerRadius;
        }

//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(bgColor);
        setWillNotDraw(false);
        mPath = new Path();

        mRadius = new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.addRoundRect(new RectF(0, 0, width, height), mRadius, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
