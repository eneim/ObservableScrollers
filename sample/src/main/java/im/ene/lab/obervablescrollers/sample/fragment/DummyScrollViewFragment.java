package im.ene.lab.obervablescrollers.sample.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.observablescrollers.lib.fragment.ObsFragment;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsScrollView;

public class DummyScrollViewFragment extends ObsFragment {

    public static DummyScrollViewFragment newInstance() {
        DummyScrollViewFragment fragment = new DummyScrollViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DummyScrollViewFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.scrollview)
    ObsScrollView mScrollView;

    private int[] mPaddings = new int[4];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(EXTRA_PADDINGS)) {
            mPaddings = getArguments().getIntArray(EXTRA_PADDINGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dummy_scroll_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mScrollView.setOnScrollObservedListener(mOnScrollObservedListener);

        mScrollView.setPadding(
                mScrollView.getPaddingLeft() + mPaddings[0],
                mScrollView.getPaddingTop() + mPaddings[1],
                mScrollView.getPaddingRight() + mPaddings[2],
                mScrollView.getPaddingBottom() + mPaddings[3]
        );
        mScrollView.setClipToPadding(false);
    }

    @Override
    public Scrollable getScrollable() {
        return mScrollView;
    }

    @Override
    public void fixScrollerPadding(int left, int top, int right, int bottom) {
        Bundle args = getArguments();
        args.putIntArray(EXTRA_PADDINGS, new int[]{left, top, right, bottom});
        setArguments(args);
        mPaddings = new int[]{left, top, right, bottom};
    }

}
