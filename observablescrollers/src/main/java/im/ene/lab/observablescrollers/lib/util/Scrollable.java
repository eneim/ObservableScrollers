package im.ene.lab.observablescrollers.lib.util;

/**
 * Created by eneim on 5/29/15.
 */
public interface Scrollable {

    /**
     *
     * @return
     */
    public int getCurrentScrollX();

    /**
     *
     * @return
     */
    public int getCurrentScrollY();

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

}
