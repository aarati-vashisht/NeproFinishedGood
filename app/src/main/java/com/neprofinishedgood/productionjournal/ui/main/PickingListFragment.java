package com.neprofinishedgood.productionjournal.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.ProductionJournal;
import com.neprofinishedgood.productionjournal.adapter.PickingListAdapter;
import com.neprofinishedgood.productionjournal.model.PickingModel;
import com.neprofinishedgood.productionjournal.model.PickingListSearchInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;
import com.neprofinishedgood.productionjournal.presenter.IPickingListInterface;
import com.neprofinishedgood.productionjournal.presenter.IPickingListPresenter;
import com.neprofinishedgood.productionjournal.presenter.IPickingListView;
import com.neprofinishedgood.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    String itemName, itemId, workOrderNo, userId;

    IPickingListInterface iPickingListInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picking_list, container, false);
        ButterKnife.bind(this, rootView);

        workOrderNo = ProductionJournal.getInstance().workOrderNo;
        userId = ProductionJournal.getInstance().userId;
        iPickingListInterface = new IPickingListPresenter(this, getActivity());

        setAdapter();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.imageViewSearchButton)
    public void onImageViewSearchButtonClick() {
        PickingListSearchInput pickingListSearchInput = new PickingListSearchInput(ProductionJournal.getInstance().workOrderNo, ProductionJournal.getInstance().userId, editTextSearchItem.getText().toString().trim());
        iPickingListInterface.callSearchItemService(pickingListSearchInput);
    }

    @OnClick(R.id.buttonOk)
    public void buttonOk() {
        if (!editTextQuantity.getText().toString().equals("")) {
            PickingModel pickingData = new PickingModel(itemId, itemName, editTextQuantity.getText().toString());
            ProductionJournal.getInstance().pickingModelList.add(pickingData);
            adapter.notifyDataSetChanged();
            SharedPref.savePickingListData(new Gson().toJson(ProductionJournal.getInstance().pickingModelList));
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
        itemId = body.getItemId();
    }

    void setAdapter() {
        adapter = new PickingListAdapter(ProductionJournal.getInstance().pickingModelList);
        recyclerViewPickingList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPickingList.setAdapter(adapter);
        recyclerViewPickingList.setHasFixedSize(true);
    }
}
