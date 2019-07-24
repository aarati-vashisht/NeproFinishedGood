package com.neprofinishedgood.pickandload;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadAdapter extends RecyclerView.Adapter<PickAndLoadAdapter.ViewHolder> implements Filterable {

    private final List<ScanLoadingPlanList> scanLoadingPlanList;
    private List<ScanLoadingPlanList> scanLoadingPlanListFiltered;
    private Context context;
    private View view;

    public PickAndLoadAdapter(List<ScanLoadingPlanList> scanLoadingPlanList) {
        this.scanLoadingPlanList = scanLoadingPlanList;
        this.scanLoadingPlanListFiltered = scanLoadingPlanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loading_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewLoadingPlan.setText(scanLoadingPlanListFiltered.get(position).getLoadingPlanNo());
        holder.textViewCustomer.setText(scanLoadingPlanListFiltered.get(position).getCustomerId());

    }

    @Override
    public int getItemCount() {
        return scanLoadingPlanListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewLoadingPlan)
        TextView textViewLoadingPlan;
        @BindView(R.id.textViewCustomer)
        TextView textViewCustomer;

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
//                Gson gson = new Gson();
//                String putExtraData = gson.toJson(scanLoadingPlanListFiltered.get(getAdapterPosition()));
                String loadingPlanID = scanLoadingPlanListFiltered.get(getAdapterPosition()).getTLPHID()+"";
                context.startActivity(new Intent(context, PickAndLoadStillageActivity.class)
                        .putExtra(Constants.SELECTED_STILLAGE, loadingPlanID));
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
                    scanLoadingPlanListFiltered = scanLoadingPlanList;
                } else {
                    List<ScanLoadingPlanList> filteredList = new ArrayList<>();
                    for (ScanLoadingPlanList row : scanLoadingPlanList) {
                        if (row.getLoadingPlanNo().equalsIgnoreCase(charSequence.toString())) {
                            filteredList.add(row);
                            scanLoadingPlanListFiltered.remove(row);
                            Gson gson = new Gson();
                            String putExtraData = gson.toJson(filteredList.get(0));
                            context.startActivity(new Intent(context, PickAndLoadStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
                        }
                    }
                    filteredList.addAll(scanLoadingPlanListFiltered);
                    scanLoadingPlanListFiltered = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = scanLoadingPlanListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                scanLoadingPlanListFiltered = (ArrayList<ScanLoadingPlanList>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
