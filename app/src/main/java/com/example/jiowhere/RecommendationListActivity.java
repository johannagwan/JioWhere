package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecommendationListActivity extends AppCompatActivity implements View.OnClickListener{
    //this is the search list
    ListView mListView;
    SearchView mySearchView;

    ListViewAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();

    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo, R.drawable.socnus};
    String[] activity = {"NUS", "Sentosa", "Underwater World Singapore", "Vivo City", "Soc NUS"};
    String[] location = {"Kent Ridge/Buona Vista", "Harbour Front", "Harbour Front", "Harbour Front", "Kent Ridge"}; //nearest MRT
    String[] time = {"Permanent", "Permanent", "Permanent", "Permanent", "Permanent"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendations_list);

        mListView = (ListView) findViewById(R.id.mainListView);

        for (int i = 0; i < activity.length; i++) {
            RecommendationInfo ri = new RecommendationInfo(location[i], time[i], activity[i], images[i]);
            arrayList.add(ri);
        }

        adaptor = new ListViewAdaptor(this, arrayList);
        mListView.setAdapter(adaptor);

        //trying to make search view
        SearchView searchView = (SearchView) findViewById(R.id.mainSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adaptor.filter("");
                    mListView.clearTextFilter();
                } else {
                    adaptor.filter(newText);
                }

                return true;
            }
        });

    }

    /*

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
    */

    @Override
    public void onClick(View v) {

    }
}
