package com.ndandey.android.primenumber.adapters;

import java.util.List;

import com.ndandey.android.primenumber.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ndandey on 7/11/2017.
 */
//public class PrimeNumberListAdapter extends RecyclerView.Adapter {
//}

public class PrimeNumberListAdapter extends RecyclerView.Adapter<PrimeNumberListAdapter.ViewHolder> {
    private final List<String> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView txtHeader;
        public final TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.primeindex);
            txtFooter = (TextView) v.findViewById(R.id.primenumber);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrimeNumberListAdapter(List<String> myDataset) {
        values = myDataset;
    }

    public void appendMoreData(List<String> pMoreData)
    {
        int vCurrentIndex = values.size();
        values.addAll(pMoreData);
        notifyItemRangeInserted(vCurrentIndex, pMoreData.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrimeNumberListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.prime_number_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.txtHeader.setText( (position+1)  + ".");
        holder.txtFooter.setText(name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}