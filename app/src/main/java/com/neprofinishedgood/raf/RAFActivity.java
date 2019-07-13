package com.neprofinishedgood.raf;

import android.os.Bundle;
import android.text.Editable;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class RAFActivity extends BaseActivity {

    @BindView(R.id.recyclerViewStillage)
    RecyclerView recyclerViewStillage;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    RAFAdapter contingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        ButterKnife.bind(this);
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.reportasfinished));
       // setAdapter();
    }

    @OnTextChanged(value = R.id.editTextScanStillage,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        contingAdapter.getFilter().filter(editable);
    }

    private void setAdapter() {
        recyclerViewStillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<StillageDatum> getFormResponseList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            StillageDatum formDatum = new StillageDatum();
            formDatum.setNumber("S0000" + i);
            formDatum.setName("S" + i);
            formDatum.setItem("Item" + i);
            formDatum.setStdQuantity("20");
            formDatum.setQuantity("20");
            getFormResponseList.add(formDatum);
        }
        contingAdapter = new RAFAdapter(getFormResponseList);
        recyclerViewStillage.setAdapter(contingAdapter);
        recyclerViewStillage.setHasFixedSize(true);

    }
}
