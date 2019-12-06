package com.neprofinishedgood.move.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.move.MoveTransferActivity;
import com.neprofinishedgood.move.model.TransferStillageList;
import com.neprofinishedgood.pickandload.PickAndLoadStillageActivity;
import com.neprofinishedgood.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferDetailAdapter extends RecyclerView.Adapter<TransferDetailAdapter.ViewHolder> implements Filterable {
//    private List<TransferStillageList> stillageDetailsList;
    private List<TransferStillageList> stillageDetailsListFiltered;
    private Context context;
    private View view;
    private String charString = "";
    private List<TransferStillageList> stillageList;

    public TransferDetailAdapter(List<TransferStillageList> pickingListDatumList) {
//        this.stillageDetailsList = pickingListDatumList;
        this.stillageDetailsListFiltered = pickingListDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stillage_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewNumber.setText(stillageDetailsListFiltered.get(position).getStillageID());
        holder.textViewitem.setText(stillageDetailsListFiltered.get(position).getItemID());
        holder.textViewitemDesc.setText(stillageDetailsListFiltered.get(position).getItemDesc());

        try {
            float qty = Float.parseFloat(stillageDetailsListFiltered.get(position).getStillageQty());
            holder.textViewQuantity.setText(MoveTransferActivity.getInstance().roundWithPlace(qty, 2) + "");
        } catch (Exception e) {
            e.printStackTrace();
            holder.textViewQuantity.setText(stillageDetailsListFiltered.get(position).getStillageQty());
        }

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));

//        if (stillageDetailsListFiltered.get(position).getStatus().equalsIgnoreCase("-2")) {
//            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.blue_light));
//        }
        if (stillageDetailsListFiltered.get(position).getStatus().equalsIgnoreCase("1")) {
            pickStillage(position);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.card_background_dark));
        }
        if (stillageDetailsListFiltered.get(position).getStatus().equalsIgnoreCase("2")) {
            transferStillge(position);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.card_background_light));
        }

    }

    private void pickStillage(int position) {
//        List<TransferList> transferLists = SharedPref.getTransferDetailList();
//        for (TransferList transferList : transferLists) {
//            if (transferList.getActivityId().equalsIgnoreCase(MoveTransferActivity.getInstance().transferData.getActivityId())) {
//                stillageList = transferList.getStillageList();
//                break;
//            }
//        }
//        stillageList.get(position).setStatus("-1");
        MoveTransferActivity.savedTransferData.setStillageList(stillageDetailsListFiltered);
//        stillageDetailsListFiltered.get(position).setStatus("-1");
    }

    private void transferStillge(int position) {
        MoveTransferActivity.savedTransferData.setStillageList(stillageDetailsListFiltered);
//        stillageDetailsListFiltered.get(position).setStatus("-2");
    }

    @Override
    public int getItemCount() {
        return stillageDetailsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                TransferStillageList beforFilerRow;
                charString = charSequence.toString();
                if (charString.isEmpty()) {
//                    stillageDetailsListFiltered = stillageDetailsList;
                } else {
                    List<TransferStillageList> filteredList = new ArrayList<>();


//                    for (TransferStillageList row : stillageDetailsList) {
//                        beforFilerRow = row;
//                        if (row.getStillageID().equalsIgnoreCase(charSequence.toString())) {
//                            if (row.getStatus().equalsIgnoreCase("")) {
//                                row.setStatus("1");
//                                Toast.makeText(context, "Stillage Picked", Toast.LENGTH_SHORT).show();
////                                showCustomAlert(context,row.getStillageID() + " " +context.getResources().getString(R.string.picked_successfully));
//                            } else if (row.getStatus().equalsIgnoreCase("1")) {
//                                row.setStatus("2");
//                                Toast.makeText(context, "Stillage Transferred", Toast.LENGTH_SHORT).show();
////                                showCustomAlert(context,row.getStillageID() + " " +context.getResources().getString(R.string.transferred_successfully));
//                            } else if (row.getStatus().equalsIgnoreCase("2")) {
//                                Toast.makeText(context, "Already Transferred", Toast.LENGTH_SHORT).show();
////                                showCustomAlert(context,context.getResources().getString(R.string.already_ransferred));
//                            }
//                            filteredList.add(row);
//                            stillageDetailsList.remove(beforFilerRow);
//                            stillageDetailsListFiltered.remove(row);
//                            break;
//                        }
//                    }

                    int indexToDelete = -1;
                    for (int i = 0; i < stillageDetailsListFiltered.size(); i++) {
                        beforFilerRow = stillageDetailsListFiltered.get(i);
                        if (beforFilerRow.getStillageID().equalsIgnoreCase(charSequence.toString())) {
                            if (beforFilerRow.getStatus().equalsIgnoreCase("")) {
                                beforFilerRow.setStatus("1");
                                Toast.makeText(context, "Stillage Picked", Toast.LENGTH_SHORT).show();
//                                showCustomAlert(context,row.getStillageID() + " " +context.getResources().getString(R.string.picked_successfully));
                            } else if (beforFilerRow.getStatus().equalsIgnoreCase("1")) {
                                beforFilerRow.setStatus("2");
                                Toast.makeText(context, "Stillage Transferred", Toast.LENGTH_SHORT).show();
//                                showCustomAlert(context,row.getStillageID() + " " +context.getResources().getString(R.string.transferred_successfully));
                            } else if (beforFilerRow.getStatus().equalsIgnoreCase("2")) {
                                Toast.makeText(context, "Already Transferred", Toast.LENGTH_SHORT).show();
//                                showCustomAlert(context,context.getResources().getString(R.string.already_ransferred));
                            }
                            filteredList.add(beforFilerRow);
                            indexToDelete = i;
                            break;
                        }
                    }

                    if (indexToDelete != -1) {
//                        stillageDetailsList.remove(indexToDelete);
                        stillageDetailsListFiltered.remove(indexToDelete);
                    }


                    filteredList.addAll(stillageDetailsListFiltered);
                    stillageDetailsListFiltered = filteredList;
//                    stillageDetailsList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stillageDetailsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stillageDetailsListFiltered = (ArrayList<TransferStillageList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewitem)
        TextView textViewitem;

        @BindView(R.id.textViewNumber)
        TextView textViewNumber;

        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;

        @BindView(R.id.textViewStdQtyHead)
        TextView textViewStdQtyHead;

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
            textViewStdQtyHead.setVisibility(View.GONE);
            textViewStdQuatity.setVisibility(View.GONE);

            cardView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            //return super.toString() + " '" + textViewReference.getText() + "'";
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {

            }
        }
    }

//    public void showCustomAlert(Context context, String message) {
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(context);
//        builder.setMessage(message)
//                .setCancelable(true)
//                .setPositiveButton("Ok", (dialog, id) -> {
//                    dialog.cancel();
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
