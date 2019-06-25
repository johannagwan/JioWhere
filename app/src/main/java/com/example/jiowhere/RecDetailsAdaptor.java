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
    List<RecommendationDetails> recommendationInfoList;
    ArrayList<RecommendationDetails> arrayList;

    public RecDetailsAdaptor(Context context, List<RecommendationDetails> recommendationInfoList) {
        this.mContext = context;
        this.recommendationInfoList = recommendationInfoList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RecommendationDetails>();
        this.arrayList.addAll(recommendationInfoList);
    }

    public class ViewHolder {
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
        holder.myTimePeriod.setText(recommendationInfoList.get(position).getTimePeriod());
        holder.myLocation.setText(recommendationInfoList.get(position).getNearestMRT());
        holder.myActivity.setText(recommendationInfoList.get(position).getNameOfActivity());
        //holder.myTags.setText(recommendationInfoList.get(position).get);

        //make clickable
        final int currentPosition = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String TempListViewClickedValue = listValue[position].toString();
                Intent myIntent = new Intent(mContext, RecommendationDetailsActivity.class);

                //trying to pass data
                //int image = recommendationInfoList.get(currentPosition).getImage();
                String name = recommendationInfoList.get(currentPosition).getNameOfActivity();
                String time_period = recommendationInfoList.get(currentPosition).getTimePeriod();
                String location = recommendationInfoList.get(currentPosition).getNearestMRT();
                //String tags = recommendationInfoList.get(currentPosition).getTags();

                myIntent.putExtra("name", name);
                myIntent.putExtra("location", location);
                myIntent.putExtra("time", time_period);
                //myIntent.putExtra("tags", tags);
                //myIntent.putExtra("picture", image);

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
        charText = charText.toLowerCase(Locale.getDefault());
        recommendationInfoList.clear();
        if (charText.length() == 0) {
            recommendationInfoList.addAll(arrayList);
        } else {
            for (RecommendationDetails rc : arrayList) {
                if (rc.getNearestMRT().toLowerCase(Locale.getDefault()).contains(charText)) {
                    recommendationInfoList.add(rc);
                }
            }
        }
        notifyDataSetChanged();
    }

    //tags
    /*
    public void tagFilter (String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        recommendationInfoList.clear();
        if (charText.length() == 0) {
            recommendationInfoList.addAll(arrayList);
        } else {
            for (RecommendationInfo rc : arrayList) {
                if (rc.getTags().toLowerCase(Locale.getDefault()).contains(charText)) {
                    recommendationInfoList.add(rc);
                }
            }
        }
        notifyDataSetChanged();
    }
    */
}

