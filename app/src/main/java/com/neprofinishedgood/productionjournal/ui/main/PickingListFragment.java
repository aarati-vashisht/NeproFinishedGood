package com.neprofinishedgood.productionjournal.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.ProductionJournal;
import com.neprofinishedgood.productionjournal.adapter.PickingListAdapter;
import com.neprofinishedgood.productionjournal.model.PickingList;
import com.neprofinishedgood.productionjournal.model.PickingListSearchInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.presenter.IPickingListInterface;
import com.neprofinishedgood.productionjournal.presenter.IPickingListPresenter;
import com.neprofinishedgood.productionjournal.presenter.IPickingListView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PickingListFragment extends Fragment implements IPickingListView {

    @BindView(R.id.recyclerViewPickingList)
    RecyclerView recyclerViewPickingList;

    @BindView(R.id.editTextSearchItem)
    AppCompatEditText editTextSearchItem;

    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;

    @BindView(R.id.linearLayoutItemName)
    LinearLayout linearLayoutItemName;

    @BindView(R.id.linearLayoutQuantity)
    LinearLayout linearLayoutQuantity;

    @BindView(R.id.textViewItemName)
    TextView textViewItemName;

    private PickingListAdapter adapter;
    View rootView;

    String itemName;

    IPickingListInterface iPickingListInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picking_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iPickingListInterface = new IPickingListPresenter(this, getActivity());
    }

    @OnClick(R.id.imageViewSearchButton)
    public void onImageViewSearchButtonClick() {
        PickingListSearchInput pickingListSearchInput = new PickingListSearchInput(ProductionJournal.getInstance().workOrderId, ProductionJournal.getInstance().userId, editTextSearchItem.getText().toString().trim());
        iPickingListInterface.callSearchItemService(pickingListSearchInput);
    }

    @OnClick(R.id.buttonOk)
    public void buttonOk() {
        if (!editTextQuantity.getText().toString().equals("")) {
            PickingList pickingList = new PickingList(itemName, editTextQuantity.getText().toString(), ProductionJournal.getInstance().workOrderNo);
            SharedPref.savePickingListData(new Gson().toJson(pickingList));
        } else {
            editTextQuantity.setError(getString(R.string.enter_quantity));
            editTextQuantity.requestFocus();
        }
    }

    @Override
    public void onPickingSearchFailure(String message) {
        BaseActivity.getInstance().hideProgress();
        CustomToast.showToast(rootView.getContext(), message);
    }

    @Override
    public void onPickingSearchSuccess(PickingListSearchResponse body) {
        BaseActivity.getInstance().hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body);
        } else {
            CustomToast.showToast(rootView.getContext(), body.getMessage());
            editTextSearchItem.setText("");
        }
    }

    private void setData(PickingListSearchResponse body) {
        editTextQuantity.setEnabled(true);
        linearLayoutQuantity.setVisibility(View.VISIBLE);
        linearLayoutItemName.setVisibility(View.VISIBLE);
        textViewItemName.setText(body.getItemName());

        itemName = body.getItemName();
    }
//
//	void setAdapter(){
//		adapter = new PickingListAdapter(stillageList);
//		recyclerViewPickingList.setAdapter(adapter);
//		recyclerViewPickingList.setHasFixedSize(true);
//	}
}
