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
import com.neprofinishedgood.productionjournal.model.PickingListDatum;
import com.neprofinishedgood.productionjournal.model.RouteCardPicked;
import com.neprofinishedgood.productionjournal.model.RoutingListDatum;
import com.neprofinishedgood.productionjournal.ui.main.PickingListFragment;
import com.neprofinishedgood.productionjournal.ui.main.RouteCardFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteCardAdapter extends RecyclerView.Adapter<RouteCardAdapter.ViewHolder> {

    private final List<RouteCardPicked> pickingListDatumList;
    private List<RouteCardPicked> routeListDatumListFiltered;
    private Context context;
    private View view;

    public RouteCardAdapter(List<RouteCardPicked> pickingListDatumList) {
        this.pickingListDatumList = pickingListDatumList;
        this.routeListDatumListFiltered = pickingListDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_card_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewItemName.setText(routeListDatumListFiltered.get(position).getOperationName());
        holder.textViewQuantity.setText(routeListDatumListFiltered.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return routeListDatumListFiltered.size();
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
                String operationId = routeListDatumListFiltered.get(getAdapterPosition()).getOperationId();
                String priority = routeListDatumListFiltered.get(getAdapterPosition()).getPriority();
                RoutingListDatum routingListDatum = null;
                for (int i = 0; i < ProductionJournal.getInstance().routingListDatumList.size(); i++) {
                    if (ProductionJournal.getInstance().routingListDatumList.get(i).getOperationId().equals(operationId) && ProductionJournal.getInstance().routingListDatumList.get(i).getPriority().equals(priority)) {
                        routingListDatum = ProductionJournal.getInstance().routingListDatumList.get(i);
                        RouteCardFragment.getInstance().spinnerOperation.setSelection(
                                RouteCardFragment.getInstance().operationAdapter.getPosition(routingListDatum));
                    }
                }

                RouteCardFragment.getInstance().updatePosition = getAdapterPosition();
                RouteCardFragment.getInstance().spinnerShift.setSelection(RouteCardFragment.getInstance().arrayAdapter.getPosition(routeListDatumListFiltered.get(getAdapterPosition()).getShift()));
                RouteCardFragment.getInstance().editTextDate.setText(routeListDatumListFiltered.get(getAdapterPosition()).getDate());
                RouteCardFragment.getInstance().editTextQuantity.setText(routeListDatumListFiltered.get(getAdapterPosition()).getQuantity());
                RouteCardFragment.getInstance().editTextHours.setText(routeListDatumListFiltered.get(getAdapterPosition()).getHours());
//                CustomToast.showToast(PickingListFragment.getInstance().getActivity(), ";skfdhglkjsdfglvkbdjn");


            } else if (v == back_layout) {
                RouteCardFragment.getInstance().deleteAddedData(getAdapterPosition());
            }
        }
    }
}
