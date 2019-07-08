package com.neprofinishedgood.pickandhold;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadStillagesAdapter extends RecyclerView.Adapter<PickAndLoadStillagesAdapter.ViewHolder> implements Filterable {

    private final List<StillageDatum> stillageDatumList;
    private List<StillageDatum> stillageDatumListFiltered;
    private Context context;
    private View view;
    private String charString = "";

    public PickAndLoadStillagesAdapter(List<StillageDatum> stillageDatumList) {
        this.stillageDatumList = stillageDatumList;
        this.stillageDatumListFiltered = stillageDatumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loading_item_layout, parent, false);
        return new ViewHolder(view);
    }

    ViewHolder viewHolder;

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        viewHolder = new ViewHolder(view);
        holder.textViewWorkOrder.setText(stillageDatumListFiltered.get(position).getNumber());
        holder.textViewitem.setText("Item" + position + 1);
        holder.textViewSite.setText("Site" + position + 1);
        holder.textViewAisle.setText("A" + position + 1);
        holder.textViewBin.setText("B" + position + 1);
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getQuantity());
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getStdQuantity());
        holder.textViewWarehouse.setText("W" + position + 1);
        holder.textViewRack.setText("R" + position + 1);
        if (charString.equalsIgnoreCase(stillageDatumListFiltered.get(position).getNumber())) {
            if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("1")) {
                showCustomAlert(context, position);
            } else if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("2")) {
                showDialog(context, position);
            }
        }
        if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("-1")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("2")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }


    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewWorkOrder)
        TextView textViewWorkOrder;
        @BindView(R.id.textViewitem)
        TextView textViewitem;
        @BindView(R.id.textViewSite)
        TextView textViewSite;
        @BindView(R.id.textViewAisle)
        TextView textViewAisle;
        @BindView(R.id.textViewBin)
        TextView textViewBin;

        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
        @BindView(R.id.textViewStdQuatity)
        TextView textViewStdQuatity;
        @BindView(R.id.textViewWarehouse)
        TextView textViewWarehouse;
        @BindView(R.id.textViewRack)
        TextView textViewRack;

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


        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stillageDatumListFiltered = stillageDatumList;
                } else {
                    List<StillageDatum> filteredList = new ArrayList<>();
                    for (StillageDatum row : stillageDatumList) {
                        if (row.getNumber().equalsIgnoreCase(charSequence.toString())) {
                            if (row.getStatus().equalsIgnoreCase("")) {
                                row.setStatus("1");
                            } else if (row.getStatus().equalsIgnoreCase("-1")) {
                                row.setStatus("2");
                            }
                            filteredList.add(row);
                            stillageDatumListFiltered.remove(row);
                        }
                    }
                    filteredList.addAll(stillageDatumListFiltered);
                    stillageDatumListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stillageDatumListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stillageDatumListFiltered = (ArrayList<StillageDatum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void showCustomAlert(Context context, int position) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.dialog_message));
        builder.setMessage("Do you want to pick this stillage ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        stillageDatumListFiltered.get(position).setStatus("-1");
                        notifyDataSetChanged();
                        PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    int quatityToLoad, stdQuantity;

    public void showDialog(Context context, int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_confirm_loading_quantity);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText editTextLoadQuantity = dialog.findViewById(R.id.editTextLoadQuantity);
        CustomButton buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
        CustomButton buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quatityToLoad = Integer.parseInt(editTextLoadQuantity.getText().toString().trim());
                int stdQuantity = Integer.parseInt(stillageDatumListFiltered.get(position).getQuantity());
                if (editTextLoadQuantity.getText().toString().trim().length() == 0) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.please_add_load_quantity));
                    editTextLoadQuantity.requestFocus();
                } else if (quatityToLoad > stdQuantity) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty));
                    editTextLoadQuantity.requestFocus();
                } else {
                    dialog.cancel();
                    stillageDatumListFiltered.remove(stillageDatumListFiltered.get(position));
                    stillageDatumList.remove(stillageDatumList.get(position));
                    notifyDataSetChanged();
                    PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
