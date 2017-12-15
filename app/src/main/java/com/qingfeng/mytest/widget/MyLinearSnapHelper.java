package com.qingfeng.mytest.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by WangQF on 2017/11/20 0020.
 */

public class MyLinearSnapHelper {

    private float mScale = 0.4f; // 视图放大scale
    private int mPagePadding = 15; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private int mShowLeftCardWidth = 15;   // 左边卡片显示大小

    private int mOnePageWidth; // 滑动一页的距离

    private int mCurrentItemPos;
    private int mCurrentAllOffset;

    private RecyclerView mRecyclerView;
    private Context mContext;
    private CardLinearSnapHelper mSnapHelper = new CardLinearSnapHelper();
    private int direction;
    private OnItemScrollListener mScrollListener;
    private ViewPager mViewPager;
    private int mMoveStatus; //1：滑动recyclerView, 2:滑动viewPager;

    public void attachToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = recyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mSnapHelper.mNoNeedToScroll = mCurrentAllOffset == 0 ||
                            mCurrentAllOffset == getDestItemOffset(mRecyclerView.getAdapter().getItemCount() - 1);
                    computeCurrentItemPos(); //这里计算是因为:初始化recyclerView 的leftPadding时，如果不是整数会有误差
                    if (mScrollListener != null) {
                        mScrollListener.onItemSelected(mCurrentItemPos);
                    }
                    if (mMoveStatus == 1 && mViewPager != null) { //如果手指滑动的是RecyclerView
                        mViewPager.setCurrentItem(mCurrentItemPos);
                    }
                } else {
                    mSnapHelper.mNoNeedToScroll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                mCurrentAllOffset += dx;
                direction = dx;
                computeCurrentItemPos(dx); //这里计算是因为:快速滑动的时候需要实时改变当前的position
                onScrolledChangedCallback();
            }
        });

        initWidth();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMoveStatus = 1;
                return false;
            }
        });
    }

    public void setMoveStatus(int moveStatus) {
        this.mMoveStatus = moveStatus;
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mOnePageWidth <= 0) {
                    throw new RuntimeException("需要设置item width");
                } else {
                    int leftPadding = (mRecyclerView.getWidth() - mOnePageWidth) / 2;
                    mRecyclerView.setPadding(leftPadding, 0, leftPadding, 0);
                    mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
                    onScrolledChangedCallback();
                }
            }
        });
    }

    public void setCurrentItemPos(int currentItemPos) {
        this.mCurrentItemPos = currentItemPos;
    }

    public int getCurrentItemPos() {
        return mCurrentItemPos;
    }

    private int getDestItemOffset(int destPos) {
        return mOnePageWidth * destPos;
    }

    public void setItemWidth(int itemWidth) {
        mOnePageWidth = itemWidth;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mMoveStatus == 2) { //如果手指滑动的是viewPager
                    int targetPos = (int) (mOnePageWidth * position + (positionOffset * mOnePageWidth));
                    mRecyclerView.scrollBy(targetPos - (mCurrentAllOffset + mRecyclerView.getPaddingLeft()), 0);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMoveStatus = 2;
                return false;
            }
        });
    }

    /**
     * 计算mCurrentItemOffset
     */
    private void computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return;
        mCurrentItemPos = (mCurrentAllOffset + mRecyclerView.getPaddingLeft()) / mOnePageWidth;
        if ((mCurrentAllOffset + mRecyclerView.getPaddingLeft()) % mOnePageWidth > 0) {
            mCurrentItemPos++;
        }
    }

    private void computeCurrentItemPos(int dx) {
        if (mOnePageWidth <= 0) return;
        mCurrentItemPos = (mCurrentAllOffset + mRecyclerView.getPaddingLeft()) / mOnePageWidth;
        if (dx < 0) {
            mCurrentItemPos++;
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {
        int offset = mCurrentAllOffset + mRecyclerView.getPaddingLeft() - mCurrentItemPos * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

        if (mScrollListener != null) {
            mScrollListener.onScrolling(percent);
        }

        float expandScale = 1 + mScale * percent;
        float retractScale = 1 + mScale - mScale * percent;

        View currentView = null;
        View nextView = null;

        if (direction > 0) { //右
            currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
            if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
                nextView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
            }
        } else if (direction < 0) { //左
            currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
            if (mCurrentItemPos > 0) {
                nextView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
            }
        }

        if (nextView != null) {
            nextView.setScaleY(expandScale);
            nextView.setScaleX(expandScale);
        }
        if (currentView != null) {
            currentView.setScaleY(retractScale);
            currentView.setScaleX(retractScale);
        }
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public interface OnItemScrollListener {
        void onScrolling(float offsetPercent);

        void onItemSelected(int position);
    }
}
