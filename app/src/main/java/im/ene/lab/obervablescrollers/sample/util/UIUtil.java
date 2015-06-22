package im.ene.lab.obervablescrollers.sample.util;

import android.content.Context;
import android.util.TypedValue;

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
}
