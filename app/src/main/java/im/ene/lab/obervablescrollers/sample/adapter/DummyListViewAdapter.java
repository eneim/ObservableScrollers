package im.ene.lab.obervablescrollers.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import im.ene.lab.obervablescrollers.sample.R;

/**
 * Created by eneim on 6/5/15.
 */
public class DummyListViewAdapter extends BaseAdapter {

    private final Context mContext;

    private final int[] dummytStrings = {R.string.lipsum_short, R.string.lipsum_short_2, R.string.lipsum_short_3};

    public DummyListViewAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return mContext.getString(R.string.lipsum_short);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null)
            return convertView;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        TextView mText;
        if ((mText = (TextView) convertView.findViewById(R.id.text)) != null)
            mText.setText(getRandomStringId(dummytStrings.length));
        return convertView;
    }

    private int getRandomStringId(int max) {
        int seed = (int) (Math.random() * max);
        return dummytStrings[seed];
    }
}
