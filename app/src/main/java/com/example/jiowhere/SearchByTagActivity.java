package com.example.jiowhere;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchByTagActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<RecommendationDetails> activities;
    ArrayList<RecommendationDetails> allActivities;
    ArrayList<RecommendationDetails> familyActivities;
    ArrayList<RecommendationDetails> friendsActivities;
    ArrayList<RecommendationDetails> tempActivities;
    ArrayList<RecommendationInfo> finalActivities;

    ListViewAdaptor adaptor;
    ListView tagListView;

    Button searchByTagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_tag);

        activities = new ArrayList<RecommendationDetails>();
        allActivities = new ArrayList<RecommendationDetails>();
        familyActivities = new ArrayList<RecommendationDetails>();
        friendsActivities = new ArrayList<RecommendationDetails>();
        tempActivities = new ArrayList<RecommendationDetails>();
        finalActivities = new ArrayList<>();

        tagListView = (ListView) findViewById(R.id.tagListView);
        searchByTagButton = (Button) findViewById(R.id.searchByTagButton);
        searchByTagButton.setOnClickListener(this);

        getAllActivities();
    }

    public void getAllActivities() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("recommendations");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String id = dataSnapshot1.child("id").getValue().toString();
                    String nameOfActivity = dataSnapshot1.child("nameOfActivity").getValue().toString();
                    String nearestMRT = dataSnapshot1.child("nearestMRT").getValue().toString();
                    String address = dataSnapshot1.child("address").getValue().toString();
                    String timePer = dataSnapshot1.child("timePeriod").getValue().toString();
                    String openHours = dataSnapshot1.child("openingHours").getValue().toString();
                    String allTags = dataSnapshot1.child("tags").getValue().toString();
                    String imageUrl = dataSnapshot1.child("imageUrl").getValue().toString();
                    String cost = dataSnapshot1.child("cost").getValue().toString();
                    RecommendationDetails act =
                            new RecommendationDetails(id, nameOfActivity, nearestMRT, address,
                                    timePer, openHours, allTags, cost, imageUrl);

                    allActivities.add(act);
                }
//                Toast.makeText(SearchByTagActivity.this, "all activities size " + allActivities.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addTaggedActivity(ArrayList<RecommendationDetails> allActivities, String tagName) {
//        activities.clear();
        for (RecommendationDetails activity : allActivities) {
            String[] tags = activity.getTags().split("\\s");
            for (String indvTag : tags) {
                if (indvTag.equalsIgnoreCase(tagName)) {
                    activities.add(activity);
                }
            }
        }
        tempActivities = (ArrayList<RecommendationDetails>) activities.clone();
    }

    public void findTaggedActivity1(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.familyCheckBox:
                if (isChecked) {
                    addTaggedActivity(allActivities, "#Family");
                } else {
                    activities.clear();
                    tempActivities.clear();
                    finalActivities.clear();
                }
                break;

            case R.id.friendsCheckBox:
                if (isChecked) {
                    addTaggedActivity(allActivities, "#Friends");
                } else {
                    activities.clear();
                    tempActivities.clear();
                    finalActivities.clear();
                }
                break;
        }
    }

    public void searchByTag(View view) {
//        ArrayList<RecommendationInfo> finalActivities = new ArrayList<>();
        for (RecommendationDetails rec : tempActivities) {
            String nearestMRT = rec.getNearestMRT();
            String timePeriod = rec.getTimePeriod();
            String nameOfActivity = rec.getNameOfActivity();
            String tags = rec.getTags();

            String imageUrl = rec.getImageUrl();

            RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, imageUrl);
            finalActivities.add(ri);
        }
        adaptor = new ListViewAdaptor(this, finalActivities);
        tagListView.setAdapter(adaptor);
    }

    @Override
    public void onClick(View v) {
        if (v == searchByTagButton) {
            searchByTag(v);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
