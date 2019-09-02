package com.neprofinishedgood.productionjournal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neprofinishedgood.R;
import com.neprofinishedgood.productionjournal.model.PickingListDatum;

import java.util.ArrayList;
import java.util.List;

public class SpinnerItemAdapter extends ArrayAdapter<PickingListDatum> {
    List<PickingListDatum> pickingListDatumList = new ArrayList<>();
    int res;
    private final LayoutInflater mInflater;

    public SpinnerItemAdapter(Context context, int resource, ArrayList<PickingListDatum> objects) {
        super(context, resource, objects);
        pickingListDatumList = objects;
        this.res = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PickingListDatum getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(res, null);
        TextView textView1 = v.findViewById(R.id.text1);
        String str = pickingListDatumList.get(position).getItemId();
        String str1 = pickingListDatumList.get(position).getItemName();
        if(position > 0){
            textView1.setText(str + " | " + str1);
        }else{
            textView1.setText(str);
        }
        return v;

    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(res, parent, false);

        TextView text1 = view.findViewById(R.id.text1);
        String str = pickingListDatumList.get(position).getItemId();
        String str1 = pickingListDatumList.get(position).getItemName();
        if(position > 0){
            text1.setText(str + " | " + str1);
        }else{
            text1.setText(str);
        }

        return view;
    }
}
