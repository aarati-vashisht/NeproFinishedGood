package com.neprofinishedgood.pickandload;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadAdapter extends RecyclerView.Adapter<PickAndLoadAdapter.ViewHolder> implements Filterable {

    private final List<ScanLoadingPlanList> stillageDatumList;
    private List<ScanLoadingPlanList> stillageDatumListFiltered;
    private Context context;
    private View view;
    String customerName;
//    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public PickAndLoadAdapter(List<ScanLoadingPlanList> stillageDatumList) {
        this.stillageDatumList = stillageDatumList;
        this.stillageDatumListFiltered = stillageDatumList;
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
//        viewBinderHelper.bind(holder.swipeRevealLayout, stillageDatumListFiltered.get(position).getLoadingPlanNo());
//        holder.swipeRevealLayout.close(true);
//        viewBinderHelper.setOpenOnlyOne(true);

        holder.textViewLoadingPlan.setText(stillageDatumListFiltered.get(position).getLoadingPlanNo());
        holder.textViewCustomer.setText(stillageDatumListFiltered.get(position).getCustomerId());

    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewLoadingPlan)
        TextView textViewLoadingPlan;
        @BindView(R.id.textViewCustomer)
        TextView textViewCustomer;

        @BindView(R.id.cardView)
        CardView cardView;
//        @BindView(R.id.back_layout)
//        FrameLayout back_layout;
//        @BindView(R.id.swipeRevealLayout)
//        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            cardView.setOnClickListener(this);
//            back_layout.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {
                Gson gson = new Gson();
                String putExtraData = gson.toJson(stillageDatumListFiltered.get(getAdapterPosition()));
                context.startActivity(new Intent(context, PickAndLoadStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
            }
//            else if (v == back_layout) {
//                PickAndLoadActivity.getInstance().LpNoToDelete = stillageDatumListFiltered.get(getAdapterPosition()).getLoadingPlanNo();
//                PickAndLoadActivity.getInstance().showProgress(PickAndLoadActivity.getInstance());
//                PickAndLoadActivity.getInstance().iPickAndLoadInterFace.callCancelLoadingPlan
//                        (new LoadingPlanInput(stillageDatumListFiltered.get(getAdapterPosition()).getTLPHID() + "",
//                                PickAndLoadActivity.getInstance().userId, ""));
////                CustomToast.showToast(context, "deleted successfully " + getAdapterPosition());
//            }

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
                    List<ScanLoadingPlanList> filteredList = new ArrayList<>();
                    for (ScanLoadingPlanList row : stillageDatumList) {
                        if (row.getLoadingPlanNo().equalsIgnoreCase(charSequence.toString())) {
                            filteredList.add(row);
                            stillageDatumListFiltered.remove(row);
                            Gson gson = new Gson();
                            String putExtraData = gson.toJson(filteredList.get(0));
                            context.startActivity(new Intent(context, PickAndLoadStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
                        }
                    }
                    filteredList.addAll(stillageDatumListFiltered);
                    stillageDatumListFiltered = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stillageDatumListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stillageDatumListFiltered = (ArrayList<ScanLoadingPlanList>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}