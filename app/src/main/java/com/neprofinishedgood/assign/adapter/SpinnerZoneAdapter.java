package com.neprofinishedgood.assign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.model.UniversalSpinner;

import java.util.ArrayList;
import java.util.List;

public class SpinnerZoneAdapter extends ArrayAdapter<UniversalSpinner> {
    List<UniversalSpinner> universalSpinnerList = new ArrayList<>();
    int res;
    private final LayoutInflater mInflater;

    public SpinnerZoneAdapter(Context context, int resource, ArrayList<UniversalSpinner> objects) {
        super(context, resource, objects);
        universalSpinnerList = objects;
        this.res = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public UniversalSpinner getItem(int position) {
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
        TextView textView = v.findViewById(R.id.text);
        TextView textView1 = v.findViewById(R.id.text1);
        String str = universalSpinnerList.get(position).getId();
        String str1 = universalSpinnerList.get(position).getName();
        textView.setText(str);
        textView1.setText(str1);
        return v;

    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(res, parent, false);

        TextView text = view.findViewById(R.id.text);
        TextView text1 = view.findViewById(R.id.text1);

        text.setText(universalSpinnerList.get(position).getId());
        text1.setText(universalSpinnerList.get(position).getName());

        return view;
    }
}
