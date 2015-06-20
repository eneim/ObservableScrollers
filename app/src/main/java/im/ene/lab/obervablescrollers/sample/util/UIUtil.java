package im.ene.lab.obervablescrollers.sample.util;

import android.content.Context;
import android.content.res.TypedArray;

import im.ene.lab.observablescrollers.lib.util.LogHelper;

/**
 * Created by eneim on 6/5/15.
 */
public class UIUtil {
    private UIUtil() {
    }

    public static int getActionbarToolbarHeight(Context context) {
        int actionBarHeight = 0;
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        LogHelper.d("action_bar_height", actionBarHeight + "");
        return actionBarHeight;
    }
}
