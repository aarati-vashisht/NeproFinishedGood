package com.neprofinishedgood.putaway.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.putaway.model.Reason;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Reason> {

        LayoutInflater inflater;
        ArrayList<Reason> reason;

        Context context;
        public SpinnerAdapter(Activity context, int resouceId, ArrayList<Reason> list){
            super(context,resouceId, list);
            inflater = context.getLayoutInflater();

            this.reason = list;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Reason reason = getItem(position);

            //View rowview = inflater.inflate(R.layout.spinner_layout,null,true);

            View view =  View.inflate(context, R.layout.spinner_layout, null);

            TextView txtTitle = view.findViewById(R.id.text1);
            txtTitle.setText(reason.getName());

            return view;
        }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.spinner_layout, null);
        final TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText(reason.get(position).getName());


        return view;
    }
}
