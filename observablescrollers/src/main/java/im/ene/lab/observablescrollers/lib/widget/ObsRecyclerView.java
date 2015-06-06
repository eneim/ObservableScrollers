package im.ene.lab.observablescrollers.lib.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 5/31/15.
 */
public class ObsRecyclerView extends RecyclerView implements Scrollable {

    private static final String TAG = LogHelper.createLogTag(ObsRecyclerView.class);

    private LinearLayoutManager mLayoutManager;

    public ObsRecyclerView(Context context) {
        super(context);
    }

    public ObsRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObsRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int diffY;

    private OnScrollObservedListener mScrollListener;

    private ScrollState mLastScrollState = ScrollState.SCROLL_STATE_IDLE;

    private ScrollState mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;

    public void setOnScrollObservedListener(OnScrollObservedListener listener) {
        this.mScrollListener = listener;
    }

    @Deprecated
    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        throw new IllegalArgumentException("This method is no longer supported, please use RecyclerView#addOnScrollListener() instead");
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        super.addOnScrollListener(listener);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        diffY = dy;
        if (mLastScrollState != ScrollState.SCROLL_STATE_IDLE)
            if (this.mScrollListener != null)
                this.mScrollListener.onScrollChanged(this, dx, dy);

        super.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case SCROLL_STATE_IDLE:
                mExpectedScrollSate = ScrollState.SCROLL_STATE_IDLE;
                break;
            case SCROLL_STATE_DRAGGING:
                mExpectedScrollSate = ScrollState.SCROLL_STATE_TOUCH_SCROLL;
                break;
            case SCROLL_STATE_SETTLING:
                mExpectedScrollSate = ScrollState.SCROLL_STATE_FLING;
                break;
            default:
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
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager))
            throw new IllegalArgumentException("this class must have a LinearLayoutManager");

        mLayoutManager = (LinearLayoutManager) layout;
        super.setLayoutManager(layout);
    }

    @Override
    public int getHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int getVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
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
