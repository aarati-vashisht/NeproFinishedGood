package com.neprofinishedgood.counting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.counting.model.StillageDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContingAdapter extends RecyclerView.Adapter<ContingAdapter.ViewHolder> {

    private final List<StillageDatum> mValues;
    private Context context;

    public ContingAdapter(List<StillageDatum> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stillage_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewitem.setText(mValues.get(position).getItem());
        holder.textViewName.setText(mValues.get(position).getName());
        holder.textViewNumber.setText(mValues.get(position).getNumber());
        holder.textViewQuantity.setText(mValues.get(position).getQuantity());
        holder.textViewStdQuatity.setText(mValues.get(position).getStdQuantity());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewitem)
        TextView textViewitem;
        @BindView(R.id.textViewNumber)
        TextView textViewNumber;
        @BindView(R.id.textViewQuantity)
        TextView textViewQuantity;
        @BindView(R.id.textViewStdQuatity)
        TextView textViewStdQuatity;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            mView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            //return super.toString() + " '" + textViewReference.getText() + "'";
            return "";
        }

        @Override
        public void onClick(View v) {
            if (v == mView) {
                Toast.makeText(context, "Clicked on " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
