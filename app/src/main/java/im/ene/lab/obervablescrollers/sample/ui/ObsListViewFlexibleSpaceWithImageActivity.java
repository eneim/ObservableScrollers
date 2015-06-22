package im.ene.lab.obervablescrollers.sample.ui;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
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
    @InjectView(R.id.title)
    View mTitleView;

    private int mMaxTransition;

    private int mActionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_list_view_flexible_space_with_image);
        ButterKnife.inject(this);

        mMaxTransition = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarHeight = UIUtil.getActionbarToolbarHeight(this);
    }

    DummyListViewAdapter dummyListViewAdapter;

    private final float MAX_TEXT_SCALE_DELTA = 0.3f;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dummyListViewAdapter = new DummyListViewAdapter(this);
        mListView.setAdapter(dummyListViewAdapter);
        mListView.setPadding(mListView.getPaddingLeft(), (int) (mListView.getPaddingTop() + getMaxTransition()),
                mListView.getPaddingRight(), mListView.getPaddingBottom());

        mListView.setOnScrollObservedListener(new OnScrollObservedListener() {
            @Override
            public void onScrollChanged(Scrollable scroller, int dx, int dy) {
                int scrollY = scroller.getVerticalScrollOffset();

                float transition = Math.min(0, Math.max(-getMaxTranslationYRange(), -scrollY));
                ViewCompat.setTranslationY(mOverLayView, transition);

                float parallax = Math.min(0, Math.max(-getMaxTranslationYRange(), (float) -scrollY / 2));
                ViewCompat.setTranslationY(mImageView, parallax);

                float alpha = Math.min(1, Math.max(0, (float) scrollY / getMaxTranslationYRange()));
                ViewCompat.setAlpha(mOverLayView, alpha);

                float scale = 1 + Math.min(0, Math.max(MAX_TEXT_SCALE_DELTA,
                        (getMaxTranslationYRange() - (float) (scrollY)) / getMaxTranslationYRange()));
                LogHelper.d("Space", (getMaxTranslationYRange() - (float) (scrollY)) / getMaxTranslationYRange() + "");
            }

            @Override
            public void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState) {
                if (newState == Scrollable.ScrollState.SCROLL_STATE_IDLE) {

                }
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
    protected float getMaxTranslationYRange() {
        return getMaxTransition() - getMinTransition();
    }

    @Override
    protected float getMinTransition() {
        return mActionBarHeight;
    }

    @Override
    protected float getMaxTransition() {
        return mMaxTransition;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewCompat.setPivotX(mTitleView, findViewById(android.R.id.content).getWidth());
        } else {
            ViewCompat.setPivotX(mTitleView, 0);
        }
    }
}
