package com.neprofinishedgood.qualitycheck.rejectquantity.adapter;

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
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.pickandload.PickAndLoadAdapter;
import com.neprofinishedgood.pickandload.PickAndLoadStillageActivity;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RejectionListAdapter extends RecyclerView.Adapter<RejectionListAdapter.ViewHolder> {

    private final List<ScanStillageResponse> stillageDatumList;
    private List<ScanStillageResponse> stillageDatumListFiltered;
    private Context context;
    private View view;

    public RejectionListAdapter(List<ScanStillageResponse> stillageDatumList) {
        this.stillageDatumList = stillageDatumList;
        this.stillageDatumListFiltered = stillageDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stillage_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewitem.setText(stillageDatumListFiltered.get(position).getItemId());
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getStandardQty() + "");
        holder.textViewitemDesc.setText(stillageDatumListFiltered.get(position).getDescription());
        holder.textViewStdQuantity.setText(stillageDatumListFiltered.get(position).getItemStdQty() + "");
        holder.textViewNumber.setText(stillageDatumListFiltered.get(position).getStickerID());
    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewitem)
        public TextView textViewitem;

        @BindView(R.id.textViewNumber)
        public TextView textViewNumber;

        @BindView(R.id.textViewQuantity)
        public TextView textViewQuantity;

        @BindView(R.id.textViewStdQuatity)
        public TextView textViewStdQuantity;

        @BindView(R.id.textViewitemDesc)
        public TextView textViewitemDesc;

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
        }
    }

}
