package im.ene.lab.observablescrollers.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

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

    private OnScrollObservedListener mScrollListener;

    public void setOnScrollObservedListener(OnScrollObservedListener listener) {
        this.mScrollListener = listener;
    }

    private int mLastScrollY;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        LogHelper.d(TAG, "OnScrollListener#onScrollStateChanged(): " + "idle");
                        break;
                    case SCROLL_STATE_FLING:
                        LogHelper.d(TAG, "OnScrollListener#onScrollStateChanged(): " + "fling");
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        LogHelper.d(TAG, "OnScrollListener#onScrollStateChanged(): " + "touch scrolling");
                        break;
                }
                LogHelper.d(TAG, "first child top: " + view.getChildAt(0).getTop());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                LogHelper.d(TAG, "OnScrollListener#onScroll(): " + getVerticalScrollOffset());
                if (mScrollListener != null)
                    mScrollListener.onScrollChanged(ObsListView.this, 0, ObsListView.super.computeVerticalScrollExtent());    // TODO
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        LogHelper.d(TAG, "View#onScrollChanged(): " + ObsListView.super.computeVerticalScrollExtent());
    }

    @Override
    protected void onDetachedFromWindow() {
        setOnScrollListener(null);
        super.onDetachedFromWindow();
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
        smoothScrollToPosition(y);
    }

    @Override
    public void scrollVerticallyBy(int y) {
        smoothScrollBy(y, SMOOTHLY_SCROLL_DURATION);
    }
}
