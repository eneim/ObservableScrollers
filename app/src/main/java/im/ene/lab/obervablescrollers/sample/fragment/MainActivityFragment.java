package im.ene.lab.obervablescrollers.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.ui.AOSLListViewFlexibleSpaceWithImageActivity;
import im.ene.lab.obervablescrollers.sample.ui.ObsListViewActivity;
import im.ene.lab.obervablescrollers.sample.ui.ObsListViewFlexibleSpaceWithImageActivity;
import im.ene.lab.obervablescrollers.sample.ui.ObsRecyclerViewActivity;
import im.ene.lab.obervablescrollers.sample.ui.ObsScrollViewActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @OnClick(R.id.open_scrollview)
    void openScrollview() {
        Intent intent = new Intent(getActivity(), ObsScrollViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.open_recyclerview)
    void openRecyclerView() {
        Intent intent = new Intent(getActivity(), ObsRecyclerViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.open_listview)
    void openListView() {
        Intent intent = new Intent(getActivity(), ObsListViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.open_listview_flexspace_image)
    void openListViewFlexImage() {
        Intent intent = new Intent(getActivity(), ObsListViewFlexibleSpaceWithImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.open_aosl_listview_flexspace_image)
    void openAoslListViewFlexImage() {
        Intent intent = new Intent(getActivity(), AOSLListViewFlexibleSpaceWithImageActivity.class);
        startActivity(intent);
    }
}
