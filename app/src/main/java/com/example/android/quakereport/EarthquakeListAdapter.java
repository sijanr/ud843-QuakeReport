package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder>{

    private final List<Earthquake> mEarthquakeList;
    private LayoutInflater mInflater;

    public EarthquakeListAdapter(Context context, List<Earthquake> earthquakeList){
        mInflater = LayoutInflater.from(context);
        mEarthquakeList = earthquakeList;
    }
    @NonNull
    @Override
    public EarthquakeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.earthquake_list_template,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeListAdapter.ViewHolder holder, int position) {
        holder.mMagnitude.setText(String.valueOf(mEarthquakeList.get(position).getMagnitude()));
        holder.mDate.setText(mEarthquakeList.get(position).getDate());
        holder.mLocation.setText(mEarthquakeList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return mEarthquakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mMagnitude;
        private final TextView mLocation;
        private final TextView mDate;

        //constructor
        public ViewHolder(View itemView){
            super(itemView);
            mMagnitude = itemView.findViewById(R.id.magnitude);
            mLocation = itemView.findViewById(R.id.location);
            mDate = itemView.findViewById(R.id.date);
        }
    }
}
