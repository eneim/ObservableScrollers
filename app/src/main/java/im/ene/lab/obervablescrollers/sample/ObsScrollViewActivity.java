package im.ene.lab.obervablescrollers.sample;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;

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
            public void onScrollChanged(View scroller, int dx, int dy) {
                int currTransY = (int) ViewCompat.getTranslationY(getActionbarToolbar());
                int maxTransY = 0;
                int minTransY = -getMaxTranslationYRange();

                ViewCompat.setTranslationY(getActionbarToolbar(),
                        Math.min(maxTransY, Math.max(minTransY, currTransY - dy)));
            }

            @Override
            public void onScrollStateChanged(View scroller, ScrollState newState) {
                if (!(scroller instanceof Scrollable))
                    throw new IllegalArgumentException("This scrollview must implement Scrollable");

                Scrollable scrollable = (Scrollable) scroller;

                if (isToolbarFullyHiddenOrShown())
                    return;

                if (newState == ScrollState.SCROLL_STATE_IDLE) {
                    if (scrollable.getCurrentScrollY() > getMaxTranslationYRange()) {
                        hideToolbar();
                    } else {
                        showToolbar();
                    }
                }
            }
        });
    }
}
