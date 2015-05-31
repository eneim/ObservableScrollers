package im.ene.lab.obervablescrollers.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.ene.lab.obervablescrollers.sample.R;

/**
 * Created by eneim on 3/26/15.
 */
public class DummyRecyclerAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View vh = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new RecyclerView.ViewHolder(vh) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(R.string.lipsum_short);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
