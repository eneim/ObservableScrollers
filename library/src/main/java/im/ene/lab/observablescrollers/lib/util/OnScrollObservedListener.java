package im.ene.lab.observablescrollers.lib.util;

/**
 * Created by eneim on 5/29/15.
 */
public interface OnScrollObservedListener {

    void onScrollChanged(Scrollable scroller, int dx, int dy);

    void onScrollStateChanged(Scrollable scroller, Scrollable.ScrollState newState);

}
