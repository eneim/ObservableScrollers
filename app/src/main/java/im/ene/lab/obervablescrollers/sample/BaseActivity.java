package im.ene.lab.obervablescrollers.sample;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

    protected Toolbar getActionbarToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
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

    protected int getMaxTranslationYRange() {
        return mToolbar == null ? 0 : mToolbar.getHeight();
    }

    protected boolean isToolbarFullyHiddenOrShown() {
        return ViewCompat.getTranslationY(getActionbarToolbar()) >= 0 ||
                ViewCompat.getTranslationY(getActionbarToolbar()) <= -getMaxTranslationYRange();
    }
}
