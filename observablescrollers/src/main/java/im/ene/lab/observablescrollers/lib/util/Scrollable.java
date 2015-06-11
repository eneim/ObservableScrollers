package im.ene.lab.observablescrollers.lib.util;

/**
 * Created by eneim on 5/29/15.
 */
public interface Scrollable {

    /**
     *
     * @return
     */
    public int getHorizontalScrollOffset();

    /**
     *
     * @return
     */
    public int getVerticalScrollOffset();

    /**
     *
     * @param x
     * @param y
     */
    public void scrollVerticallyTo(int y);

    /**
     *
     * @param y
     */
    public void scrollVerticallyBy(int y);

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

        public String getName() {
            return this.state;
        }
    }
}
