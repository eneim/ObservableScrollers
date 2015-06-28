package im.ene.lab.observablescrollers.lib.util;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by budius on 16.05.14.
 * This improves on Zsolt Safrany answer on stack-overflow (see link)
 * by making it a detector that can be attached to any AbsListView.
 * http://stackoverflow.com/questions/8471075/android-listview-find-the-amount-of-pixels-scrolled
 */
public class InternalScrollListener implements AbsListView.OnScrollListener {

    private SparseIntArray mChildHeights = new SparseIntArray();

    private TrackElement[] trackElements = {
            new TrackElement(0), // top view, bottom Y
            new TrackElement(1), // mid view, bottom Y
            new TrackElement(2), // mid view, top Y
            new TrackElement(3)};// bottom view, top Y

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // init the values every time the list is moving
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL ||
                scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
            for (TrackElement t : trackElements)
                t.syncState(view);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // update children views height first
        updateChildHeights(view, firstVisibleItem, visibleItemCount);

        boolean wasTracked = false;
        for (TrackElement t : trackElements) {
            if (!wasTracked) {
                if (t.isSafeToTrack(view)) {
                    wasTracked = true;
                    onScroll(view, t.getDeltaY(), getVerticalScroll(view, firstVisibleItem));
                    t.syncState(view);
                } else {
                    t.reset();
                }
            } else {
                t.syncState(view);
            }
        }
    }

    /**
     *
     * @param listView
     * @param firstVisibleItem
     * @param visibleItemCount
     */
    private void updateChildHeights(AbsListView listView, int firstVisibleItem, int visibleItemCount) {
        // update heights list
        for (int i = 0; i < visibleItemCount; i++) {
            if (mChildHeights.indexOfKey(firstVisibleItem + i) < 0 && listView.getChildAt(i) != null)
                mChildHeights.put(firstVisibleItem + i, listView.getChildAt(i).getHeight());
        }
    }

    public int getVerticalScroll(AbsListView listView, int firstVisibleItem) {
        int totalChildHeight = 0;

        // we look at the current top item only
        int maxIndex = mChildHeights.size() > firstVisibleItem ? firstVisibleItem : mChildHeights.size();

        for (int i = 0; i < maxIndex; i++) {
            int key = mChildHeights.keyAt(i);
            totalChildHeight += mChildHeights.get(key);
        }

        return totalChildHeight - listView.getChildAt(0).getTop() + listView.getPaddingTop();
    }

    /**
     *
     * @param view
     * @param deltaY
     * @param scrollY
     */
    protected void onScroll(AbsListView view, float deltaY, int scrollY) {

    }

    private class TrackElement {

        private final int position;

        private TrackElement(int position) {
            this.position = position;
        }

        void syncState(AbsListView view) {
            if (view.getChildCount() > 0) {
                trackedChild = getChild(view);
                trackedChildPrevTop = getY();
                trackedChildPrevPosition = view.getPositionForView(trackedChild);
            }
        }

        void reset() {
            trackedChild = null;
        }

        boolean isSafeToTrack(AbsListView view) {
            return (trackedChild != null) &&
                    (trackedChild.getParent() == view) && (view.getPositionForView(trackedChild) == trackedChildPrevPosition);
        }

        int getDeltaY() {
            return getY() - trackedChildPrevTop;
        }

        private View getChild(AbsListView view) {
            switch (position) {
                case 0:
                    return view.getChildAt(0);
                case 1:
                case 2:
                    return view.getChildAt(view.getChildCount() / 2);
                case 3:
                    return view.getChildAt(view.getChildCount() - 1);
                default:
                    return null;
            }
        }

        private int getY() {
            if (position <= 1) {
                return trackedChild.getBottom();
            } else {
                return trackedChild.getTop();
            }
        }

        View trackedChild;
        int trackedChildPrevPosition;
        int trackedChildPrevTop;
    }
}
