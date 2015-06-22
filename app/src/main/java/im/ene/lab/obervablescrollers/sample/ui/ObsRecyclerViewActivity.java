package im.ene.lab.obervablescrollers.sample.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.adapter.DummyRecyclerViewAdapter;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsRecyclerView;


public class ObsRecyclerViewActivity extends BaseActivity {

    public static final String TAG = LogHelper.createLogTag(ObsRecyclerViewActivity.class);

    private ObsRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_recycler_view);
        getActionbarToolbar();
        mRecyclerView = (ObsRecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);    // for better performance
        mRecyclerView.setAdapter(new DummyRecyclerViewAdapter());

        mRecyclerView.setOnScrollObservedListener(new OnScrollObservedListener() {
            @Override
            public void onScrollChanged(Scrollable scroller, int dx, int dy) {
                float currTransY = ViewCompat.getTranslationY(getActionbarToolbar());
                float transition = Math.min(getMinTransition(), Math.max(-getMaxTransition(), currTransY - dy));
                ViewCompat.setTranslationY(getActionbarToolbar(), transition);
            }

            @Override
            public void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState) {
                if (isToolbarFullyHiddenOrShown())
                    return;

                if (newState == Scrollable.ScrollState.SCROLL_STATE_IDLE) {
                    LogHelper.d(TAG, scroller.getVerticalScrollOffset() + "");
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
