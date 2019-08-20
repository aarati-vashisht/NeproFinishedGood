package com.neprofinishedgood.productionjournal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.ProductionJournal;
import com.neprofinishedgood.productionjournal.model.ItemPicked;
import com.neprofinishedgood.productionjournal.model.PickingListDatum;
import com.neprofinishedgood.productionjournal.ui.main.PickingListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickingListAdapter extends RecyclerView.Adapter<PickingListAdapter.ViewHolder> {

    private final List<ItemPicked> pickingListDatumList;
    private List<ItemPicked> pickingListDatumListFiltered;
    private Context context;
    private View view;

    public PickingListAdapter(List<ItemPicked> pickingListDatumList) {
        this.pickingListDatumList = pickingListDatumList;
        this.pickingListDatumListFiltered = pickingListDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picking_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewItemName.setText(pickingListDatumListFiltered.get(position).getItemName());
        holder.textViewQuantity.setText(pickingListDatumListFiltered.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return pickingListDatumListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewItemName)
        TextView textViewItemName;
        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
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
                String itemId = pickingListDatumListFiltered.get(getAdapterPosition()).getItemId();
                PickingListDatum pickingListDatum  = null;
                for(int i=0; i< ProductionJournal.getInstance().pickingListDatumList.size();i++){
                    if(ProductionJournal.getInstance().pickingListDatumList.get(i).getItemId().equals(itemId)){
                        pickingListDatum = ProductionJournal.getInstance().pickingListDatumList.get(i);
                        PickingListFragment.getInstance().spinnerItem.setSelection(
                                PickingListFragment.getInstance().itemAdapter.getPosition(pickingListDatum));
                    }
                }
                PickingListFragment.getInstance().spinnerShift.setSelection(PickingListFragment.getInstance().arrayAdapter.getPosition(pickingListDatumListFiltered.get(getAdapterPosition()).getShift()));
                PickingListFragment.getInstance().editTextDate.setText(pickingListDatumListFiltered.get(getAdapterPosition()).getDate());
                PickingListFragment.getInstance().editTextQuantity.setText(pickingListDatumListFiltered.get(getAdapterPosition()).getQuantity());
//                CustomToast.showToast(PickingListFragment.getInstance().getActivity(), ";skfdhglkjsdfglvkbdjn");




            } else if (v == back_layout) {
                CustomToast.showToast(PickingListFragment.getInstance().getActivity(), "Cancelled");
            }
        }
    }
}
