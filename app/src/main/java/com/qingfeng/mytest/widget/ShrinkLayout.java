package com.qingfeng.mytest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.qingfeng.mytest.R;

/**
 * Created by WangQF on 2018/1/17 0017.
 */

public class ShrinkLayout extends LinearLayout {
    private int mBgColor = Color.parseColor("#33000000");
    private int mInitialWidth, mFinalWidth, mInitialHeight, mFinalHeight;
    private float mCurrentWidth, mCurrentHeight;
    private float mInitialRadius, mFinalRadius, mCurrentRadius;
    private Paint mPaint;

    public ShrinkLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShrinkLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet atts) {
        if (atts != null) {
            TypedArray ta = context.obtainStyledAttributes(atts, R.styleable.ShrinkLayout);
            mInitialWidth = ta.getDimensionPixelSize(R.styleable.ShrinkLayout_shrink_init_width, -1);
            mFinalWidth = ta.getDimensionPixelOffset(R.styleable.ShrinkLayout_shrink_final_width, -1);
            if (mFinalWidth == -1 && mInitialWidth != -1) {
                mFinalWidth = mInitialWidth;
            } else if (mInitialWidth == -1 && mFinalWidth != -1) {
                mInitialWidth = mFinalWidth;
            }
            mInitialHeight = ta.getDimensionPixelSize(R.styleable.ShrinkLayout_shrink_init_height, -1);
            mFinalHeight = ta.getDimensionPixelOffset(R.styleable.ShrinkLayout_shrink_final_height, -1);
            if (mFinalHeight == -1 && mInitialHeight != -1) {
                mFinalHeight = mInitialHeight;
            } else if (mInitialHeight == -1 && mFinalHeight != -1) {
                mInitialHeight = mFinalHeight;
            }
            mInitialRadius = ta.getDimensionPixelSize(R.styleable.ShrinkLayout_shrink_init_radius, -1);
            mFinalRadius = ta.getDimensionPixelOffset(R.styleable.ShrinkLayout_shrink_final_radius, -1);
            if (mFinalRadius == -1 && mInitialRadius != -1) {
                mFinalRadius = mInitialRadius;
            } else if (mInitialRadius == -1 && mFinalRadius != -1) {
                mInitialRadius = mFinalRadius;
            }

            mBgColor = ta.getColor(R.styleable.ShrinkLayout_shrink_bg_color, mBgColor);
            ta.recycle();
        }

        mCurrentRadius = mInitialRadius;
        mCurrentWidth = mInitialWidth;
        mCurrentHeight = mInitialHeight;
        initPaint();
        setWillNotDraw(false);
    }

    @Override
    public void setWillNotDraw(boolean willNotDraw) {
        super.setWillNotDraw(willNotDraw);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mBgColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) mCurrentHeight;
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) mCurrentWidth;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, mCurrentWidth, mCurrentHeight, mCurrentRadius, mCurrentRadius, mPaint);
        super.onDraw(canvas);
    }

    public void setScale(float scale) {
        if (mInitialHeight < mFinalHeight) {
            mCurrentHeight = mInitialHeight + (mFinalHeight - mInitialHeight) * scale;
        } else if (mInitialHeight > mFinalHeight) {
            mCurrentHeight = mInitialHeight - (mInitialHeight - mFinalHeight) * scale;
        }

        if (mInitialWidth < mFinalWidth) {
            mCurrentWidth = mInitialWidth + (mFinalWidth - mInitialWidth) * scale;
        } else if (mInitialWidth > mFinalWidth) {
            mCurrentWidth = mInitialWidth - (mInitialWidth - mFinalWidth) * scale;
        }

        if (mInitialRadius < mFinalRadius) {
            mCurrentRadius = mInitialRadius + (mFinalRadius - mInitialRadius) * scale;
        } else if (mInitialRadius > mFinalRadius) {
            mCurrentRadius = mInitialRadius - (mInitialRadius - mFinalRadius) * scale;
        }

        requestLayout();
//        invalidate();
    }
}
