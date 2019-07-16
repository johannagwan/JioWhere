package com.example.jiowhere;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestingActivity extends AppCompatActivity {

    ListView testingListView;
    ListView random;
    TextView haha;

    ArrayList<String> activityList;
    ArrayList<String> activity;
    ArrayList<RecommendationDetails> recommendationDetailsArrayList;
    ArrayList<RecommendationInfo> recommendationInfoArrayList;
    int[] images = {R.drawable.nus, R.drawable.sentosa, R.drawable.underwaterworldsg, R.drawable.vivo}; //, R.drawable.socnus, R.drawable.pokemoncarnival, R.drawable.pinkdot, R.drawable.yummyfood};

    DatabaseReference reff;
    Firebase mRef;

    //RecDetailsAdaptor adaptor;
    ListViewAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        //RecommendationInfo(String location, String time_period, String name, String tags, int image)

        testingListView = findViewById(R.id.testingListView);
        random = findViewById(R.id.randomListView);
        haha = findViewById(R.id.ahahahhaaah);

        activityList = new ArrayList<>();
        recommendationDetailsArrayList = new ArrayList<>();
        recommendationInfoArrayList = new ArrayList<>();
        activity = new ArrayList<>();


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);
        random.setAdapter(adapter);

        //try data
        /*
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://jiowhere-4f215.firebaseio.com/allActivities");
        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                activityList.add(dataSnapshot.getValue(String.class));

                //activityList.add(value);
                //haha.setText(value);
                //activity.add(haha.getText().toString());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */

        /*
        retrieveActivityNames(); //now arraylist is NOT loaded
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);
        random.setAdapter(adapter);
        */

        //recommendationDetailsArrayList.add(new RecommendationDetails("a", "a", "a", "a", "a", "a", "a", "a"));
        //recommendationInfoArrayList.add(new RecommendationInfo("Har", "1", "nama", "#me", R.drawable.underwaterworldsg));
        //retrieveRecDetailsData();

        //loopData();
        retrieveRecDetailsData();

        //adaptor = new ListViewAdaptor(this, recommendationInfoArrayList);
        //testingListView.setAdapter(adaptor);

        //haha.setText(activityList.get(3));
        //check();
    }



    //get ActivityName
    public ArrayList<String> retrieveActivityNames() {
        reff = FirebaseDatabase.getInstance().getReference().child("allActivities");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchDataActivityName(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchDataActivityName(dataSnapshot);
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
        return activityList;
    }

    private void fetchDataActivityName(DataSnapshot dataSnapshot) {
        String rev = dataSnapshot.getValue(String.class);
        activityList.add(rev);

        //adapter = new CustomAdaptor(this, reviewList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);
        //random.setAdapter(adapter);
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
                    fetchRecDetails(dataSnapshot);
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

        haha.setText(nearestMRT);

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, imageUrl);
        recommendationInfoArrayList.add(ri);

        recommendationDetailsArrayList.add(rev);

        adaptor = new ListViewAdaptor(this, recommendationInfoArrayList);
        testingListView.setAdapter(adaptor);
        //adapter = new CustomAdaptor(this, reviewList);
        //mListView.setAdapter(adapter);
        /*
        String id = dataSnapshot.child("id").getValue(String.class);
        String nameOfActivity = dataSnapshot.child("nameOfActivity").getValue(String.class);
        String nearestMRT = dataSnapshot.child("nearestMRT").getValue(String.class);
        String address = dataSnapshot.child("address").getValue(String.class);
        String timePeriod = dataSnapshot.child("timePeriod").getValue(String.class);
        String openingHours = dataSnapshot.child("openingHours").getValue(String.class);
        String tags = dataSnapshot.child("tags").getValue(String.class);
        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);



        RecommendationDetails recDetails = new RecommendationDetails(
                id, nameOfActivity,
                nearestMRT, address,
                timePeriod, openingHours,
                tags, imageUrl);

        recommendationDetailsArrayList.add(recDetails);

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, R.drawable.pinkdot);
        recommendationInfoArrayList.add(ri);

        //adaptor = new RecDetailsAdaptor(this, recommendationDetailsArrayList);
        //adaptor = new ListViewAdaptor(this, recommendationInfoArrayList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);
        //testingListView.setAdapter(adaptor);
        */
    }

    /*
    public ArrayList<RecommendationInfo> retrieveRecDetailsData() {
        for (String activityName : activityList) {
            reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child(activityName);
            reff.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    fetchRecDetails(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    fetchRecDetails(dataSnapshot);
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
        }
        return recommendationInfoArrayList;
    }


    private void fetchRecDetails(DataSnapshot dataSnapshot) {
        String id = dataSnapshot.child("id").getValue(String.class);
        String nameOfActivity = dataSnapshot.child("nameOfActivity").getValue(String.class);
        String nearestMRT = dataSnapshot.child("nearestMRT").getValue(String.class);
        String address = dataSnapshot.child("address").getValue(String.class);
        String timePeriod = dataSnapshot.child("timePeriod").getValue(String.class);
        String openingHours = dataSnapshot.child("openingHours").getValue(String.class);
        String tags = dataSnapshot.child("tags").getValue(String.class);
        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);


        RecommendationDetails recDetails = new RecommendationDetails(
                id, nameOfActivity,
                nearestMRT, address,
                timePeriod, openingHours,
                tags, imageUrl);

        recommendationDetailsArrayList.add(recDetails);

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, R.drawable.pinkdot);
        recommendationInfoArrayList.add(ri);

        //adaptor = new RecDetailsAdaptor(this, recommendationDetailsArrayList);
        //adaptor = new ListViewAdaptor(this, recommendationInfoArrayList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);
        //testingListView.setAdapter(adaptor);
    }
    */






}
