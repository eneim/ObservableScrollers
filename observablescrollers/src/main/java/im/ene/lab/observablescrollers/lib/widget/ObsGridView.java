package im.ene.lab.observablescrollers.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 6/9/15.
 *
 * I'm not about to support these legacy classes.
 * I expect the usage of new RecyclerView
 */

@Deprecated
public class ObsGridView extends GridView implements Scrollable {
    public ObsGridView(Context context) {
        super(context);
    }

    public ObsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObsGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObsGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private OnScrollObservedListener mOnScrollObservedListener;

    @Override
    public void setOnScrollObservedListener(OnScrollObservedListener listener) {
        mOnScrollObservedListener = listener;
    }

    @Override
    public int getHorizontalScrollOffset() {
        return 0;
    }

    @Override
    public int getVerticalScrollOffset() {
        return 0;
    }

    @Override
    public void scrollVerticallyTo(int y) {

    }

    @Override
    public void scrollVerticallyBy(int y) {

    }

    @Override
    public ScrollState getCurrentScrollState() {
        return ScrollState.SCROLL_STATE_IDLE;
    }
}
