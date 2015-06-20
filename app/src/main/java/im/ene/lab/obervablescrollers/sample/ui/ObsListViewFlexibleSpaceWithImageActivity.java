package im.ene.lab.obervablescrollers.sample.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AdapterView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.adapter.DummyListViewAdapter;
import im.ene.lab.obervablescrollers.sample.util.UIUtil;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsListView;

public class ObsListViewFlexibleSpaceWithImageActivity extends BaseActivity {

    public static final String TAG = LogHelper.createLogTag(ObsListViewFlexibleSpaceWithImageActivity.class);

    @InjectView(R.id.listview)
    ObsListView mListView;

    @InjectView(R.id.overlay)
    View mOverLayView;
    @InjectView(R.id.image)
    View mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_list_view_flexible_space_with_image);
        ButterKnife.inject(this);
    }

    DummyListViewAdapter dummyListViewAdapter;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dummyListViewAdapter = new DummyListViewAdapter(this);
        mListView.setAdapter(dummyListViewAdapter);

        mListView.setPadding(mListView.getPaddingLeft(), mListView.getPaddingTop() + getMaxTransition(),
                mListView.getPaddingRight(), mListView.getPaddingBottom());

        mListView.setOnScrollObservedListener(new OnScrollObservedListener() {
            @Override
            public void onScrollChanged(View scroller, int dx, int dy) {
                int currTransY = (int) ViewCompat.getTranslationY(mOverLayView);
                int transition = Math.min(0, Math.max(-getMaxTranslationYRange(), currTransY - dy));
                ViewCompat.setTranslationY(mOverLayView, transition);

                int parallax = Math.min(0, Math.max(-getMaxTranslationYRange(), (currTransY - dy) / 2));
                ViewCompat.setTranslationY(mImageView, parallax);

                // transition = 56dp --> alpha = 1.1
                // transition = 240dp --> alpha = 0.0
                ViewCompat.setAlpha(mOverLayView, (float) -transition / getMaxTranslationYRange());
            }

            @Override
            public void onScrollStateChanged(View scroller, Scrollable.ScrollState newState) {
                // do nothing here
            }
        });

        // i want to test notifyDatasetChanged method
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dummyListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getMaxTranslationYRange() {
        return getMaxTransition() - getMinTransition();
    }

    @Override
    protected int getMinTransition() {
        return UIUtil.getActionbarToolbarHeight(this);
    }

    @Override
    protected int getMaxTransition() {
        return getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
    }
}
