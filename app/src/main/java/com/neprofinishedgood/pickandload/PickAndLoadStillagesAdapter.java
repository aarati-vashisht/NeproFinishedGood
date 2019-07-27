package com.neprofinishedgood.pickandload;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;
import com.neprofinishedgood.pickandload.model.UpdateLoadInput;
import com.neprofinishedgood.plannedandunplannedmove.adapter.SpinnerAdapter;
import com.neprofinishedgood.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadStillagesAdapter extends RecyclerView.Adapter<PickAndLoadStillagesAdapter.ViewHolder> implements Filterable {

    private final List<LoadingPlanList> loadingPlanDetailList;
    private List<LoadingPlanList> stillageDatumList;
    public List<LoadingPlanList> stillageDatumListFiltered;
    List<LoadingPlanList> loadingPlanLists = new ArrayList<>();
    private Context context;
    private View view;
    private String charString = "";
    private String reason;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<LoadingPlanList> loadingPlanDetailLists;

    public PickAndLoadStillagesAdapter(List<LoadingPlanList> stillageDatumList) {
        this.stillageDatumList = stillageDatumList;
        this.stillageDatumListFiltered = stillageDatumList;
        this.loadingPlanDetailList = SharedPref.getLoadinGplanDetailList();
        for (LoadingPlanList loadingPlanList : this.stillageDatumListFiltered) {
            for (LoadingPlanList savedLoadingPlanList : this.loadingPlanDetailList) {
                if (savedLoadingPlanList.getLoadingNumber().equals(PickAndLoadStillageActivity.getInstance().loadingPlan)) {
                    if (savedLoadingPlanList.getStillageNO().equals(loadingPlanList.getStillageNO())) {
                        loadingPlanList.setStatus("-1");
                    }
                }
            }
        }
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
        viewBinderHelper.bind(holder.swipeRevealLayout, stillageDatumListFiltered.get(position).getStillageNO());

        holder.textViewitem.setText(stillageDatumListFiltered.get(position).getItemName());
        holder.textViewSite.setText(stillageDatumListFiltered.get(position).getSiteName());
        holder.textViewQuantity.setText(stillageDatumListFiltered.get(position).getStillageQty() + "");
        holder.textViewStdQuatity.setText(stillageDatumListFiltered.get(position).getStillageStdQty() + "");

        for (int j = 0; j < PickAndLoadStillageActivity.getInstance().warehouseList.size(); j++) {
            if (PickAndLoadStillageActivity.getInstance().warehouseList.get(j).getId().equals(stillageDatumListFiltered.get(position).getWareHouseID() + "")) {
                holder.textViewWarehouse.setText(PickAndLoadStillageActivity.getInstance().warehouseList.get(j).getName());
            }
        }
        holder.textViewNumber.setText(stillageDatumListFiltered.get(position).getStillageNO());
        holder.textViewLocation.setText(stillageDatumListFiltered.get(position).getAisle() + "-" + stillageDatumListFiltered.get(position).getRack() + "-" + stillageDatumListFiltered.get(position).getBin());

        if (stillageDatumListFiltered.get(position).getStatus().equals("")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else if (stillageDatumListFiltered.get(position).getStatus().equals("-1")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        if (stillageDatumListFiltered.get(position).getStatus().equals("1")) {
            showCustomAlert(context, position);
            stillageDatumListFiltered.get(position).setStatus("-1");
            stillageDatumList.get(position).setStatus("-1");
            holder.swipeRevealLayout.setLockDrag(true);
        } else if (stillageDatumListFiltered.get(position).getStatus().equals("2")) {
            alertDialogForQuantity(context, position);
            holder.swipeRevealLayout.setLockDrag(true);
        }
        if (!stillageDatumListFiltered.get(position).getStatus().equals("-1")) {
            holder.swipeRevealLayout.setLockDrag(true);
            holder.swipeRevealLayout.close(true);
        }

    }

    @Override
    public int getItemCount() {
        return stillageDatumListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        @BindView(R.id.textViewNumber)
        TextView textViewNumber;
        @BindView(R.id.textViewitem)
        TextView textViewitem;
        @BindView(R.id.textViewSite)
        TextView textViewSite;
        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
        @BindView(R.id.textViewStdQuatity)
        TextView textViewStdQuatity;
        @BindView(R.id.textViewWarehouse)
        TextView textViewWarehouse;
        @BindView(R.id.textViewLocation)
        TextView textViewLocation;
        @BindView(R.id.cardView)
        public
        CardView cardView;
        @BindView(R.id.view_background)
        public
        LinearLayout view_background;

        @BindView(R.id.swipeRevealLayout)
        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);

            view_background.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public void onClick(View v) {
            removeItem(getAdapterPosition());
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
                        if (row.getStillageNO().equalsIgnoreCase(charSequence.toString())) {
                            if (row.getStatus().equalsIgnoreCase("")) {
                                row.setStatus("1");
                            } else if (row.getStatus().equalsIgnoreCase("-1")) {
                                row.setStatus("2");
                            }
                            filteredList.add(row);
                            stillageDatumList.remove(beforFilerRow);
                            stillageDatumListFiltered.remove(row);
                            break;
                        }
                    }
                    filteredList.addAll(stillageDatumListFiltered);
                    stillageDatumListFiltered = filteredList;
                    stillageDatumList = filteredList;
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
                        stillageDatumListFiltered.get(position).setLoadingNumber(PickAndLoadStillageActivity.getInstance().loadingPlan);
                        loadingPlanLists = SharedPref.getLoadinGplanDetailList();
                        loadingPlanLists.add(stillageDatumListFiltered.get(position));
                        String saveLoadingPlanListData = new Gson().toJson(loadingPlanLists);
                        SharedPref.saveLoadinGplanDetailList(saveLoadingPlanListData);
                        notifyDataSetChanged();
                        PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                        CustomToast.showToast(context, stillageDatumListFiltered.get(position).getStillageNO() + " " + context.getString(R.string.picked_successfullt));
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

    public void removeItem(int position) {
        if (stillageDatumListFiltered.get(position).getStatus().equals("-1")) {
            stillageDatumListFiltered.get(position).setStatus("");
            loadingPlanDetailLists = SharedPref.getLoadinGplanDetailList();
            for (LoadingPlanList loadingPlanList : loadingPlanDetailLists) {
                if (loadingPlanList.getStillageNO().equals(stillageDatumListFiltered.get(position).getStillageNO())) {
                    loadingPlanDetailLists.remove(loadingPlanList);
                    SharedPref.saveLoadinGplanDetailList(new Gson().toJson(loadingPlanDetailLists));
                }
            }
            CustomToast.showToast(context, stillageDatumListFiltered.get(position).getStillageNO() + " " + context.getString(R.string.stillage_unpicked));
            notifyItemRemoved(position);
        }
    }

    public void restoreItem(LoadingPlanList item, int position) {
        stillageDatumListFiltered.add(position, item);
        notifyItemInserted(position);
    }

    public void alertDialogForQuantity(Context context, int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_confirm_loading_quantity);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText editTextLoadQuantity = dialog.findViewById(R.id.editTextLoadQuantity);
        CustomButton buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
        LinearLayout linearLayoutRejectReason = dialog.findViewById(R.id.linearLayoutRejectReason);
        Spinner spinnerRejectReason = dialog.findViewById(R.id.spinnerRejectReason);
        TextView textViewQuantityToBeLoad = dialog.findViewById(R.id.textViewQuantityToBeLoad);
        CustomButton buttonCancel = dialog.findViewById(R.id.buttonCancel);
        textViewQuantityToBeLoad.setText(stillageDatumListFiltered.get(position).getPickingQty() + "");
        editTextLoadQuantity.setText(textViewQuantityToBeLoad.getText().toString().trim());
        editTextLoadQuantity.requestFocus();
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(PickAndLoadStillageActivity.getInstance(), R.layout.spinner_layout, PickAndLoadStillageActivity.getInstance().reasonList);
        spinnerRejectReason.setAdapter(aisleListAdapter);
        spinnerRejectReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reason = PickAndLoadStillageActivity.getInstance().reasonList.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editTextLoadQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextLoadQuantity.getText().toString().trim().length() > 0)
                    if (Integer.parseInt(editTextLoadQuantity.getText().toString().trim()) < stillageDatumListFiltered.get(position).getPickingQty()) {
                        linearLayoutRejectReason.setVisibility(View.VISIBLE);
                        linearLayoutRejectReason.setAnimation(PickAndLoadStillageActivity.getInstance().fadeIn);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                }
                if (Integer.parseInt(editTextLoadQuantity.getText().toString().trim()) > stillageDatumListFiltered.get(position).getPickingQty()) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.load_quantiy_must_be_lower_than_tobeload));
                    editTextLoadQuantity.requestFocus();
                } else if (quatityToLoad > stdQuantity) {
                    editTextLoadQuantity.setError(context.getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty));
                    editTextLoadQuantity.requestFocus();
                } else {
                    dialog.cancel();
                    notifyDataSetChanged();
                    PickAndLoadStillageActivity.getInstance().editTextScanLoadingPlan.setText("");
                    PickAndLoadStillageActivity.getInstance().stillageNoToDelete = stillageDatumListFiltered.get(position).getStillageNO();
                    UpdateLoadInput updateLoadInput = new UpdateLoadInput(stillageDatumListFiltered.get(position).getStillageNO(), PickAndLoadStillageActivity.getInstance().userId, PickAndLoadStillageActivity.getInstance().scanLoadingPlanList.getTLPHID() + "", reason, stillageDatumListFiltered.get(position).getItemId() + "", editTextLoadQuantity.getText().toString().trim());
                    PickAndLoadStillageActivity.getInstance().iPickAndLoadItemInterFace.callUpdateLoadService(updateLoadInput);
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


}