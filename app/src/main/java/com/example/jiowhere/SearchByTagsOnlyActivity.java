package com.example.jiowhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchByTagsOnlyActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox familyCheckBox;
    private CheckBox friendsCheckBox;
    private CheckBox loverCheckBox;
    private CheckBox soloCheckBox;
    private CheckBox indoorCheckBox;
    private CheckBox outdoorCheckBox;
    private CheckBox romanceCheckBox;
    private CheckBox culinaryCheckBox;
    private CheckBox natureCheckBox;
    private CheckBox gamesCheckBox;
    private CheckBox culturalCheckBox;
    private CheckBox museumCheckBox;
    private CheckBox parksCheckBox;
    private CheckBox shoppingCheckBox;
    private CheckBox theaterCheckBox;
    private CheckBox sportsCheckBox;
    private CheckBox concertCheckBox;
    private CheckBox historicCheckBox;
    private CheckBox sightseeingCheckBox;
    private CheckBox bikingCheckBox;
    private CheckBox zooCheckBox;
    private CheckBox architecturalCheckBox;
    private CheckBox monumentCheckBox;
    private CheckBox religiousCheckBox;
    private CheckBox themeparkCheckBox;
    private CheckBox beachCheckBox;

    private TextView random;
    private TextView noActivityText;
    private Button searchButton;

    String selectedTags;
    private ListViewAdaptor adaptor;
    private ListView listView;

    private DatabaseReference reff;
    ArrayList<RecommendationDetails> recommendationDetailsArrayList;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_tags_only);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedTags = "";
        random = findViewById(R.id.randomTextV);

        searchButton = findViewById(R.id.searchByTagButton);
        searchButton.setOnClickListener(this);
        listView = findViewById(R.id.tagActivityListView);
        noActivityText = findViewById(R.id.noActivityText);

        familyCheckBox = (CheckBox) findViewById(R.id.checkBoxo);
        friendsCheckBox = (CheckBox) findViewById(R.id.checkBox2o);
        loverCheckBox = (CheckBox) findViewById(R.id.checkBox3o);
        soloCheckBox = (CheckBox) findViewById(R.id.checkBox4o);
        indoorCheckBox = (CheckBox) findViewById(R.id.checkBox5o);
        outdoorCheckBox = (CheckBox) findViewById(R.id.checkBox6o);
        romanceCheckBox = (CheckBox) findViewById(R.id.romanceCheckBox1o);
        culinaryCheckBox = (CheckBox) findViewById(R.id.culinaryCheckBox1o);
        natureCheckBox = findViewById(R.id.natureCheckBox1o);
        gamesCheckBox = findViewById(R.id.gamesCheckBox1o);
        culturalCheckBox = findViewById(R.id.culturalCheckBox1o);
        museumCheckBox = findViewById(R.id.museumCheckBox1o);
        parksCheckBox = findViewById(R.id.parkCheckBox1o);
        shoppingCheckBox = findViewById(R.id.shoppingCheckBox1o);
        theaterCheckBox = findViewById(R.id.theaterCheckBox1o);
        sportsCheckBox = findViewById(R.id.sportsCheckBox1o);
        concertCheckBox = findViewById(R.id.concertCheckBox1o);
        historicCheckBox = findViewById(R.id.historicCheckBox1o);
        sightseeingCheckBox = findViewById(R.id.sightseeingCheckBox1o);
        bikingCheckBox = findViewById(R.id.bikingCheckBox1o);
        zooCheckBox = findViewById(R.id.zooCheckBox1o);
        architecturalCheckBox = findViewById(R.id.architecturalCheckBox1o);
        monumentCheckBox = findViewById(R.id.monumentCheckBox1o);
        religiousCheckBox = findViewById(R.id.religiousCheckBox1o);
        themeparkCheckBox = findViewById(R.id.themeparkCheckBox1o);
        beachCheckBox = findViewById(R.id.beachCheckBox1o);

        retrieveRecDetailsData();

        random.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                System.out.println("Text ["+s+"]");

                adaptor.tagFilter(s.toString());

                if (adaptor.noActivity()) {
                    noActivityText.setText("NO SEARCH RESULT");
                } else {
                    noActivityText.setText("");
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

    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    private void checkBoxChecking() {
        List<CheckBox> items = new ArrayList<>();

        items.add(familyCheckBox);
        items.add(friendsCheckBox);
        items.add(loverCheckBox);
        items.add(soloCheckBox);
        items.add(indoorCheckBox);
        items.add(outdoorCheckBox);
        items.add(romanceCheckBox);
        items.add(culinaryCheckBox);
        items.add(natureCheckBox);
        items.add(gamesCheckBox);
        items.add(culturalCheckBox);
        items.add(museumCheckBox);
        items.add(parksCheckBox);
        items.add(shoppingCheckBox);
        items.add(theaterCheckBox);
        items.add(sportsCheckBox);
        items.add(concertCheckBox);
        items.add(historicCheckBox);
        items.add(sightseeingCheckBox);
        items.add(bikingCheckBox);
        items.add(zooCheckBox);
        items.add(architecturalCheckBox);
        items.add(monumentCheckBox);
        items.add(religiousCheckBox);
        items.add(themeparkCheckBox);
        items.add(beachCheckBox);

        String text = "";
        selectedTags = "";

        for (CheckBox item : items){
            if(item.isChecked()) {
                text = item.getText().toString();
                selectedTags = selectedTags + "/" + text;
            }
        }

        if (selectedTags.length() > 0) {
            selectedTags = selectedTags.substring(1);
        }

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
        listView.setAdapter(adaptor);
    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {

            checkBoxChecking();

            if (selectedTags.length() > 0) {
                //searchByTag(v);
                random.setText(selectedTags);
            } else {
                Toast.makeText(this, "Please select at least 1 tag", Toast.LENGTH_LONG).show();
            }

            //adaptor.tagFilter("Indoor");

        }
    }
}
