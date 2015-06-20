package im.ene.lab.observablescrollers.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import im.ene.lab.observablescrollers.lib.util.ListViewScrollTracker;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 6/5/15.
 */
public class ObsListView extends ListView implements Scrollable {
    private static final String TAG = LogHelper.createLogTag(ObsListView.class);
    private static final int SMOOTHLY_SCROLL_DURATION = 200;

    public ObsListView(Context context) {
        super(context);
    }

    public ObsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObsListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ListViewScrollTracker mScrollTracker;

    private OnScrollObservedListener mScrollListener;

    private ScrollState mLastScrollState = ScrollState.SCROLL_STATE_IDLE;

    private ScrollState mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;

    public void setOnScrollObservedListener(OnScrollObservedListener listener) {
        this.mScrollListener = listener;
    }

    private int mLastScrollY;

    private int diffY;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mScrollTracker = new ListViewScrollTracker(this);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;
                        break;
                    case SCROLL_STATE_FLING:
                        mExpectedScrollSate = ScrollState.SCROLL_STATE_FLING;
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        mExpectedScrollSate = ScrollState.SCROLL_STATE_TOUCH_SCROLL;
                        break;
                }

                if (mExpectedScrollSate != ScrollState.SCROLL_STATE_IDLE) {
                    if (diffY < 0) {
                        mExpectedScrollSate = ScrollState.SCROLL_STATE_NEGATIVE;
                    } else
                        mExpectedScrollSate = ScrollState.SCROLL_STATE_POSITIVE;
                }

                reportScrollStateChange(mExpectedScrollSate);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                diffY = -mScrollTracker.calculateIncrementalOffset(firstVisibleItem, visibleItemCount);
                if (mLastScrollState != ScrollState.SCROLL_STATE_IDLE)
                    if (mScrollListener != null)
                        mScrollListener.onScrollChanged(ObsListView.this, 0, diffY);
                mLastScrollY = mScrollTracker.getVerticalScroll(firstVisibleItem, visibleItemCount);

                LogHelper.d(TAG, "mLastScrollY: " + mLastScrollY);
            }
        });
    }

    void reportScrollStateChange(ScrollState newState) {
        if (newState != mLastScrollState) {
            mLastScrollState = newState;
            if (mScrollListener != null) {
                mScrollListener.onScrollStateChanged(this, newState);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        setOnScrollListener(null);
        if (mScrollTracker != null)
            mScrollTracker.clear();
        mScrollTracker = null;
        super.onDetachedFromWindow();
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public int getHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int getVerticalScrollOffset() {
        return mLastScrollY;
    }

    @Override
    public void scrollVerticallyTo(int y) {
        smoothScrollToPosition(y);
    }

    @Override
    public void scrollVerticallyBy(int y) {
        smoothScrollBy(y, SMOOTHLY_SCROLL_DURATION);
    }
}
