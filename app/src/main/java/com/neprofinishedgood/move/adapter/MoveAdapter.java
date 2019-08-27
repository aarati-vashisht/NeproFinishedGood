package com.neprofinishedgood.move.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.move.MoveActivity;
import com.neprofinishedgood.move.MoveStillageActivity;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.ViewHolder> {

    private final List<ScanStillageResponse> stillageDatumList;
    private List<ScanStillageResponse> stillageDatumListFiltered;
    private Context context;
    private View view;

    public MoveAdapter(List<ScanStillageResponse> stillageDatumList) {
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
        holder.textViewitem.setText(stillageDatumListFiltered.get(position).getItemId());
        holder.textViewNumber.setText(stillageDatumListFiltered.get(position).getStickerID());
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getStandardQty() + "");
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getItemStdQty() + "");
        holder.textViewitemDesc.setText(stillageDatumListFiltered.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewitem)
        TextView textViewitem;
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
                MoveActivity.getInstance().showProgress(MoveActivity.getInstance());
                MoveActivity.getInstance().iPlannedUnplannedPresenter.callScanStillageService(new MoveInput(stillageDatumListFiltered.get(getAdapterPosition()).getStickerID(),  MoveActivity.getInstance().userId));
//                Gson gson = new Gson();
//                String putExtraData = gson.toJson(stillageDatumListFiltered.get(getAdapterPosition()));
//                context.startActivity(new Intent(context, MoveStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
            }

        }
    }


}
