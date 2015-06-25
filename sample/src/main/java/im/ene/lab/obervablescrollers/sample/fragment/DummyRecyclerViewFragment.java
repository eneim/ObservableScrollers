package im.ene.lab.obervablescrollers.sample.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.adapter.DummyRecyclerViewAdapter;
import im.ene.lab.observablescrollers.lib.fragment.ObsFragment;
import im.ene.lab.observablescrollers.lib.util.Scrollable;
import im.ene.lab.observablescrollers.lib.widget.ObsRecyclerView;

public class DummyRecyclerViewFragment extends ObsFragment {

    public static DummyRecyclerViewFragment newInstance() {
        DummyRecyclerViewFragment fragment = new DummyRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DummyRecyclerViewFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.recyclerview)
    ObsRecyclerView mRecyclerView;

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
        return inflater.inflate(R.layout.fragment_dummy_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);    // for better performance
        mRecyclerView.setAdapter(new DummyRecyclerViewAdapter());
        mRecyclerView.setOnScrollObservedListener(mOnScrollObservedListener);

        mRecyclerView.setPadding(
                mRecyclerView.getPaddingLeft() + mPaddings[0],
                mRecyclerView.getPaddingTop() + mPaddings[1],
                mRecyclerView.getPaddingRight() + mPaddings[2],
                mRecyclerView.getPaddingBottom() + mPaddings[3]
        );
        mRecyclerView.setClipToPadding(false);
    }

    @Override
    public Scrollable getScrollable() {
        return mRecyclerView;
    }

    @Override
    public void fixScrollerPadding(int left, int top, int right, int bottom) {
        Bundle args = getArguments();
        args.putIntArray(EXTRA_PADDINGS, new int[]{left, top, right, bottom});
        setArguments(args);
        mPaddings = new int[]{left, top, right, bottom};
    }

}
