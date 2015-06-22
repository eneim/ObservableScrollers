package im.ene.lab.obervablescrollers.sample.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.observablescrollers.lib.BuildConfig;
import im.ene.lab.observablescrollers.lib.util.LogHelper;

/**
 * Created by eneim on 5/29/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.debugMode(true);
    }

    protected Toolbar mToolbar;

    protected int mToolbarHeight;

    protected Toolbar getActionbarToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                mToolbarHeight = mToolbar.getLayoutParams().height;
            } else {
                // DEBUG only
                if (BuildConfig.DEBUG) {
                    throw new IllegalArgumentException("You didn't setup a toolbar in your layout");
                }
            }
        }

        return mToolbar;
    }

    protected static final int ACTIONBAR_TOOLBAR_TRANSITION_DURATION = 200;

    protected void showToolbar() {
        animateToolbar(ViewCompat.getTranslationY(getActionbarToolbar()), 0);
    }

    protected void hideToolbar() {
        animateToolbar(ViewCompat.getTranslationY(getActionbarToolbar()), -getMaxTranslationYRange());
    }

    private void animateToolbar(final float fromY, final float toY) {
        if (fromY != toY) {
            ValueAnimator animator = ValueAnimator.ofFloat(fromY, toY).setDuration(ACTIONBAR_TOOLBAR_TRANSITION_DURATION);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translationY = (float) animation.getAnimatedValue();
                    ViewCompat.setTranslationY(getActionbarToolbar(), translationY);
                }
            });

            animator.start();
        }
    }

    protected float getMaxTranslationYRange() {
        return getMaxTransition() - getMinTransition();
    }

    protected float getMinTransition() {
        return 0;
    }

    // this method need to be overridden in those activities that don't have toolbar/actionbar
    protected float getMaxTransition() {
        return mToolbarHeight;
    }

    protected boolean isToolbarFullyHiddenOrShown() {
        return ViewCompat.getTranslationY(getActionbarToolbar()) >= 0 ||
                ViewCompat.getTranslationY(getActionbarToolbar()) <= -getMaxTranslationYRange();
    }
}
