package com.neprofinishedgood.counting;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountingActivity extends BaseActivity {
    @BindView(R.id.recyclerViewStillage)
    RecyclerView recyclerViewStillage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        ButterKnife.bind(this);
        setTitle("Counting");
        setAdapter();
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
        recyclerViewStillage.setAdapter(new ContingAdapter(getFormResponseList));
        recyclerViewStillage.setHasFixedSize(true);

    }
}
