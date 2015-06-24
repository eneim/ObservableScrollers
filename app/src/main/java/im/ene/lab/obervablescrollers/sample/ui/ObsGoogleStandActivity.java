package im.ene.lab.obervablescrollers.sample.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.fragment.DummyRecyclerViewFragment;
import im.ene.lab.obervablescrollers.sample.fragment.DummyScrollViewFragment;
import im.ene.lab.obervablescrollers.sample.util.UIUtil;
import im.ene.lab.observablescrollers.lib.adapter.SmartFragmentStatePagerAdapter;
import im.ene.lab.observablescrollers.lib.fragment.ObsFragment;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

public class ObsGoogleStandActivity extends BaseActivity implements OnScrollObservedListener {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @InjectView(R.id.viewpager)
    ViewPager mViewPager;
    @InjectView(R.id.main_header)
    View mMainHeader;
    @InjectView(R.id.pager_header)
    View mPagerHeader;
    @InjectView(R.id.header_thumbnail)
    View mThumbNail;

    private String[] mTitles;

    private int mMainHeaderHeight;
    private int mPagerHeaderHeight;

    private int mThemedStatusBarColor;
    private int mNormalStatusBarColor;

    private int mThemedToolBarColor;
    private int mNormalToolBarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        setContentView(R.layout.activity_obs_google_stand);
//        getActionbarToolbar().setPadding(0, UIUtil.getStatusBarHeight(this), 0, 0);
//        getActionbarToolbar().getLayoutParams().height = mToolbarHeight + UIUtil.getStatusBarHeight(this);

        mMainHeaderHeight = getResources().getDimensionPixelSize(R.dimen.google_play_header_large);
        mPagerHeaderHeight = getResources().getDimensionPixelSize(R.dimen.google_play_header_middle);

        getTheme().resolveAttribute(R.attr.colorPrimary, mTypedValue, true);
        mToolbarColorDrawable = new ColorDrawable(mTypedValue.data);
        mThemedToolBarColor = mToolbarColorDrawable.getColor();
        mNormalToolBarColor = mThemedToolBarColor;

        getTheme().resolveAttribute(R.attr.colorPrimaryDark, mTypedValue, true);
        mStatusbarColorDrawable = new ColorDrawable(mTypedValue.data);
        mThemedStatusBarColor = mStatusbarColorDrawable.getColor();
        mNormalStatusBarColor = mThemedStatusBarColor;

        mTransitionColorDrawable = mToolbarColorDrawable;

    }

    private int mCurrentScrollY;

    private final TypedValue mTypedValue = new TypedValue();
    private ColorDrawable mToolbarColorDrawable;
    private ColorDrawable mStatusbarColorDrawable;
    private ColorDrawable mTransitionColorDrawable;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setToolbarAlpha(float alpha) {
        UIUtil.setAlphaAnimation(mTransitionColorDrawable, (int) (alpha * 255), mPagerHeader);
//        UIUtil.setStatusbarAlphaAnimation(mStatusbarColorDrawable, (int) (alpha * 255), ObsGoogleStandActivity.this);
//        ViewCompat.animate(getActionbarToolbar()).alpha(alpha).setDuration(200).start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.inject(this);

//        UIUtil.setAlphaAnimation(mToolbarColor, 0, getActionbarToolbar());

        setToolbarAlpha(0.0f);

        mTitles = getResources().getStringArray(R.array.item_titles);
        final ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setViewPager(mViewPager);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int currentItemPosition = 0;

            private int expectedNextPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (currentItemPosition == position) {
                    if (positionOffset < 0.5) {
                        expectedNextPosition = position + 1;
                    }
                } else {
                    if (positionOffset > 0.5) {
                        expectedNextPosition = position;
                    }
                }

                // I would like to scroll the "adjacent page" here to prevent them from
                // visible during the scrolling.
                // in ObsRecyclerView#getVerticalScrollOffset() I disallow the scroll by checking the
                // vertical scroll value so there is no need to worry about to many method calls.
                // I'm aware of the trade off when asking it to call many time. Please give me a better call here
                Fragment expectedNextItem = mAdapter.getRegisteredFragment(expectedNextPosition);
                if (expectedNextItem != null && expectedNextItem instanceof ObsFragment) {
                    Scrollable currentScrollable = ((ObsFragment) expectedNextItem).getScrollable();
                    currentScrollable.scrollVerticallyBy(mCurrentScrollY -
                            currentScrollable.getVerticalScrollOffset());
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentItemPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }

    @Override
    public void onScrollChanged(Scrollable scroller, int dx, int dy) {
        float scrollY = scroller.getVerticalScrollOffset();

        mCurrentScrollY = (int) scrollY;
        float parallax = Math.min(0, Math.max(-mMainHeaderHeight, -scrollY / 2));
        ViewCompat.setTranslationY(mMainHeader, parallax);

        float transition = Math.min(0, Math.max(-mPagerHeaderHeight, -scrollY));
        ViewCompat.setTranslationY(mPagerHeader, transition);

        float alpha = Math.min(1, Math.max(0, (scrollY * 2) / mPagerHeaderHeight));
        LogHelper.d("scrollY", scrollY + "");
        ViewCompat.setAlpha(mThumbNail, 1.0f - alpha);

        if ((mPagerHeaderHeight - scrollY) <= mToolbarHeight + mTabs.getHeight()) {
            UIUtil.setPaddingAnimation(mTabs, mTabs.getTabPaddingLeftRight(), mTabs.getTabPaddingLeftRight(), new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mTabs.setPaddingMiddle(false);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

//            mTabs.setTabBackground(R.attr.colorPrimary);
            setToolbarAlpha(1.0f);
            float currTransY = ViewCompat.getTranslationY(getActionbarToolbar());
            float transition_ = Math.min(getMinTransition(), Math.max(-getMaxTransition(), currTransY - dy));
            ViewCompat.setTranslationY(getActionbarToolbar(), transition_);
        } else {
            setToolbarAlpha(0.0f);
            if (!mTabs.isPaddingMiddle()) {
                UIUtil.setPaddingAnimation(mTabs, mTabs.getPaddingLeftOnMiddle(), mTabs.getPaddingRightOnMiddle(), new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTabs.setPaddingMiddle(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        }
    }

    @Override
    public void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState) {
        if (newState != Scrollable.ScrollState.SCROLL_STATE_IDLE)
            return;
    }

    private class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

        private String[] titles;

        public ViewPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position % 3 == 0)
                fragment = DummyRecyclerViewFragment.newInstance();
            else
                fragment = DummyScrollViewFragment.newInstance();
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
