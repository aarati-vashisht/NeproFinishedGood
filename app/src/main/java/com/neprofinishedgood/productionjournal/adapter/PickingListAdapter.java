package com.neprofinishedgood.productionjournal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.productionjournal.model.PickingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickingListAdapter extends RecyclerView.Adapter<PickingListAdapter.ViewHolder> {

    private final List<PickingModel> pickingModelList;
    private List<PickingModel> pickingModelListFiltered;
    private Context context;
    private View view;

    public PickingListAdapter(List<PickingModel> pickingModelList) {
        this.pickingModelList = pickingModelList;
        this.pickingModelListFiltered = pickingModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picking_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewItemName.setText(pickingModelListFiltered.get(position).getItemName());
        holder.textViewQuantity.setText(pickingModelListFiltered.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return pickingModelListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewItemName)
        TextView textViewItemName;
        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
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
//                if (v == mView) {
//                    Gson gson = new Gson();
//                    String putExtraData = gson.toJson(pickingModelListFiltered.get(getAdapterPosition()));
//                    context.startActivity(new Intent(context, MoveStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
//                }

        }
    }

}
