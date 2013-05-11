package com.android.contacts.dialpad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class DialpadLayout extends FrameLayout {
    private static final int MIN_SWIPE_DOWN_DISTANCE = 200;
    private static final int MIN_SWIPE_VELOCITY = 800;

    private DialpadFragment mParent;
    private float mStartX;
    private float mStartY;
    private long mStartTime;

    public DialpadLayout(Context context) {
        this(context, null);
    }

    public DialpadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialpadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParent(DialpadFragment parent) {
        mParent = parent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                mStartTime = System.currentTimeMillis();
                return false;
            case MotionEvent.ACTION_UP:
                float dx = ev.getX() - mStartX;
                float dy= ev.getY() - mStartY;
                float dt = (float)(System.currentTimeMillis() - mStartTime) / 1000f;
                float v = dy / dt;
                if (mParent != null && v >= MIN_SWIPE_VELOCITY)
                    mParent.animateT9(true);
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
