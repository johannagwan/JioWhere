package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedActivityAdaptor extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<RecommendationInfo> recommendationInfoList;
    ArrayList<RecommendationInfo> arrayList;

    String currentLocation;
    String tagedWord;


    public SavedActivityAdaptor(Context context, List<RecommendationInfo> recommendationInfoList) {
        this.mContext = context;
        this.recommendationInfoList = recommendationInfoList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RecommendationInfo>();
        this.arrayList.addAll(recommendationInfoList);

        currentLocation = "";
        tagedWord = "";


    }

    private class ViewHolder {
        TextView myActivity, myLocation, myTimePeriod, myTags;
        ImageView myImage;
    }

    @Override
    public int getCount() {
        return recommendationInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return recommendationInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview_layout, null);

            holder.myImage = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.myActivity = (TextView) convertView.findViewById(R.id.activityTextView);
            holder.myLocation = (TextView) convertView.findViewById(R.id.locationTextView);
            holder.myTimePeriod = (TextView) convertView.findViewById(R.id.timePeriodTextView);
            holder.myTags = (TextView) convertView.findViewById(R.id.tagsView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set results into TextViews
        //holder.myImage.setImageResource(recommendationInfoList.get(position).getImage());
        Picasso.get().load(recommendationInfoList.get(position).getImage()).into(holder.myImage);
        holder.myTimePeriod.setText(recommendationInfoList.get(position).getTime_period());
        holder.myLocation.setText(recommendationInfoList.get(position).getLocation());
        holder.myActivity.setText(recommendationInfoList.get(position).getName());
        holder.myTags.setText(recommendationInfoList.get(position).getTags());

        //make clickable
        final int currentPosition = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String TempListViewClickedValue = listValue[position].toString();
                Intent myIntent = new Intent(mContext, RecommendationDetailsActivity.class);

                //trying to pass data
                String image = recommendationInfoList.get(currentPosition).getImage();
                String name = recommendationInfoList.get(currentPosition).getName();
                String time_period = recommendationInfoList.get(currentPosition).getTime_period();
                String location = recommendationInfoList.get(currentPosition).getLocation();
                String tags = recommendationInfoList.get(currentPosition).getTags();

                myIntent.putExtra("name", name);
                myIntent.putExtra("location", location);
                myIntent.putExtra("time", time_period);
                myIntent.putExtra("tags", tags);
                myIntent.putExtra("picture", image);

                mContext.startActivity(myIntent);
            }
        });


        return convertView;
    }
}
