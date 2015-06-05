package im.ene.lab.obervablescrollers.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ene.lab.obervablescrollers.sample.R;

/**
 * Created by eneim on 3/26/15.
 */
public class DummyRecyclerAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View vh = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card_item, viewGroup, false);
        return new RecyclerView.ViewHolder(vh) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
