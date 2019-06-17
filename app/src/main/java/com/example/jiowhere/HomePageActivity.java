package com.example.jiowhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    ListView mListView;
    SearchView mySearchView;
    TextView myTextView;
    ImageView myImageView;

    //private List<RecommendationInfo> recommendationInfoList;


    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo, R.drawable.socnus};
    String[] activity = {"NUS", "Sentosa", "Underwater World Singapore", "Vivo City", "Soc NUS"};
    String[] location = {"Kent Ridge/Bouna Vista", "Habourfront", "Habourfront", "Habourfront", "Kent Ridge"}; //nearest MRT
    String[] time = {"Permanant", "Permanant", "Permanant", "Permanant", "Permanent"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mListView = (ListView) findViewById(R.id.listViewTimeLimitedActivities);
        myTextView = (TextView) findViewById(R.id.searchTextView);
        myImageView = (ImageView) findViewById(R.id.searchImage);

        myTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
                startActivity(intent);
            }
        });

        myImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
                startActivity(intent);
            }
        });

        final CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);


        //using array adaptor
        //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, R.layout.recommendation_layout, items);
        //end of using arrayadaptor


        //trying to make the listview clickable
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(view.getContext(), RecommendationDetailsActivity.class);
                        loopingForListView(position, myIntent, images.length);
                    }
                });


        //making serachView

    }

    /*
    private void fillRecInfoList() {
        //RecommendationInfo(String location, String time_period, String name, int image)
        recommendationInfoList = new ArrayList<>();
        recommendationInfoList.add(new RecommendationInfo("Kent Ridge", "Permanent", "NUS", R.drawable.nus));
        recommendationInfoList.add(new RecommendationInfo("Kent Ridge", "Permanent", "Nus Soc", R.drawable.socnus));
        recommendationInfoList.add(new RecommendationInfo("Habourfront", "Permanent", "Vivo City", R.drawable.vivo));
        recommendationInfoList.add(new RecommendationInfo("Habourfront", "Permanent", "Sentosa", R.drawable.sentosa));
        recommendationInfoList.add(new RecommendationInfo("Habourfront", "Permanent", "Underwater World", R.drawable.underwaterworldsg));
    }
    */

    protected void loopingForListView(int position, Intent intent, int maxValue) {
        for (int i = 0; i < maxValue; i++) {
            if (position == i) {
                startActivity(intent);
            }
        }
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
