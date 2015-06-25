package im.ene.lab.obervablescrollers.sample.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsScrollView;


public class ObsScrollViewActivity extends BaseActivity {

    public static final String TAG = LogHelper.createLogTag(ObsScrollViewActivity.class);

    private ObsScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_scrollview);
        getActionbarToolbar();
        mScrollView = (ObsScrollView) findViewById(R.id.scrollview);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mScrollView.setOnScrollObservedListener(new OnScrollObservedListener() {
            @Override
            public void onScrollChanged(Scrollable scroller, int dx, int dy) {
                float currTransY = (int) ViewCompat.getTranslationY(getActionbarToolbar());
                float transition = Math.min(getMinTransition(), Math.max(-getMaxTransition(), currTransY - dy));
                LogHelper.d(TAG, "transition: " + transition + ", scroll y: " + mScrollView.getScrollY());
                ViewCompat.setTranslationY(getActionbarToolbar(), transition);
            }

            @Override
            public void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState) {
                if (isCollapseViewTotallyShownOrHidden())
                    return;

                if (newState == Scrollable.ScrollState.SCROLL_STATE_IDLE) {
                    if (scroller.getVerticalScrollOffset() > getMaxTranslationYRange()) {
                        hideToolbar();
                    } else {
                        showToolbar();
                    }
                }
            }
        });
    }
}
