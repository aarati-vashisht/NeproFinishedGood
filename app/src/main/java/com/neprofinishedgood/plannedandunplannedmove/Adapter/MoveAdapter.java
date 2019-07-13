package com.neprofinishedgood.plannedandunplannedmove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.plannedandunplannedmove.model.StillageList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.ViewHolder>{

    private final List<StillageList> stillageDatumList;
    private List<StillageList> stillageDatumListFiltered;
    private Context context;
    private View view;

    public MoveAdapter(List<StillageList> stillageDatumList) {
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
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getStandardQty()+"");
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getItemStdQty()+"");
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
                Gson gson = new Gson();
                String putExtraData = gson.toJson(stillageDatumListFiltered.get(getAdapterPosition()));
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
//                    stillageDatumListFiltered = stillageDatumList;
//                } else {
//                    List<StillageList> filteredList = new ArrayList<>();
//                    for (StillageList row : stillageDatumList) {
//                        if (row.get().toLowerCase().equals(charSequence.toString().toLowerCase())) {
//                            filteredList.add(row);
//                            stillageDatumListFiltered.remove(row);
//                            Gson gson = new Gson();
//                            String putExtraData = gson.toJson(filteredList.get(0));
//                            context.startActivity(new Intent(context, RAFStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
//                        }
//                    }
//                    filteredList.addAll(stillageDatumListFiltered);
//                    stillageDatumListFiltered = filteredList;
//
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = stillageDatumListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                stillageDatumListFiltered = (ArrayList<StillageDatum>) filterResults.values;
//                notifyDataSetChanged();
//
//            }
//        };
//    }
}
