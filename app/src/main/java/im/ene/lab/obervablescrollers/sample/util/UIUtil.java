package im.ene.lab.obervablescrollers.sample.util;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.observablescrollers.lib.util.LogHelper;

/**
 * Created by eneim on 6/5/15.
 */
public class UIUtil {
    private UIUtil() {
    }

    private static final TypeEvaluator ARGB_EVALUATOR = new ArgbEvaluator();

    public static int getActionbarToolbarHeight(Context context) {
        int result = 0;
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
        result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        LogHelper.d("action_bar_height", result + "");

        return result;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogHelper.d("status_bar_height", result + "");
        return result;
    }

    public static void setPaddingAnimation(final View view, final int newPaddingLeft, final int newPaddingRight, AnimatorEndListener listener) {
        final int oldPaddingLeft = view.getPaddingLeft();
        final int oldPaddingRight = view.getPaddingRight();

        if (oldPaddingLeft == newPaddingLeft && oldPaddingRight == newPaddingRight) {
            return;
        }

        final ValueAnimator animator = ValueAnimator.ofFloat(1, 0).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();

                int tempPaddingLeft = (int) (factor * oldPaddingLeft + (1 - factor) * newPaddingLeft);
                int tempPaddingRight = (int) (factor * oldPaddingRight + (1 - factor) * newPaddingRight);

                LogHelper.d("padding", factor + " | " + tempPaddingLeft + " | " + tempPaddingRight);
                view.setPadding(tempPaddingLeft, view.getPaddingTop(), tempPaddingRight, view.getPaddingBottom());
            }
        });

        if (listener != null)
            animator.addListener(listener);

        view.post(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        });
    }

    public static void setAlphaAnimation(final ColorDrawable colorDrawable, int toAlpha, final View view) {
        final int currentAlpha = colorDrawable.getAlpha();
        if (currentAlpha == toAlpha)
            return;
        ValueAnimator animator = ValueAnimator.ofInt(currentAlpha, toAlpha).setDuration(Math.abs(toAlpha - currentAlpha));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int nextAlpha = (int) animation.getAnimatedValue();
                colorDrawable.setAlpha(nextAlpha);
                view.setBackground(colorDrawable);
            }
        });

        animator.start();
    }

    public static void setStatusbarAlphaAnimation(final ColorDrawable colorDrawable, int toAlpha, final Activity activity) {
        final int currentAlpha = colorDrawable.getAlpha();
        if (currentAlpha == toAlpha)
            return;

        if (toAlpha < (int) (0.3 * 255))
            toAlpha = (int) (0.3 * 255);

        ValueAnimator animator = ValueAnimator.ofInt(currentAlpha, toAlpha).setDuration(Math.abs(toAlpha - currentAlpha));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int nextAlpha = (int) animation.getAnimatedValue();
                colorDrawable.setAlpha(nextAlpha);
                activity.getWindow().setStatusBarColor(colorDrawable.getColor());
            }
        });

        animator.start();
    }

    public static void animateTransitionY(final View view, float toY) {
        float fromY = ViewCompat.getTranslationY(view);
        if (fromY == toY)
            return;
        ValueAnimator animator = ValueAnimator.ofFloat(fromY, toY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewCompat.setTranslationY(view, translationY);
            }
        });

        animator.start();
    }

    public static void animate(ValueAnimator animator, ValueAnimator.AnimatorUpdateListener updateListener, AnimatorEndListener listener) {
        if (animator != null)
            animator.cancel();
        animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(200);
        if (updateListener != null)
            animator.addUpdateListener(updateListener);
        if (listener != null)
            animator.addListener(listener);
        animator.start();
    }

    public static abstract class AnimatorEndListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationEnd(Animator animator) {
            end(animator);
        }

        public abstract void end(Animator animator);

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
