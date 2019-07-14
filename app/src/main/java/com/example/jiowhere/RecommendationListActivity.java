package com.example.jiowhere;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.ArrayList;


public class RecommendationListActivity extends AppCompatActivity implements View.OnClickListener{
    //this is the search list
    ListView mListView;
    AutoCompleteTextView mySearchView;
    Button tagButton;

    TextView locationSearchTextView;
    ImageView myImageView;

    ListViewAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();
    TextView filterView;


    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo, R.drawable.socnus, R.drawable.pokemoncarnival, R.drawable.pinkdot, R.drawable.yummyfood};
    String[] activity = {"NUS", "Sentosa", "Underwater World Singapore", "Vivo City", "Soc NUS", "Pokemon Carnival 2019", "Pink Dot Concert 2019", "Yummy Food Expo 2019"};
    public static String[] LOCATION = {"Kent Ridge/Buona Vista", "Harbourfront", "Harbourfront", "Harbourfront", "Kent Ridge", "Harbourfront", "Clarke Quay", "Expo"}; //nearest MRT
    String[] time = {"Permanent", "Permanent", "Permanent", "Permanent", "Permanent", "15 June 2019 - 30 June 2019", "29 June, 5pm onwards", "27 to 30 June, 11am to 10pm"};
    String[] tags = {"#Solo #Indoor", "#Outdoor #Friends", "#Romance #Family #Indoor", "#Indoor", "#Indoor", "#Family #Lover #Friends #Romance #Outdoor", "#Lover #Solo #romance #Outdoor #Friends", "#Solo", "#Outdoor", "#Culinary"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations_list);

        mListView = (ListView) findViewById(R.id.mainListView);

        //try and read data pls

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //adding data into the arrayList
        for (int i = 0; i < activity.length; i++) {
            RecommendationInfo ri = new RecommendationInfo(LOCATION[i], time[i], activity[i], tags[i], images[i]);
            arrayList.add(ri);
        }

        adaptor = new ListViewAdaptor(this, arrayList);
        mListView.setAdapter(adaptor);

        locationSearchTextView = findViewById(R.id.locationSearchTextView);
        myImageView = findViewById(R.id.searchImageList);

        locationSearchTextView.setOnClickListener(this);
        myImageView.setOnClickListener(this);

        filterView = (TextView) findViewById(R.id.filterByTags);

        locationSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                System.out.println("Text ["+s+"]");

                adaptor.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //Tags System
        tagButton = (Button) findViewById(R.id.tagButton);
        tagButton.setOnClickListener(this);

        TextView tagFilter = findViewById(R.id.filterByTags);
        tagFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filterView.setText("");

                System.out.println("Text ["+s+"]");


                adaptor.tagFilter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*
        SearchView tag = (SearchView) findViewById(R.id.tagSearchView);

        tag.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adaptor.tagFilter("");
                    mListView.clearTextFilter();
                } else {
                    adaptor.tagFilter(newText);
                }

                return true;
            }
        });
        */



    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // Set text view with string
                //TextView filterView = (TextView) findViewById(R.id.filterByTags);
                if (returnString == "No Tag") {
                    filterView.setText("");
                } else {
                    filterView.setText(returnString);
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                String returnString = data.getStringExtra("locationStuff");

                // Set text view with string
                locationSearchTextView = (TextView) findViewById(R.id.locationSearchTextView);
                locationSearchTextView.setText(returnString);

                filterView.setText("");
            }
        }
    }


    @Override
    public void onClick(View v) {

        if (v == tagButton) {
            Intent intent = new Intent(this, TagSystemActivity.class);
            startActivityForResult(intent, 1);

        }

        if (v == locationSearchTextView || v == myImageView) {
            Intent intent = new Intent(this, SearchByLocationActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 2);


        }

    }
}