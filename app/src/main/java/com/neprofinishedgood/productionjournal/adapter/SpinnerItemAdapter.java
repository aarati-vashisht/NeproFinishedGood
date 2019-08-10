package com.neprofinishedgood.productionjournal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neprofinishedgood.R;
import com.neprofinishedgood.productionjournal.model.PickingModel;

import java.util.ArrayList;
import java.util.List;

public class SpinnerItemAdapter extends ArrayAdapter<PickingModel> {
    List<PickingModel> pickingModelList = new ArrayList<>();
    int res;
    private final LayoutInflater mInflater;

    public SpinnerItemAdapter(Context context, int resource, ArrayList<PickingModel> objects) {
        super(context, resource, objects);
        pickingModelList = objects;
        this.res = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PickingModel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(res, null);
        TextView textView = v.findViewById(R.id.text1);
        String str = pickingModelList.get(position).getItemName();
        textView.setText(str);
        return v;

    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(res, parent, false);

        TextView text = view.findViewById(R.id.text1);
        text.setText(pickingModelList.get(position).getItemName());

        return view;
    }
}
