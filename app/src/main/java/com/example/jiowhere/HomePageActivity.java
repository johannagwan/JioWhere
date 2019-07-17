package com.example.jiowhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.jiowhere.ListViewAdaptor.MRTLOCATIONS;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    ListView mListView;

    private Button recommendButton;

    TextView myTextView;
    ImageView myImageView;
    Button button;
    Button testingButton;

    ListViewAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList;
    ArrayList<RecommendationDetails> recommendationDetailsArrayList;

    private List<RecommendationDetails> rd = new ArrayList<>();
    private DatabaseReference reff;

    //me tryin out
    ArrayList<String> activityList;
    RecDetailsAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mListView = (ListView) findViewById(R.id.listViewTimeLimitedActivities);
        recommendButton = (Button) findViewById(R.id.recommendButton);

        arrayList = new ArrayList<>();
        recommendationDetailsArrayList = new ArrayList<>();
        //reviewList = new ArrayList<>();


        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);


        adaptor = new ListViewAdaptor(this, arrayList);
        mListView.setAdapter(adaptor);



        //the search bar
        myTextView = (TextView) findViewById(R.id.searchTextView);
        myImageView = (ImageView) findViewById(R.id.searchImage);
        //testingButton = findViewById(R.id.testingButton);

        myTextView.setOnClickListener(this);
        myImageView.setOnClickListener(this);
        recommendButton.setOnClickListener(this);

        //retrieve(); //activityList contains the list of activity
        retrieveRecDetailsData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutButton:
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.userProfile:
                startActivity(new Intent(this, UserProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        mListView.setAdapter(adaptor);
    }


    public void onClick(View v) {
        if (v == recommendButton) {
            startActivity(new Intent(this, RecommendingActivity.class));
        }

        if (v == myImageView || v == myTextView) {
            if (MRTLOCATIONS.length != 0) {
                Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please wait for activities to load", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
