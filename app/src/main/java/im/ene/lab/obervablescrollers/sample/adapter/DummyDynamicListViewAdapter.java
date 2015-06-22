package im.ene.lab.obervablescrollers.sample.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import im.ene.lab.obervablescrollers.sample.R;
import im.ene.lab.observablescrollers.lib.util.LogHelper;

/**
 * Created by eneim on 6/5/15.
 */
public class DummyDynamicListViewAdapter extends BaseAdapter {

    private final Context mContext;

    private final int[] dummytStrings = {R.string.lipsum_short, R.string.lipsum_short_2, R.string.lipsum_short_3};

    private int count = (int) (Math.random() * 40) + 10;

    public DummyDynamicListViewAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void notifyDataSetChanged() {
        count = (int) (Math.random() * 40) + 10;
        super.notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_item_dynamic, parent, false);

        TextView mText = ViewHolder.get(convertView, R.id.text);
        final View mExtra = ViewHolder.get(convertView, R.id.view_extra);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExtra.getVisibility() == View.GONE) {
                    mExtra.setVisibility(View.VISIBLE);
                } else {
                    mExtra.setVisibility(View.GONE);
                }
            }
        });

        LogHelper.d("test", position + " - " + convertView.getMeasuredHeight());
        mText.setText(getContent(dummytStrings.length, position));
        return convertView;
    }

    private String getContent(int max, int position) {
        return position + " - " + mContext.getString(dummytStrings[position % max]);
    }

    public static class ViewHolder {

        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
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
