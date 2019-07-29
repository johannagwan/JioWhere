package com.example.jiowhere;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.jiowhere.ListViewAdaptor.MRTLOCATIONS;
import static com.example.jiowhere.ListViewAdaptor.numberOfActivities;


public class RecommendationListActivity extends AppCompatActivity implements View.OnClickListener{
    //this is the search list
    ListView mListView;
    AutoCompleteTextView mySearchView;
    Button tagButton;
    CardView carddie;

    TextView locationSearchTextView;
    TextView testText;
    TextView statementTextView;
    TextView noActivityTextView;
    ImageView myImageView;
    ImageView locationDelete;
    ImageView questionMark;
    Button searchByTagButton;

    ListViewAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();
    ArrayList<RecommendationDetails> recommendationDetailsArrayList;

    ArrayList<String> mrtLocationList;
    TextView filterView;

    String searchBarString;

    private DatabaseReference reff;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations_list);

        mListView = (ListView) findViewById(R.id.mainListView);
        carddie = findViewById(R.id.cardViewList);
        carddie.setOnClickListener(this);

        //try and read data pls

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recommendationDetailsArrayList = new ArrayList<>();
        mrtLocationList = new ArrayList<>();

        locationSearchTextView = findViewById(R.id.locationSearchTextView);
        myImageView = findViewById(R.id.searchImageList);
        locationDelete = findViewById(R.id.locationDelete);
        questionMark = findViewById(R.id.questionMarkList);
        statementTextView = findViewById(R.id.statementTextView);
        noActivityTextView = findViewById(R.id.noActivityTextView);

        questionMark.setOnClickListener(this);
        locationSearchTextView.setOnClickListener(this);
        myImageView.setOnClickListener(this);
        locationDelete.setOnClickListener(this);

        filterView = (TextView) findViewById(R.id.filterByTags);

        locationSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                System.out.println("Text ["+s+"]");

                adaptor.filter(s.toString());

                if (adaptor.noActivity()) {
                    noActivityTextView.setText("NO SEARCH RESULT");
                } else {
                    noActivityTextView.setText("");
                }
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
        //tagButton = (Button) findViewById(R.id.tagButton);
        //tagButton.setOnClickListener(this);

        TextView tagFilter = findViewById(R.id.filterByTags);
        tagFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filterView.setText("");

                System.out.println("Text ["+s+"]");


                adaptor.tagFilter(s.toString());
                if (adaptor.noActivity()) {
                    noActivityTextView.setText("NO SEARCH RESULT");
                } else {
                    noActivityTextView.setText("");
                }

                //adaptor.filtering(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchByTagButton = (Button) findViewById(R.id.searchByTagButton);
        searchByTagButton.setOnClickListener(this);
        searchByTagButton.setVisibility(View.VISIBLE);

        searchBarString = locationSearchTextView.getText().toString();
        if (searchBarString.equals("Search by Location here") || searchBarString.equals("")) {
            searchByTagButton.setVisibility(View.GONE);
            //tagFilter.setText("");
        }

        retrieveRecDetailsData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    public ArrayList<RecommendationDetails> retrieveRecDetailsData() {
        reff = FirebaseDatabase.getInstance().getReference().child("recommendations");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchRecDetails(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //fetchRecDetails(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return recommendationDetailsArrayList;
    }


    private void fetchRecDetails(DataSnapshot dataSnapshot) {
        RecommendationDetails rev = dataSnapshot.getValue(RecommendationDetails.class);
        //String rev = dataSnapshot.child("nameOfActivity").getValue(String.class);

        String nearestMRT = rev.getNearestMRT();
        String timePeriod = rev.getTimePeriod();
        String nameOfActivity = rev.getNameOfActivity();
        String tags = rev.getTags();

        String imageUrl = rev.getImageUrl();

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, imageUrl);
        arrayList.add(ri);

        //recommendationDetailsArrayList.add(rev);

        adaptor = new ListViewAdaptor(this, arrayList);
        mListView.setAdapter(adaptor);
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
                searchByTagButton.setVisibility(View.VISIBLE);
                //"Or don't know where to go? \nDecide what to do first!"

                statementTextView.setText("Select some tags to further filter your search results!");
                filterView.setText("");


            }

        } else if (requestCode == 3) { //from Johanna
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("testing");
                String[] arrOfStr = returnString.split("/");

                //String baba = "" + arrOfStr.length;
                //filterView.setText(arrOfStr[0]);
                filterView.setText(returnString);

            }
        }
    }


    @Override
    public void onClick(View v) {

        if (v == tagButton) {
            Intent intent = new Intent(this, TagSystemActivity.class);
            startActivityForResult(intent, 1);
        }

        if (v == locationSearchTextView || v == myImageView || v == carddie) {
            Intent intent = new Intent(this, SearchByLocationActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 2);
        }

        if (v == searchByTagButton) {
            statementTextView.setText("");

            Intent intent = new Intent(this, SearchByTagActivity.class);
            startActivityForResult(intent, 3);
        }

        if (v == locationDelete) {

            locationSearchTextView.setText("");
            filterView.setText("");
        }

        if (v == questionMark) {
            new AlertDialog.Builder(RecommendationListActivity.this)
                    .setTitle("Recommended Activity")
                    .setMessage("All activities available are listed here. " +
                            "Users will be able to search and filter for activities that they are interested in based on " +
                            "location of activity and various tags.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

        }

    }
}