package im.ene.lab.obervablescrollers.sample.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import im.ene.lab.obervablescrollers.sample.BaseActivity;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.adapter.DummyRecyclerAdapter;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsRecyclerView;


public class ObsRecyclerViewActivity extends BaseActivity {

    public static final String TAG = LogHelper.createLogTag(ObsRecyclerViewActivity.class);

    private ObsRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

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
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);    // for better performance
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(new DummyRecyclerAdapter());

        mRecyclerView.setOnScrollObservedListener(new OnScrollObservedListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_obs_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
