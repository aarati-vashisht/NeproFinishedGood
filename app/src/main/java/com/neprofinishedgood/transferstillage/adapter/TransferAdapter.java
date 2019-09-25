package com.neprofinishedgood.transferstillage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.TransferStillageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.ViewHolder> {

    private final List<ScanStillageResponse> stillageDetailsList;
    private List<ScanStillageResponse> stillageDetailsListFiltered;
    private Context context;
    private View view;

    public TransferAdapter(List<ScanStillageResponse> pickingListDatumList) {
        this.stillageDetailsList = pickingListDatumList;
        this.stillageDetailsListFiltered = pickingListDatumList;
    }

    @Override
    public TransferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stillage_layout_transfer, parent, false);

        return new TransferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TransferAdapter.ViewHolder holder, int position) {
        holder.textViewLocation.setText(stillageDetailsListFiltered.get(position).getLocation());
        holder.textViewitem.setText(stillageDetailsListFiltered.get(position).getItemId());
        holder.textViewNumber.setText(stillageDetailsListFiltered.get(position).getStickerID());
        holder.textViewQuantity.setText(stillageDetailsListFiltered.get(position).getStandardQty()+"");
        holder.textViewStdQuatity.setText(stillageDetailsListFiltered.get(position).getItemStdQty()+"");
        holder.textViewitemDesc.setText(stillageDetailsListFiltered.get(position).getDescription());
        holder.textViewWarehouse.setText(stillageDetailsListFiltered.get(position).getWareHouseID() + " | " + stillageDetailsListFiltered.get(position).getWareHouseName());

    }

    @Override
    public int getItemCount() {
        return stillageDetailsListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewLocation)
        TextView textViewLocation;

        @BindView(R.id.textViewitem)
        TextView textViewitem;

        @BindView(R.id.textViewWarehouse)
        TextView textViewWarehouse;

        @BindView(R.id.textViewNumber)
        TextView textViewNumber;

        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;

        @BindView(R.id.textViewStdQuatity)
        TextView textViewStdQuatity;

        @BindView(R.id.textViewitemDesc)
        TextView textViewitemDesc;

        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.back_layout)
        FrameLayout back_layout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            mView.setOnClickListener(this);

            cardView.setOnClickListener(this);
            back_layout.setOnClickListener(this);
        }

        @Override
        public String toString() {
            //return super.toString() + " '" + textViewReference.getText() + "'";
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {

            } else if (v == back_layout) {
                TransferStillageActivity.getInstance().deleteAddedData(getAdapterPosition());
            }
        }
    }
}
