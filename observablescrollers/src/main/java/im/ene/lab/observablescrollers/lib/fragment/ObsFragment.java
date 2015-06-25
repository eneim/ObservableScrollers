package im.ene.lab.observablescrollers.lib.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 6/22/15.
 * <p/>
 * Fragment that hold a scrollable
 */

public abstract class ObsFragment extends Fragment {

    protected OnScrollObservedListener mOnScrollObservedListener;

    protected final String EXTRA_PADDINGS = "PADDINGS";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnScrollObservedListener))
            throw new IllegalArgumentException("This Fragment must be attached to an OnScrollObservedListener");

        mOnScrollObservedListener = (OnScrollObservedListener) activity;
    }

    public abstract Scrollable getScrollable();

    /**
     * Normally a scroller need to be fix its padding. In case you define your own padding, just ignore this
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void fixScrollerPadding(int left, int top, int right, int bottom) {

    }
}
