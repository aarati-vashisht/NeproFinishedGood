package com.neprofinishedgood.pickandload;

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
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadStillagesAdapter extends RecyclerView.Adapter<PickAndLoadStillagesAdapter.ViewHolder> implements Filterable {

    private List<LoadingPlanList> stillageDatumList;
    private List<LoadingPlanList> stillageDatumListFiltered;
    private Context context;
    private View view;
    private String charString = "";

    public PickAndLoadStillagesAdapter(List<LoadingPlanList> stillageDatumList) {
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
        holder.textViewWorkOrder.setText(stillageDatumListFiltered.get(position).getWorkOrderQty()+"");
        holder.textViewitem.setText(stillageDatumListFiltered.get(position).getItemName());
        holder.textViewSite.setText(stillageDatumListFiltered.get(position).getSiteName());
        holder.textViewAisle.setText(stillageDatumListFiltered.get(position).getAisle());
        holder.textViewBin.setText(stillageDatumListFiltered.get(position).getBin());
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getStillageQty()+"");
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getStillageStdQty()+"");
        holder.textViewWarehouse.setText(stillageDatumListFiltered.get(position).getWareHouseID()+"");
        holder.textViewRack.setText(stillageDatumListFiltered.get(position).getRack());
// if (charString.equalsIgnoreCase(stillageDatumListFiltered.get(position).ge())) {
// if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("1")) {
// stillageDatumListFiltered.get(position).setStatus("");
// showCustomAlert(context, position);
// } else if (stillageDatumListFiltered.get(position).getStatus().equalsIgnoreCase("2")) {
// alertDialogForScanTAR(context, position);
// }
// }
        if (stillageDatumListFiltered.get(position).getStatus().equals("")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else if (stillageDatumListFiltered.get(position).getStatus().equals("-1")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
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
                LoadingPlanList beforFilerRow;
                charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stillageDatumListFiltered = stillageDatumList;
                } else {
                    List<LoadingPlanList> filteredList = new ArrayList<>();
                    for (LoadingPlanList row : stillageDatumList) {
                        beforFilerRow = row;
                        if (row.getLoadingNumber().equalsIgnoreCase(charSequence.toString())) {
                            if (row.getStatus().equalsIgnoreCase("")) {
                                row.setStatus("1");
                            } else if (row.getStatus().equalsIgnoreCase("-1")) {
                                row.setStatus("2");
                            }
                            filteredList.add(row);
                            stillageDatumList.remove(beforFilerRow);
                            stillageDatumList.add(0, row);
                            stillageDatumListFiltered.remove(row);
                            break;
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
                stillageDatumListFiltered = (ArrayList<LoadingPlanList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void showCustomAlert(Context context, int position) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.dialog_message));
        builder.setMessage(context.getString(R.string.do_you_want_to_pick_this_stillage))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        stillageDatumListFiltered.get(position).setStatus("-1");
                        notifyDataSetChanged();
                        PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                        CustomToast.showToast(context, stillageDatumListFiltered.get(position).getLoadingNumber() + " " + context.getString(R.string.picked_successfullt));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        stillageDatumListFiltered.get(position).setStatus("");
                        notifyDataSetChanged();
                        PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    int quatityToLoad, stdQuantity;

    public void alertDialogForQuantity(Context context, int position) {
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
                int quatityToLoad;
                if (editTextLoadQuantity.getText().toString().trim().equals("")) {
                    quatityToLoad = 0;
                } else {
                    quatityToLoad = Integer.parseInt(editTextLoadQuantity.getText().toString().trim());
                }
                int stdQuantity = stillageDatumListFiltered.get(position).getStillageQty();
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
                    CustomToast.showToast(context, context.getString(R.string.stillage_loaded_successfully));
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                stillageDatumListFiltered.get(position).setStatus("-1");
                PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
            }
        });
        dialog.show();

    }

// public void alertDialogForScanTAR(Context context, int position) {
// final Dialog dialog = new Dialog(context);
// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
// dialog.setCancelable(false);
// dialog.setContentView(R.layout.custom_alert_scan_t_a_r);
// dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
// EditText editTextScanReport = dialog.findViewById(R.id.editTextScanReport);
// CustomButton buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
// CustomButton buttonCancel = dialog.findViewById(R.id.buttonCancel);
// buttonConfirm.setOnClickListener(new View.OnClickListener() {
// @Override
// public void onClick(View v) {
// if (editTextScanReport.getText().toString().trim().length() == 0) {
// editTextScanReport.setError(context.getResources().getString(R.string.please_scan_t_a_r));
// editTextScanReport.requestFocus();
// } else if (!stillageDatumListFiltered.get(position).getLoadingPlan().equalsIgnoreCase(editTextScanReport.getText().toString().trim())) {
// editTextScanReport.setError(context.getResources().getString(R.string.stillage_loading_plan_doesnt_match_with_tar));
// editTextScanReport.requestFocus();
// } else {
// dialog.cancel();
// alertDialogForQuantity(context, position);
// }
// }
// });
// buttonCancel.setOnClickListener(new View.OnClickListener() {
// @Override
// public void onClick(View v) {
// dialog.dismiss();
// stillageDatumListFiltered.get(position).setStatus("-1");
// PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
// }
// });
// dialog.show();
//
// }

}