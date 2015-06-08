package im.ene.lab.obervablescrollers.sample.adapter;

import android.content.Context;
import android.util.SparseArray;
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
        return 50;
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
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);

        TextView mText = ViewHolder.get(convertView, R.id.text);
        mText.setText(getRandomStringId(dummytStrings.length, position));
        return convertView;
    }

    private String getRandomStringId(int max, int position) {
        int seed = (int) (Math.random() * max);
        return position + " - " + mContext.getString(dummytStrings[seed]);
    }

    public static class ViewHolder {

        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

    }
}
