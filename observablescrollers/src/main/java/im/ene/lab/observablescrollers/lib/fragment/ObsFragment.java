package im.ene.lab.observablescrollers.lib.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.lab.observablescrollers.lib.util.OnScrollObservedListener;
import im.ene.lab.observablescrollers.lib.util.Scrollable;

/**
 * Created by eneim on 6/22/15.
 * <p/>
 * Fragment that hold a scrollable
 */

public abstract class ObsFragment extends Fragment {

    protected OnScrollObservedListener mOnScrollObservedListener;

    private boolean mIsScrollableAttached = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnScrollObservedListener))
            throw new IllegalArgumentException("This Fragment must be attached to an OnScrollObservedListener");

        mOnScrollObservedListener = (OnScrollObservedListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onScrollableAttached();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onScrollableAttached();
    }

    @Override
    public void onStart() {
        super.onStart();
        onScrollableAttached();
    }

    @Override
    public void onResume() {
        super.onResume();
        onScrollableAttached();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onScrollableDetached();
    }

    private void onScrollableAttached() {
        if (!mIsScrollableAttached) {
            if (mOnScrollObservedListener != null && getScrollable() != null) {
                getScrollable().setOnScrollObservedListener(mOnScrollObservedListener);
                mIsScrollableAttached = true;
            }
        }
    }

    private void onScrollableDetached() {
        if (getScrollable() != null)
            getScrollable().setOnScrollObservedListener(null);
    }

    public abstract Scrollable getScrollable();

}
