package im.ene.lab.observablescrollers.lib.util;

import android.view.View;

/**
 * Created by eneim on 5/29/15.
 */
public interface OnScrollObservedListener {

    void onScrollChanged(View scroller, int dx, int dy);

    void onScrollStateChanged(View scroller, ScrollState newState);

    /**
     * Created by eneim on 5/29/15.
     */
    enum ScrollState {

        SCROLL_STATE_POSITIVE("state_positive"),

        SCROLL_STATE_NEGATIVE("state_negative"),

        SCROLL_STATE_TOUCH_SCROLL("state_touch_scroll"),

        SCROLL_STATE_FLING("state_fling"),

        SCROLL_STATE_IDLE("state_idle");

        private String state;

        ScrollState(String state) {
            this.state = state;
        }

        public String getState() {
            return this.state;
        }
    }
}
