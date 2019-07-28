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
import java.util.Locale;


public class ListViewAdaptor extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<RecommendationInfo> recommendationInfoList;
    ArrayList<RecommendationInfo> arrayList;

    String currentLocation;
    String tagedWord;

    public static int numberOfActivities;
    public static String MRTLOCATIONS[];

    //public static String[] LOCATION = {};

    public ListViewAdaptor(Context context, List<RecommendationInfo> recommendationInfoList) {
        this.mContext = context;
        this.recommendationInfoList = recommendationInfoList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RecommendationInfo>();
        this.arrayList.addAll(recommendationInfoList);

        currentLocation = "";
        tagedWord = "";

        numberOfActivities = arrayList.size();
        MRTLOCATIONS = new String[numberOfActivities];

        int counter = 0;
        for(RecommendationInfo ri : recommendationInfoList) {
            String mrt = ri.getLocation();
            MRTLOCATIONS[counter] = mrt;
            counter++;
        }

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

    //filter
    public void filter (String charText) {
            //currentLocation = charText;

            charText = charText.toLowerCase(Locale.getDefault());
            recommendationInfoList.clear();

            if (charText.length() == 0) {
                recommendationInfoList.addAll(arrayList);
            } else {
                for (RecommendationInfo rc : arrayList) {
                    currentLocation = charText;
                    if (rc.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                        recommendationInfoList.add(rc);
                    }
                }
            }
        notifyDataSetChanged();
    }

    /*
    public void filtering(String charText) {
        String[] arrOfStr = charText.split("/");
        for (int i = 0; i < arrOfStr.length; i++) {
            tagFilter(arrOfStr[i]);
        }
    }

    public  void tagFilter(String charText) {
        tagedWord = charText; //useless
        String[] arrOfStr = charText.split("/");

        if (charText.length() != 0) {//ignore if no tag
            charText = charText.toLowerCase(Locale.getDefault());

            List<RecommendationInfo> temp = new ArrayList<>();
            for(RecommendationInfo ri : recommendationInfoList) {
                temp.add(ri);
            }

            recommendationInfoList.clear();

            if (currentLocation != "") { //if there is smth in location Search

                for (RecommendationInfo ri : arrayList) {//arraylist with everything
                    if (ri.getLocation().toLowerCase(Locale.getDefault()).contains(currentLocation)) {

                        //for (int i = 0; i < arrOfStr.length; i++) {
                            if (ri.getTags().toLowerCase(Locale.getDefault()).contains("Indoor")) {
                                //if (ri.getTags().toLowerCase(Locale.getDefault()).contains(arrOfStr[1])) {
                                    recommendationInfoList.add(ri);
                                    //break;
                                //}
                            }
                        //}

                    }
                }
            } else { //if there is nothing in location
                for (RecommendationInfo ri : arrayList) {
                    if (ri.getTags().toLowerCase(Locale.getDefault()).contains("Indoor")) {
                        //if (ri.getTags().toLowerCase(Locale.getDefault()).contains(arrOfStr[1])) {
                            recommendationInfoList.add(ri);
                            //break;
                        //}
                    }
                }
            }
        }

        notifyDataSetChanged();
    }
    */

    public  void tagFilter(String charText) {

        if (charText.length() != 0) {//ignore if no tag
            charText = charText.toLowerCase(Locale.getDefault());
            String[] arrOfStr = charText.split("/"); //ok now it works

            List<RecommendationInfo> temp = new ArrayList<>();
            for(RecommendationInfo ri : recommendationInfoList) {
                temp.add(ri);
            }

            recommendationInfoList.clear();

            if (currentLocation != "") { //if there is smth in location Search
                for (RecommendationInfo ri : arrayList) { //for everything =>
                    if (ri.getLocation().toLowerCase(Locale.getDefault()).contains(currentLocation)) { //for only this location
                        if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                            recommendationInfoList.add(ri);
                        }
                    }
                }
            } else {
                for (RecommendationInfo ri : arrayList) {
                    /*
                    if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                        recommendationInfoList.add(ri);
                    }
                    */
                    boolean haveAllTags = true;
                    for (int i = 0; i < arrOfStr.length; i++) {
                        if (haveAllTags == true) { //only when its true do I continue, else i just stop cause one tag is missing
                            if (ri.getTags().toLowerCase(Locale.getDefault()).contains(arrOfStr[i])) {
                                haveAllTags = true;
                            } else {
                                haveAllTags = false;
                            }
                        }
                    }

                    if (haveAllTags == true) {
                        recommendationInfoList.add(ri);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

    /*
    public  void tagFilter(String charText) {
        tagedWord = charText;

        //if (charText != null) {


            if (charText.length() != 0) {//ignore if no tag
                charText = charText.toLowerCase(Locale.getDefault());

                List<RecommendationInfo> temp = new ArrayList<>();
                for(RecommendationInfo ri : recommendationInfoList) {
                    temp.add(ri);
                }


                recommendationInfoList.clear();

                if (currentLocation != "") { //if there is smth in location Search

                    for (RecommendationInfo ri : arrayList) {
                        if (ri.getLocation().toLowerCase(Locale.getDefault()).contains(currentLocation)) {
                            if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                                recommendationInfoList.add(ri);
                            }
                        }
                    }
                } else {
                    for (RecommendationInfo ri : arrayList) {
                        if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                            recommendationInfoList.add(ri);
                        }
                    }
                }
            }

            notifyDataSetChanged();
        }
        */

}

