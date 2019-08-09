package com.neprofinishedgood.productionjournal.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
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
import butterknife.OnTextChanged;

public class PickingListFragment extends Fragment implements IPickingListView {

    @BindView(R.id.recyclerViewPickingList)
    RecyclerView recyclerViewPickingList;

    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;

    @BindView(R.id.spinnerItem)
    Spinner spinnerItem;

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

    private void setData(PickingListSearchResponse body) {
        editTextQuantity.setEnabled(true);
        itemName = body.getItemName();
        itemId = body.getItemId();
    }

    void setAdapter() {
        adapter = new PickingListAdapter(ProductionJournal.getInstance().pickingModelList);
        recyclerViewPickingList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPickingList.setAdapter(adapter);
        recyclerViewPickingList.setHasFixedSize(true);
    }

    @Override
    public void onPickingSearchFailure(String message) {

    }

    @Override
    public void onPickingSearchSuccess(PickingListSearchResponse body) {

    }

}
