package im.ene.lab.observablescrollers.lib.util;

/**
 * Created by eneim on 6/22/15.
 */
public class TabPosition {
    public int position;
    public float offset;

    public TabPosition(int position, float offset) {
        this.position = position;
        this.offset = offset;
    }

    public Direction relativePosition(TabPosition otherTab) {
        if (this.position >= otherTab.position)
            return Direction.RIGHT;
        else return Direction.LEFT;
    }

    public enum Direction {

        LEFT,

        RIGHT,

        SAME
    }
}
