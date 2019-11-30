package com.neprofinishedgood.move.adapter;

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
import com.neprofinishedgood.move.MoveActivity;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.move.model.TransferDetailInputModel;
import com.neprofinishedgood.move.model.TransferList;
import com.neprofinishedgood.pickandload.PickAndLoadStillageActivity;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferListAdapter extends RecyclerView.Adapter<TransferListAdapter.ViewHolder> /*implements Filterable*/ {

    private final List<TransferList> transferList;
    private List<TransferList> transferListFiltered;
    private Context context;
    private View view;

    public TransferListAdapter(List<TransferList> transferList) {
        this.transferList = transferList;
        this.transferListFiltered = transferList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewActivityId.setText(transferListFiltered.get(position).getActivityId());
        holder.textViewStillageCount.setText(transferListFiltered.get(position).getNoOfStillages());
    }

    @Override
    public int getItemCount() {
        return transferListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewActivityId)
        TextView textViewActivityId;
        @BindView(R.id.textViewStillageCount)
        TextView textViewStillageCount;

        @BindView(R.id.cardView)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            cardView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {
                if (NetworkChangeReceiver.isInternetConnected(MoveActivity.getInstance())) {
                    MoveActivity.getInstance().transferData = transferListFiltered.get(getAdapterPosition());
                    MoveActivity.getInstance().showProgress(MoveActivity.getInstance());
                    TransferDetailInputModel transferDetailInputModel = new TransferDetailInputModel(
                            MoveActivity.getInstance().userId,
                            transferListFiltered.get(getAdapterPosition()).getTATHID(),
                            transferListFiltered.get(getAdapterPosition()).getActivityId());
                    MoveActivity.getInstance().iPlannedUnplannedPresenter.callGetAssignTransferDetail(transferDetailInputModel);
                } else {
                    MoveActivity.getInstance().showSuccessDialog(MoveActivity.getInstance().getString(R.string.no_internet));
                }
            }
        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    transferListFiltered = transferList;
//                } else {
//                    List<TransferList> filteredList = new ArrayList<>();
//                    for (TransferList row : transferList) {
//                        if (row.getLoadingPlanNo().equalsIgnoreCase(charSequence.toString())) {
//                            filteredList.add(row);
//                            transferListFiltered.remove(row);
//                            Gson gson = new Gson();
//                            String putExtraData = gson.toJson(filteredList.get(0));
//                            context.startActivity(new Intent(context, PickAndLoadStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
//                        }
//                    }
//                    filteredList.addAll(transferListFiltered);
//                    transferListFiltered = filteredList;
//
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = transferListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                transferListFiltered = (ArrayList<TransferList>) filterResults.values;
//                notifyDataSetChanged();
//
//            }
//        };
//    }
}
