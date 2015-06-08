package im.ene.lab.observablescrollers.lib.util;

import android.util.SparseIntArray;
import android.widget.AbsListView;

/**
 * Helper class for calculating relative scroll offsets in a ListView or GridView by tracking the
 * position of child views.
 */
public class ListViewScrollTracker {
    private AbsListView mListView;
    private SparseIntArray mPositions;

    public ListViewScrollTracker(final AbsListView listView) {
        mListView = listView;
    }

    /**
     * Call from an AbsListView.OnScrollListener to calculate the incremental offset (change in scroll offset
     * since the last calculation).
     *
     * @param firstVisiblePosition First visible item position in the list.
     * @param visibleItemCount     Number of visible items in the list.
     * @return The incremental offset, or 0 if it wasn't possible to calculate the offset.
     */
    public int calculateIncrementalOffset(final int firstVisiblePosition, final int visibleItemCount) {
        // Remember previous positions, if any
        SparseIntArray previousPositions = null;
        if (mPositions != null)
            previousPositions = mPositions.clone();

        // Store new positions
        mPositions = new SparseIntArray();
        for (int i = 0; i < visibleItemCount; i++) {
            if (mListView.getChildAt(i) == null)
                break;

            mPositions.put(firstVisiblePosition + i, mListView.getChildAt(i).getTop());
        }

        if (previousPositions != null) {
            // Find position which exists in both mPositions and previousPositions, then return the difference
            // of the new and old Y values.
            for (int i = 0; i < previousPositions.size(); i++) {
                int position = previousPositions.keyAt(i);
                if (firstVisiblePosition != position)
                    continue;

                int previousTop = previousPositions.get(position);
                Integer newTop = mPositions.get(position);
                return newTop - previousTop;
            }
        }

        return 0; // No view's position was in both previousPositions and mPositions
    }

    private SparseIntArray mChildHeights = new SparseIntArray();

    public int getVerticalScroll(int firstVisibleItem, int visibleItemCount) {
        if (mListView == null || mListView.getChildAt(0) == null || firstVisibleItem < 0)
            return 0;

        if (firstVisibleItem == 0)
            return mListView.getPaddingTop() - mListView.getChildAt(0).getTop();

        for (int i = 0; i < visibleItemCount; i++) {
            if (mChildHeights.indexOfKey(firstVisibleItem + i) < 0 && mListView.getChildAt(i) != null)
                mChildHeights.put(firstVisibleItem + i, mListView.getChildAt(i).getHeight());
        }

        int totalChildHeight = 0;
        int maxIndex = mChildHeights.size() > firstVisibleItem ? firstVisibleItem : mChildHeights.size();

        for (int i = 0; i < maxIndex; i++) {
            int key = mChildHeights.keyAt(i);
            totalChildHeight += mChildHeights.get(key);
        }

        return totalChildHeight - mListView.getChildAt(0).getTop() + mListView.getPaddingTop();
    }

    public void clear() {
        mPositions = null;
    }
}