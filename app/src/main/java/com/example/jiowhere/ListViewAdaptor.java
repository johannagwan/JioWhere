package com.example.jiowhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdaptor extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<RecommendationInfo> recommendationInfoList;
    ArrayList<RecommendationInfo> arrayList;

    public ListViewAdaptor(Context context, List<RecommendationInfo> recommendationInfoList) {
        this.mContext = context;
        this.recommendationInfoList = recommendationInfoList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RecommendationInfo>();
        this.arrayList.addAll(recommendationInfoList);
    }

    public class ViewHolder {
        TextView myActivity, myLocation, myTimePeriod;
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
            holder.myTimePeriod = (TextView) convertView.findViewById(R.id.timePreiodTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set results into TextViews
        holder.myImage.setImageResource(recommendationInfoList.get(position).getImage());
        holder.myTimePeriod.setText(recommendationInfoList.get(position).getTime_period());
        holder.myLocation.setText(recommendationInfoList.get(position).getLocation());
        holder.myActivity.setText(recommendationInfoList.get(position).getName());

        //make clickable
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code later
            }
        });

        return convertView;
    }

    //filter
    public void filter (String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        recommendationInfoList.clear();
        if (charText.length() == 0) {
            recommendationInfoList.addAll(arrayList);
        } else {
            for (RecommendationInfo rc : arrayList) {
                if (rc.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    recommendationInfoList.add(rc);
                }
            }
        }
        notifyDataSetChanged();
    }
}
