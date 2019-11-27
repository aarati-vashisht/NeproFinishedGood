package com.neprofinishedgood.qualitycheck.rejectquantity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RejectionListAdapter extends RecyclerView.Adapter<RejectionListAdapter.ViewHolder> {

    private final ArrayList<RejectedInput> rejectionList;
    private ArrayList<RejectedInput> rejectionListFiltered;
    private Context context;
    private View view;

    public RejectionListAdapter(ArrayList<RejectedInput> rejectionList) {
        this.rejectionList = rejectionList;
        this.rejectionListFiltered = rejectionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rejection_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewStickerNo.setText(rejectionListFiltered.get(position).getStickerNo());
        String unit = "";
        if (rejectionListFiltered.get(position).getIsKg().equals("1")) {
            unit = "Kg.";
        } else {
            unit = "Pcs.";
        }
        holder.textViewQuantity.setText(rejectionListFiltered.get(position).getQuantity() + " " + unit);
        holder.textViewShift.setText(rejectionListFiltered.get(position).getShift());
        holder.textViewReason.setText(rejectionListFiltered.get(position).getReasonName());
    }

    @Override
    public int getItemCount() {
        return rejectionListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewStickerNo)
        public TextView textViewStickerNo;

        @BindView(R.id.textViewQuantity)
        public TextView textViewQuantity;

        @BindView(R.id.textViewShift)
        public TextView textViewShift;

        @BindView(R.id.textViewReason)
        public TextView textViewReason;

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
