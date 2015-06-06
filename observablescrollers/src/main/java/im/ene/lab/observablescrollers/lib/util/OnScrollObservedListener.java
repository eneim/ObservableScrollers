package im.ene.lab.observablescrollers.lib.util;

import android.view.View;

/**
 * Created by eneim on 5/29/15.
 */
public interface OnScrollObservedListener {

    void onScrollChanged(View scroller, int dx, int dy);

    void onScrollStateChanged(View scroller, Scrollable.ScrollState newState);

}
