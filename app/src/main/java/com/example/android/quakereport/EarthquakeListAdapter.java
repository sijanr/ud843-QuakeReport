package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder>{

    private final List<Earthquake> mEarthquakeList;
    private LayoutInflater mInflater;
    private Context context;

    public EarthquakeListAdapter(Context context, List<Earthquake> earthquakeList){
        mInflater = LayoutInflater.from(context);
        mEarthquakeList = earthquakeList;
        this.context = context;
    }
    @NonNull
    @Override
    public EarthquakeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.earthquake_list_template,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeListAdapter.ViewHolder holder, int position) {
        holder.mMagnitude.setText(formatMagnitude(mEarthquakeList.get(position).getMagnitude()));
        //set the proper background color on the magnitude
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.mMagnitude.getBackground();
        magnitudeCircle.setColor(getMagnitudeColor(mEarthquakeList.get(position).getMagnitude()));

        String location = mEarthquakeList.get(position).getLocation();
        if(!location.contains("of")){
            holder.mNearLocation.setText("Near the");
            holder.mLocation.setText(location);
        } else{
            int index = location.indexOf("of");
            holder.mNearLocation.setText(location.substring(0,index+2));
            holder.mLocation.setText(location.substring(index+3));
        }

        Date date = new Date(mEarthquakeList.get(position).getTimeInMilliSeconds());
        holder.mDate.setText(convertDate(date));
        holder.mTime.setText(convertTime(date));
        
    }

    @Override
    public int getItemCount() {
        return mEarthquakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mMagnitude;
        private final TextView mLocation;
        private final TextView mDate;
        private final TextView mTime;
        private final TextView mNearLocation;

        //constructor
        public ViewHolder(View itemView){
            super(itemView);
            mMagnitude = itemView.findViewById(R.id.magnitude);
            mLocation = itemView.findViewById(R.id.location);
            mDate = itemView.findViewById(R.id.date);
            mTime = itemView.findViewById(R.id.time);
            mNearLocation = itemView.findViewById(R.id.near_location);
        }
    }

    private String convertDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd,yyyy");
        return dateFormatter.format(date);
    }

    private String convertTime(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        return dateFormatter.format(date);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int colorId;
        switch ((int) Math.floor(magnitude)) {
            case 0:
            case 1:
                colorId = R.color.magnitude1;
                break;
            case 2:
                colorId = R.color.magnitude2;
                break;
            case 3:
                colorId = R.color.magnitude3;
                break;
            case 4:
                colorId = R.color.magnitude4;
                break;
            case 5:
                colorId = R.color.magnitude5;
                break;
            case 6:
                colorId = R.color.magnitude6;
                break;
            case 7:
                colorId = R.color.magnitude7;
                break;
            case 8:
                colorId = R.color.magnitude8;
                break;
            case 9:
                colorId = R.color.magnitude9;
                break;
            default:
                colorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, colorId);
    }



}
