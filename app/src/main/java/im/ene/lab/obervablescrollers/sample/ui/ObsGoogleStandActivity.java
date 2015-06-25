package im.ene.lab.obervablescrollers.sample.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.view.ViewTreeObserver;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.fragment.DummyScrollViewFragment;
import im.ene.lab.obervablescrollers.sample.util.UIUtil;
import im.ene.lab.observablescrollers.lib.adapter.SmartFragmentStatePagerAdapter;
import im.ene.lab.observablescrollers.lib.fragment.ObsFragment;
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

    private int mCurrentScrollY;

    private final TypedValue mTypedValue = new TypedValue();
    private ColorDrawable mToolbarColorDrawable;
    private ColorDrawable mStatusbarColorDrawable;
    private ColorDrawable mTransitionColorDrawable;

    // properly get tabview height
    private ViewTreeObserver.OnGlobalLayoutListener tabViewGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            mBaseScrollMount = mPagerHeaderHeight - mToolbarHeight - mTabs.getHeight();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                removeGlobalLayoutListenerPreJB();
            } else {
                removeGlobalLayoutListenerJB();
            }
        }

        @SuppressWarnings("deprecation")
        private void removeGlobalLayoutListenerPreJB() {
            mTabs.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private void removeGlobalLayoutListenerJB() {
            mTabs.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_google_stand);
        ButterKnife.inject(this);

        getActionbarToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTabs.getViewTreeObserver().addOnGlobalLayoutListener(tabViewGlobalLayoutListener);

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
                    Scrollable expectedNextScroller = ((ObsFragment) expectedNextItem).getScrollable();
                    int scrollY = mCurrentScrollY -
                            expectedNextScroller.getVerticalScrollOffset();
                    expectedNextScroller.scrollVerticallyBy(scrollY);
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

    private float mBaseScrollMount;

    // TODO fix usage of UIUtil.animate() method
    private ValueAnimator mToolbarAnimator, mHeaderAnimator, mTabAnimator;

    @Override
    public void onScrollChanged(Scrollable scroller, int dx, int dy) {
        float scrollY = scroller.getVerticalScrollOffset();
        float pagerVerticalChange = ViewCompat.getTranslationY(mPagerHeader);

        float pagerHeaderTransition = Math.min(0, Math.max(-mPagerHeaderHeight, pagerVerticalChange - dy));
        if (scrollY > mBaseScrollMount)
            pagerHeaderTransition = Math.min(pagerHeaderTransition, -mBaseScrollMount);
        ViewCompat.setTranslationY(mPagerHeader, pagerHeaderTransition);

        float headerImageParallax = Math.min(0, Math.max(-mMainHeaderHeight, -scrollY / 2));
        ViewCompat.setTranslationY(mMainHeader, headerImageParallax);

        float categoryIconAlpha = Math.min(1, Math.max(0, (scrollY * 2) / mPagerHeaderHeight));
        ViewCompat.setAlpha(mThumbNail, 1.0f - categoryIconAlpha);

        float toolbarTransition = Math.min(
                0,
                Math.max(
                        -mToolbarHeight - mTabs.getHeight(),
                        pagerHeaderTransition + mBaseScrollMount
                )
        );

        ViewCompat.setTranslationY(getActionbarToolbar(), Math.max(pagerHeaderTransition, toolbarTransition));

//        if ((mPagerHeaderHeight - pagerVerticalChange) <= mToolbarHeight + mTabs.getHeight()) {
//
//            final int oldPaddingLeft = mTabs.getPaddingLeft();
//            final int oldPaddingRight = mTabs.getPaddingRight();
//
//            if (oldPaddingLeft == mTabs.getTabPaddingLeftRight() && oldPaddingRight == mTabs.getTabPaddingLeftRight()) {
//                return;
//            }
//
//            UIUtil.animate(mTabAnimator,
//                    new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float factor = (float) animation.getAnimatedValue();
//                            int tempPaddingLeft = (int) (factor * mTabs.getTabPaddingLeftRight() + (1 - factor) * oldPaddingLeft);
//                            int tempPaddingRight = (int) (factor * mTabs.getTabPaddingLeftRight() + (1 - factor) * oldPaddingRight);
//                            mTabs.setPadding(tempPaddingLeft, mTabs.getPaddingTop(), tempPaddingRight, mTabs.getPaddingBottom());
//                        }
//                    }, new UIUtil.AnimatorEndListener() {
//                        @Override
//                        public void end(Animator animator) {
//                            mTabs.setPaddingMiddle(false);
//                            mTabs.scrollToChild(mViewPager.getCurrentItem(), 0);
//                        }
//                    });
//        } else {
//            if (!mTabs.isPaddingMiddle()) {
//                UIUtil.setPaddingAnimation(
//                        mTabs,
//                        mTabs.getPaddingLeftOnMiddle(), mTabs.getPaddingRightOnMiddle(),
//                        new UIUtil.AnimatorEndListener() {
//                            @Override
//                            public void end(Animator animator) {
//                                mTabs.setPaddingMiddle(true);
//                                mTabs.scrollToChild(mViewPager.getCurrentItem(), mTabs.getPaddingLeftOnMiddle());
//                            }
//                        });
//            }
//        }

        mCurrentScrollY = (int) scrollY;
    }

    @Override
    public void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState) {
        if (newState != Scrollable.ScrollState.SCROLL_STATE_IDLE)
            return;

        if (!isToolbarFullyHiddenOrShown()) {
            final float currentToolbarTrans = ViewCompat.getTranslationY(getActionbarToolbar());
            final float currentPagerTrans = ViewCompat.getTranslationY(mPagerHeader);
            final float nextPagerY = scroller.getVerticalScrollOffset() > mPagerHeaderHeight ? -mPagerHeaderHeight : -mPagerHeaderHeight + mToolbarHeight + mTabs.getHeight();
            final float nextToolbarY = nextPagerY + mBaseScrollMount;
            UIUtil.animate(mToolbarAnimator, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float factor = (float) animation.getAnimatedValue();

                    float nextTrans = factor * nextToolbarY + (1 - factor) * currentToolbarTrans;
                    ViewCompat.setTranslationY(getActionbarToolbar(), nextTrans);

                    float nextPagerTrans = factor * nextPagerY + (1 - factor) * currentPagerTrans;
                    ViewCompat.setTranslationY(mPagerHeader, nextPagerTrans);
                }
            }, new UIUtil.AnimatorEndListener() {
                @Override
                public void end(Animator animator) {
                }
            });
        }
    }

    @Override
    protected boolean isToolbarFullyHiddenOrShown() {
        return ViewCompat.getTranslationY(getActionbarToolbar()) >= 0 ||
                ViewCompat.getTranslationY(getActionbarToolbar()) <= -mToolbarHeight - mTabs.getHeight();
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
//            if (position % 2 == 0)
//                fragment = DummyRecyclerViewFragment.newInstance();
//            else
//                fragment = DummyScrollViewFragment.newInstance();
            fragment = DummyScrollViewFragment.newInstance();
            return fragment;
        }

        @Override
        public int getCount() {
            return 2    ;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
