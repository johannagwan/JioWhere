package com.example.jiowhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    ListView mListView;

    private Button recommendButton;

    TextView myTextView;
    ImageView myImageView;

    ListViewAdaptor adaptor;
    //RecDetailsAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();



    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<RecommendationDetails> rd = new ArrayList<>();
    //private List<RecommendationInfo> recommendationInfoList;




    int[] images = {R.drawable.pokemoncarnival, R.drawable.pinkdot, R.drawable.yummyfood};
    String[] activity = {"Pokemon Carnival 2019", "Pink Dot Concert 2019", "Yummy Food Expo 2019"};
    String[] location = {"Harbourfront", "Clarke Quay", "Expo"}; //nearest MRT
    String[] time = {"15 June 2019 - 30 June 2019", "29 June, 5pm onwards", "27 to 30 June, 11am to 10pm"};

    //"Family", "Friends", "Lover", "Solo", "Indoor", "Outdoor", "Culinary", "Romance"
    String[] tags = {"#Family #Lover #Friends #Romance #Outdoor", "#Lover #Solo #romance #Outdoor #Friends", "#Solo", "#Outdoor", "#Culinary"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mListView = (ListView) findViewById(R.id.listViewTimeLimitedActivities);
        recommendButton = (Button) findViewById(R.id.recommendButton);

        //database thingy
        /*
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("recommendations");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rd.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    RecommendationDetails recommendationDetails = keyNode.getValue(RecommendationDetails.class);
                    rd.add(recommendationDetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */


        //adaptor = new RecDetailsAdaptor(this, rd);
        //mListView.setAdapter(adaptor);
        //end of database thingy


        /*
        for (int i = 0; i < activity.length; i++) {
            RecommendationInfo ri = new RecommendationInfo(location[i], time[i], activity[i], tags[i], images[i]);
            arrayList.add(ri);
        }
        */

        //testing
        //String length = "Help la" + rd.size();
        //TextView testingFire = findViewById(R.id.testingFire);
        //testingFire.setText(length);

        for (int i = 0; i < activity.length; i++) {
            RecommendationInfo ri = new RecommendationInfo(location[i], time[i], activity[i], tags[i], images[i]);
            arrayList.add(ri);
        }

        adaptor = new ListViewAdaptor(this, arrayList);
        mListView.setAdapter(adaptor);



        //the search bar
        myTextView = (TextView) findViewById(R.id.searchTextView);
        myImageView = (ImageView) findViewById(R.id.searchImage);

        myTextView.setOnClickListener(this);
        myImageView.setOnClickListener(this);
        recommendButton.setOnClickListener(this);
    }


  
    public void onClick(View v) {
        if (v == recommendButton) {
            startActivity(new Intent(this, RecommendingActivity.class));
        } else if (v == myImageView) {
            Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
            startActivity(intent);
        } else if (v == myTextView) {
            Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
            startActivity(intent);
        }
    }
}
