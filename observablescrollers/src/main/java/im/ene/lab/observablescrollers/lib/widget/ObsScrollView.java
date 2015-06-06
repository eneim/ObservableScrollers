package im.ene.lab.observablescrollers.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 5/29/15.
 */
public class ObsScrollView extends ScrollView implements Scrollable {

    private static final String TAG = LogHelper.createLogTag(ObsScrollView.class);

    private int mCurrentScrollY;

    private int diffY;

    private int mLastMotionEvent = -1;

    private final int TOUCH_RUNNABLE_DELAY = 100;

    private TouchEventRunnable mTouchRunnable;

    private OnScrollObservedListener mScrollListener;

    private ScrollState mLastScrollState = ScrollState.SCROLL_STATE_IDLE;

    private ScrollState mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;

    /**
     * if user is touching to this view or not, set in ObsScrollView#onTouchEvent(MotionEvent ev);
     */
    private boolean isTouching = false;

    public ObsScrollView(Context context) {
        super(context);
    }

    public ObsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObsScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnScrollObservedListener(OnScrollObservedListener listener) {
        this.mScrollListener = listener;
    }

    void reportScrollStateChange(ScrollState newState) {
        if (newState != mLastScrollState) {
            mLastScrollState = newState;
            if (mScrollListener != null) {
                mScrollListener.onScrollStateChanged(this, newState);
            }

            LogHelper.d(TAG, "last scroll state: " + mLastScrollState.getState());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return isClickable() || isLongClickable();
        }

        final MotionEvent vtev = MotionEvent.obtain(ev);
        final int actionMasked = ev.getActionMasked();

        if (actionMasked == MotionEvent.ACTION_DOWN)
            isTouching = true;
        else if (actionMasked == MotionEvent.ACTION_UP || mLastMotionEvent == MotionEvent.ACTION_CANCEL)
            isTouching = false;

        if (actionMasked != mLastMotionEvent) {
            if (mTouchRunnable != null) removeCallbacks(mTouchRunnable);
            mTouchRunnable = new TouchEventRunnable();
            post(mTouchRunnable);
            mLastMotionEvent = actionMasked;
        }

        vtev.recycle();
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int old_l, int old_t) {
        mCurrentScrollY = t;
        diffY = t - old_t;
        if (mLastScrollState != ScrollState.SCROLL_STATE_IDLE)
            if (this.mScrollListener != null)
                this.mScrollListener.onScrollChanged(this, l - old_l, t - old_t);

        LogHelper.d(TAG, "current scroll y: " + diffY);
        super.onScrollChanged(l, t, old_l, old_t);
    }

    private void updateScrollState() {
        switch (mLastMotionEvent) {
            case MotionEvent.ACTION_MOVE:
                if (isTouching)
                    mExpectedScrollSate = ScrollState.SCROLL_STATE_TOUCH_SCROLL;
                break;
            case MotionEvent.ACTION_UP:
                mExpectedScrollSate = ScrollState.SCROLL_STATE_FLING;
                break;
            default:
                break;
        }

        if (mExpectedScrollSate == ScrollState.SCROLL_STATE_TOUCH_SCROLL) {
            if (diffY < -2) {
                mExpectedScrollSate = ScrollState.SCROLL_STATE_NEGATIVE;
            } else if (diffY > 2)
                mExpectedScrollSate = ScrollState.SCROLL_STATE_POSITIVE;
        } else {
            if (diffY * diffY <= 4) {
                mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;
            }
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (clampedY) {
            if (mTouchRunnable != null) removeCallbacks(mTouchRunnable);
            mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;
            reportScrollStateChange(mExpectedScrollSate);
        }
    }

    private class TouchEventRunnable implements Runnable {

        @Override
        public void run() {
            updateScrollState();
            reportScrollStateChange(mExpectedScrollSate);
            postDelayed(this, TOUCH_RUNNABLE_DELAY);
        }
    }

    @Override
    public int getHorizontalScrollOffset() {
        return getScrollX();
    }

    @Override
    public int getVerticalScrollOffset() {
        return getScrollY();
    }

    @Override
    public void scrollVerticallyTo(int y) {
        scrollTo(0, y);
    }

    @Override
    public void scrollVerticallyBy(int y) {
        scrollBy(0, y);
    }
}
