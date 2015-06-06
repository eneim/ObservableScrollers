package im.ene.lab.obervablescrollers.sample.ui;

import android.os.Bundle;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.obervablescrollers.sample.adapter.DummyListViewAdapter;
import im.ene.lab.observablescrollers.lib.util.LogHelper;
import im.ene.lab.observablescrollers.lib.widget.ObsListView;

public class ObsListViewActivity extends BaseActivity {

    public static final String TAG = LogHelper.createLogTag(ObsListViewActivity.class);

    private ObsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_list_view);
        getActionbarToolbar();
        mListView = (ObsListView) findViewById(R.id.listview);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setAdapter(new DummyListViewAdapter(this));

        mListView.setPadding(mListView.getPaddingLeft(), mListView.getPaddingTop() + getMaxTranslationYRange(),
                mListView.getPaddingRight(), mListView.getPaddingBottom());
    }
}
