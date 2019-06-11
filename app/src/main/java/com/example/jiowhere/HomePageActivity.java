package com.example.jiowhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {


    ListView mListView;

    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo, R.drawable.socnus};
    String[] activity = {"NUS", "Sentosa", "Underwater World Singapore", "Vivo City", "Soc NUS"};
    String[] location = {"Kent Ridge/Bouna Vista", "Habourfront", "Habourfront", "Habourfront", "Kent Ridge"}; //nearest MRT
    String[] time = {"Permanant", "Permanant", "Permanant", "Permanant", "Permanent"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //setContentView(R.layout.testing);

        mListView = (ListView) findViewById(R.id.listViewTimeLimitedActivities);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);

        //trying to make the listview clickable
        //crashes
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //startActivity(new Intent(view.getContext(), RecommendationDetailsActivity.class));

                        if (position == 1) {
                            //code specific to first list item
                            Intent myIntent = new Intent(view.getContext(), RecommendationDetailsActivity.class);
                            startActivity(myIntent);


                        }

                    }
                });
    }




    //adaptor for the listview
    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.custom_listview_layout, null);

            ImageView mImageView = (ImageView) view.findViewById(R.id.imageView2);
            TextView aTextView = (TextView) view.findViewById(R.id.activityTextView);
            TextView lTextView = (TextView) view.findViewById(R.id.locationTextView);
            TextView tTextView = (TextView) view.findViewById(R.id.timePreiodTextView);

            mImageView.setImageResource(images[position]);
            aTextView.setText(activity[position]);
            lTextView.setText(location[position]);
            tTextView.setText(time[position]);

            return view;
        }
    }

}
