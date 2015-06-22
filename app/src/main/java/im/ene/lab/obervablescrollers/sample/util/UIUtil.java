package im.ene.lab.obervablescrollers.sample.util;

import android.animation.ValueAnimator;
import android.content.Context;
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

    public static int getActionbarToolbarHeight(Context context) {
        int result = 0;
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
        result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        LogHelper.d("action_bar_height", result + "");

        return result;
    }

    public static void setPaddingAnimation(final View view, final int newPaddingLeft, final int newPaddingRight) {
        final int oldPaddingLeft = view.getPaddingLeft();
        final int oldPaddingRight = view.getPaddingRight();

        if (oldPaddingLeft == newPaddingLeft && oldPaddingRight == newPaddingRight) {
            return;
        }
        
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0).setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();

                int tempPaddingLeft = (int) (oldPaddingLeft * factor + newPaddingLeft * (1 - factor));
                int tempPaddingRight = (int) (oldPaddingRight * factor + newPaddingRight * (1 - factor));
                view.setPadding(tempPaddingLeft, view.getPaddingTop(), tempPaddingRight, view.getPaddingBottom());
            }
        });

        animator.start();
    }
}
