package com.neprofinishedgood.counting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContingAdapter extends RecyclerView.Adapter<ContingAdapter.ViewHolder> implements Filterable {

    private final List<StillageDatum> stillageDatumList;
    private List<StillageDatum> stillageDatumListFiltered;
    private Context context;
    private View view;

    public ContingAdapter(List<StillageDatum> stillageDatumList) {
        this.stillageDatumList = stillageDatumList;
        this.stillageDatumListFiltered = stillageDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stillage_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewitem.setText(stillageDatumListFiltered.get(position).getItem());
        holder.textViewName.setText(stillageDatumListFiltered.get(position).getName());
        holder.textViewNumber.setText(stillageDatumListFiltered.get(position).getNumber());
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getQuantity());
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getStdQuantity());


    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewitem)
        TextView textViewitem;
        @BindView(R.id.textViewNumber)
        TextView textViewNumber;
        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
        @BindView(R.id.textViewStdQuatity)
        TextView textViewStdQuatity;
        @BindView(R.id.cardView)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            mView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            //return super.toString() + " '" + textViewReference.getText() + "'";
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == mView) {
                CustomToast.showTOast(context, "Clicked on " + getAdapterPosition());
            }

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stillageDatumListFiltered = stillageDatumList;
                } else {
                    List<StillageDatum> filteredList = new ArrayList<>();
                    for (StillageDatum row : stillageDatumList) {
                        if (row.getNumber().toLowerCase().equals(charSequence.toString().toLowerCase())) {
                            filteredList.add(row);
                            stillageDatumListFiltered.remove(row);
                        }
                    }
                    filteredList.addAll(stillageDatumListFiltered);
                    stillageDatumListFiltered = filteredList;
                }
                //notifyDataSetChanged();
                FilterResults filterResults = new FilterResults();
                filterResults.values = stillageDatumListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stillageDatumListFiltered = (ArrayList<StillageDatum>) filterResults.values;
                ViewHolder viewHolder = new ViewHolder(view);
                notifyDataSetChanged();
            }
        };
    }
}
