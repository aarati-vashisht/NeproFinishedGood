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
import com.neprofinishedgood.pickandload.model.LoadingPlanList1;
import com.neprofinishedgood.raf.model.StillageDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadStillagesAdapter extends RecyclerView.Adapter<PickAndLoadStillagesAdapter.ViewHolder> implements Filterable {

    private List<LoadingPlanList1> loadingPlanList;
    private List<LoadingPlanList1> loadingPlanListFiltered;
    private Context context;
    private View view;
    private String charString = "";

    public PickAndLoadStillagesAdapter(List<LoadingPlanList1> loadingPlanList) {
        this.loadingPlanList = loadingPlanList;
        this.loadingPlanListFiltered = loadingPlanList;
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
        holder.textViewWorkOrder.setText(loadingPlanListFiltered.get(position).getWorkOrderQty()+"");
        holder.textViewitem.setText(loadingPlanListFiltered.get(position).getItemId()+"");
        holder.textViewSite.setText(loadingPlanListFiltered.get(position).getSiteName());
        holder.textViewAisle.setText(loadingPlanListFiltered.get(position).getAisle());
        holder.textViewBin.setText(loadingPlanListFiltered.get(position).getBin());
        holder.textViewQuantity.setText(loadingPlanListFiltered.get(position).getStillageQty()+"");
        holder.textViewStdQuatity.setText(loadingPlanListFiltered.get(position).getStillageStdQty()+"");
        holder.textViewWarehouse.setText(loadingPlanListFiltered.get(position).getWareHouseID()+"");
        holder.textViewRack.setText(loadingPlanListFiltered.get(position).getRack());
        if (charString.equalsIgnoreCase(loadingPlanListFiltered.get(position).getWorkOrderQty()+"")) {
            if (loadingPlanListFiltered.get(position).getStatus().equalsIgnoreCase("1")) {
                loadingPlanListFiltered.get(position).setStatus("");
                showCustomAlert(context, position);
            } else if (loadingPlanListFiltered.get(position).getStatus().equalsIgnoreCase("2")) {
                alertDialogForScanTAR(context, position);
            }
        }
        if (loadingPlanListFiltered.get(position).getStatus().equals("")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else if (loadingPlanListFiltered.get(position).getStatus().equals("-1")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }


    }

    @Override
    public int getItemCount() {
        return loadingPlanListFiltered.size();
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

            showCustomAlert(context, getAdapterPosition());
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                LoadingPlanList1 beforFilerRow;
                charString = charSequence.toString();
                if (charString.isEmpty()) {
                    loadingPlanListFiltered = loadingPlanList;
                } else {
                    List<LoadingPlanList1> filteredList = new ArrayList<>();
                    for (LoadingPlanList1 row : loadingPlanList) {
                        beforFilerRow = row;
                        if (row.getWorkOrderQty().toString().equalsIgnoreCase(charSequence.toString())) {
                            if (row.getStatus().equalsIgnoreCase("")) {
                                row.setStatus("1");
                            } else if (row.getStatus().equalsIgnoreCase("-1")) {
                                row.setStatus("2");
                            }
                            filteredList.add(row);
                            loadingPlanList.remove(beforFilerRow);
                            loadingPlanList.add(0, row);
                            loadingPlanListFiltered.remove(row);
                            break;
                        }
                    }
                    filteredList.addAll(loadingPlanListFiltered);
                    loadingPlanListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = loadingPlanListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                loadingPlanListFiltered = (ArrayList<LoadingPlanList1>) filterResults.values;
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
                        loadingPlanListFiltered.get(position).setStatus("-1");
                        notifyDataSetChanged();
                        PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                        CustomToast.showToast(context, loadingPlanListFiltered.get(position).getWorkOrderQty() + " " + context.getString(R.string.picked_successfullt));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        loadingPlanListFiltered.get(position).setStatus("");
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
                int stdQuantity = Integer.parseInt(loadingPlanListFiltered.get(position).getStillageQty()+"");
                if (editTextLoadQuantity.getText().toString().trim().length() == 0) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.please_add_load_quantity));
                    editTextLoadQuantity.requestFocus();
                } else if (quatityToLoad > stdQuantity) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty));
                    editTextLoadQuantity.requestFocus();
                } else {
                    dialog.cancel();
                    loadingPlanListFiltered.remove(loadingPlanListFiltered.get(position));
                    loadingPlanList.remove(loadingPlanList.get(position));
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
                loadingPlanListFiltered.get(position).setStatus("-1");
                PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
            }
        });
        dialog.show();

    }

    public void alertDialogForScanTAR(Context context, int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_scan_t_a_r);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText editTextScanReport = dialog.findViewById(R.id.editTextScanReport);
        CustomButton buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
        CustomButton buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextScanReport.getText().toString().trim().length() == 0) {
                    editTextScanReport.setError(context.getResources().getString(R.string.please_scan_t_a_r));
                    editTextScanReport.requestFocus();
                } else if (!PickAndLoadStillageActivity.loadingPlanNumber.equalsIgnoreCase(editTextScanReport.getText().toString().trim())) {
                    editTextScanReport.setError(context.getResources().getString(R.string.stillage_loading_plan_doesnt_match_with_tar));
                    editTextScanReport.requestFocus();
                } else {
                    dialog.cancel();
                    alertDialogForQuantity(context, position);
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                loadingPlanListFiltered.get(position).setStatus("-1");
                PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
            }
        });
        dialog.show();

    }

}
