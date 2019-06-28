package com.example.jiowhere;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.ArrayList;


public class RecommendationListActivity extends AppCompatActivity implements View.OnClickListener{
    //this is the search list
    ListView mListView;
    SearchView mySearchView;
    Button tagButton;

    ListViewAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();

    //see if I can get data from firebase instead

    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo, R.drawable.socnus};
    String[] activity = {"NUS", "Sentosa", "Underwater World Singapore", "Vivo City", "Soc NUS"};
    String[] location = {"Kent Ridge/Buona Vista", "Harbour Front", "Harbour Front", "Harbour Front", "Kent Ridge"}; //nearest MRT
    String[] time = {"Permanent", "Permanent", "Permanent", "Permanent", "Permanent"};
    String[] tags = {"#Family", "#Lover", "#Solo", "#Outdoor", "#Indoor  #Lover"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendations_list);

        mListView = (ListView) findViewById(R.id.mainListView);

        //try and read data pls



        //adding data into the arrayList
        for (int i = 0; i < activity.length; i++) {
            RecommendationInfo ri = new RecommendationInfo(location[i], time[i], activity[i], tags[i], images[i]);
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


        //Tags System
        tagButton = (Button) findViewById(R.id.tagButton);
        tagButton.setOnClickListener(this);


        TextView tagFilter = findViewById(R.id.filterByTags);
        tagFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // Set text view with string
                TextView filterView = (TextView) findViewById(R.id.filterByTags);
                if (returnString == "No Tag") {
                    filterView.setText("");
                } else {
                    filterView.setText(returnString);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

        if (v == tagButton) {
            Intent intent = new Intent(this, TagSystemActivity.class);
            startActivityForResult(intent, 1);

            onActivityResult(1, RESULT_OK, intent);
        }

    }
}