/*
package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
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
    List<RecommendationDetails> recommendationDetailsList;
    ArrayList<RecommendationDetails> arrayList;

    String currentLocation;
    String tagedWord;


    public ListViewAdaptor(Context context, List<RecommendationDetails> recommendationDetailsList) {
        this.mContext = context;
        this.recommendationDetailsList = recommendationDetailsList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RecommendationDetails>();
        this.arrayList.addAll(recommendationDetailsList);

        currentLocation = "";
        tagedWord = "";

    }

    private class ViewHolder {
        TextView myActivity, myLocation, myTimePeriod, myTags;
        ImageView myImage;
    }

    @Override
    public int getCount() {
        return recommendationDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return recommendationDetailsList.get(position);
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
            holder.myTags = (TextView) convertView.findViewById(R.id.tagsView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set results into TextViews
        holder.myImage.setImageResource(recommendationDetailsList.get(position).getImage());
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
                int image = recommendationInfoList.get(currentPosition).getImage();
                String name = recommendationInfoList.get(currentPosition).getName();
                String time_period = recommendationInfoList.get(currentPosition).getTime_period();
                String location = recommendationInfoList.get(currentPosition).getLocation();
                String tags = recommendationInfoList.get(currentPosition).getTags();

                myIntent.putExtra("name", name);
                myIntent.putExtra("location", location);
                myIntent.putExtra("time", time_period);
                myIntent.putExtra("tags", tags);
                myIntent.putExtra("picture", image);

                //passing image

                //end of passing image

                //end of pass data
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


    //don't work combined

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


}
*/


package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecDetailsAdaptor extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<RecommendationInfo> recommendationInfoList;
    ArrayList<RecommendationInfo> arrayList;

    String currentLocation;
    String tagedWord;


    public RecDetailsAdaptor(Context context, List<RecommendationInfo> recommendationInfoList) {
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
            holder.myTimePeriod = (TextView) convertView.findViewById(R.id.timePreiodTextView);
            holder.myTags = (TextView) convertView.findViewById(R.id.tagsView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set results into TextViews
        //holder.myImage.setImageResource(recommendationInfoList.get(position).getImage());
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
                //int image = recommendationInfoList.get(currentPosition).getImage();
                String name = recommendationInfoList.get(currentPosition).getName();
                String time_period = recommendationInfoList.get(currentPosition).getTime_period();
                String location = recommendationInfoList.get(currentPosition).getLocation();
                String tags = recommendationInfoList.get(currentPosition).getTags();

                myIntent.putExtra("name", name);
                myIntent.putExtra("location", location);
                myIntent.putExtra("time", time_period);
                myIntent.putExtra("tags", tags);
                //myIntent.putExtra("picture", image);

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


    //don't work combined

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

}


/*
package com.example.jiowhere;

 import android.content.Context;
 import android.content.Intent;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;
 import android.widget.ImageView;
 import android.widget.TextView;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Locale;

public class RecDetailsAdaptor extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<RecommendationDetails> recommendationDetailsList;
    ArrayList<RecommendationDetails> arrayList;

    String currentLocation;
    String tagedWord;

    public RecDetailsAdaptor(Context context, List<RecommendationDetails> recommendationDetailsList) {
        this.mContext = context;
        this.recommendationDetailsList = recommendationDetailsList;
        inflater = LayoutInflater.from(mContext);
        //Add everything in the List into new arrayList
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(recommendationDetailsList);

        currentLocation = "";
            tagedWord = "";

        }

        private class ViewHolder {
            TextView myActivity, myLocation, myTimePeriod, myTags;
            ImageView myImage;
        }


        @Override
        public int getCount() {
            return recommendationDetailsList.size();
        }

        @Override
        public Object getItem(int position) {
            return recommendationDetailsList.get(position);
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
                holder.myTags = (TextView) convertView.findViewById(R.id.tagsView);

                convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set results into TextViews
        String activityName = recommendationDetailsList.get(position).getNameOfActivity();

        //holder.myImage.setImageResource(recommendationInfoList.get(position).getImage());
        //reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child(activityName).child("imageUrl");

        holder.myTimePeriod.setText(recommendationDetailsList.get(position).getTimePeriod());
        holder.myLocation.setText(recommendationDetailsList.get(position).getNearestMRT());
        holder.myActivity.setText(activityName);
        holder.myTags.setText(recommendationDetailsList.get(position).getTags());

        //make clickable
        final int currentPosition = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String TempListViewClickedValue = listValue[position].toString();
                Intent myIntent = new Intent(mContext, RecommendationDetailsActivity.class);

                //trying to pass data
                //int image = recommendationDetailsList.get(currentPosition).getImage();

                String name = recommendationDetailsList.get(currentPosition).getNameOfActivity();
                String time_period = recommendationDetailsList.get(currentPosition).getTimePeriod();
                String location = recommendationDetailsList.get(currentPosition).getNearestMRT();
                String tags = recommendationDetailsList.get(currentPosition).getTags();

                myIntent.putExtra("name", name);
                myIntent.putExtra("location", location);
                myIntent.putExtra("time", time_period);
                myIntent.putExtra("tags", tags);

                //myIntent.putExtra("picture", image);

                mContext.startActivity(myIntent);
            }
        });


        return convertView;
    }

    //filter
    public void filter (String charText) {
        //currentLocation = charText;

        charText = charText.toLowerCase(Locale.getDefault());
        recommendationDetailsList.clear();

        if (charText.length() == 0) {
            recommendationDetailsList.addAll(arrayList);
        } else {
            for (RecommendationDetails rc : arrayList) {
                currentLocation = charText;
                if (rc.getNearestMRT().toLowerCase(Locale.getDefault()).contains(charText)) {
                    recommendationDetailsList.add(rc);
                }
            }
        }
        notifyDataSetChanged();
    }


    //don't work combined

    public  void tagFilter(String charText) {
        tagedWord = charText;

        //if (charText != null) {


        if (charText.length() != 0) {//ignore if no tag
            charText = charText.toLowerCase(Locale.getDefault());

            List<RecommendationDetails> temp = new ArrayList<>();
            for(RecommendationDetails ri : recommendationDetailsList) {
                temp.add(ri);
            }


            recommendationDetailsList.clear();

            if (currentLocation != "") { //if there is smth in location Search

                for (RecommendationDetails ri : arrayList) {
                    if (ri.getNearestMRT().toLowerCase(Locale.getDefault()).contains(currentLocation)) {
                        if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                            recommendationDetailsList.add(ri);
                        }
                    }
                }




            } else {
                for (RecommendationDetails ri : arrayList) {
                    if (ri.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                        recommendationDetailsList.add(ri);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }
}
*/
