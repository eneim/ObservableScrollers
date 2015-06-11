package im.ene.lab.observablescrollers.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by eneim on 6/9/15.
 */
public class ObsGridView extends GridView {
    public ObsGridView(Context context) {
        super(context);
    }

    public ObsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObsGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObsGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
