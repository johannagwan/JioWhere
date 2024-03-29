package com.example.jiowhere;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
    ImageView questionMark;
    Button button;
    Button searchByTagsHomeButton;
    CardView cardthingy;

    //new UI
    ImageView searchByLocationImageView;
    TextView searchByLocationTextView;
    ImageView searchByTagImageView;
    TextView searchByTagTextView;
    ImageView recommendingImageView;
    TextView recommendingTextView;

    //ListViewAdaptor adaptor;
    LimitedActivityAdaptor adaptor;
    ArrayList<RecommendationInfo> arrayList;
    ArrayList<RecommendationDetails> recommendationDetailsArrayList;

    private List<RecommendationDetails> rd = new ArrayList<>();
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_2);


        mListView = (ListView) findViewById(R.id.listViewTimeLimitedActivities);
        /*recommendButton = (Button) findViewById(R.id.recommendButton);
        cardthingy = findViewById(R.id.cardView);
        cardthingy.setOnClickListener(this);*/

        arrayList = new ArrayList<>();
        recommendationDetailsArrayList = new ArrayList<>();
        //reviewList = new ArrayList<>();


        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);


        //adaptor = new ListViewAdaptor(this, arrayList);
        adaptor = new LimitedActivityAdaptor(this, arrayList);

        mListView.setAdapter(adaptor);

        questionMark = findViewById(R.id.homepageQuestionMark);
        questionMark.setOnClickListener(this);
        /*searchByTagsHomeButton = findViewById(R.id.searchByTagsHomeButton);
        searchByTagsHomeButton.setOnClickListener(this);

        //the search bar
        myTextView = (TextView) findViewById(R.id.searchTextView);
        myImageView = (ImageView) findViewById(R.id.searchImage);
        //testingButton = findViewById(R.id.testingButton);

        myTextView.setOnClickListener(this);
        myImageView.setOnClickListener(this);
        recommendButton.setOnClickListener(this);*/

        //new one
        searchByLocationImageView = (ImageView) findViewById(R.id.searchByLocationImageView);
        searchByLocationImageView.setOnClickListener(this);
        searchByLocationTextView = (TextView) findViewById(R.id.searchByLocationTextView);
        searchByLocationTextView.setOnClickListener(this);

        searchByTagImageView = (ImageView) findViewById(R.id.searchByTagImageView);
        searchByTagImageView.setOnClickListener(this);
        searchByTagTextView = (TextView) findViewById(R.id.searchByTagTextView);
        searchByTagTextView.setOnClickListener(this);

        recommendingImageView = (ImageView) findViewById(R.id.recommendingImageView);
        recommendingImageView.setOnClickListener(this);
        recommendingTextView = (TextView) findViewById(R.id.recommendingTextView);
        recommendingTextView.setOnClickListener(this);

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
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(this, SignInActivity.class));
                alert();
                return true;
            case R.id.userProfile:
                startActivity(new Intent(this, UserProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alert() {

        final String uID = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uID);
        //String yesORno = databaseReference.child(uID).child("keepSignedIn").toString();


        new AlertDialog.Builder(HomePageActivity.this)
                .setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // logout

                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                    }
                })
                .show();
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

        String nearestMRT = rev.getNearestMRT();
        String timePeriod = rev.getTimePeriod();
        String nameOfActivity = rev.getNameOfActivity();
        String tags = rev.getTags();

        String imageUrl = rev.getImageUrl();

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, imageUrl);
        if (!timePeriod.equals("Permanent")) {
            arrayList.add(ri);
        }

        //adaptor = new ListViewAdaptor(this, arrayList);
        adaptor = new LimitedActivityAdaptor(this, arrayList);

        mListView.setAdapter(adaptor);
    }


    public void onClick(View v) {
        /*if (v == recommendButton) {
            startActivity(new Intent(this, RecommendingActivity.class));
        }

        if (v == myImageView || v == myTextView || v == cardthingy) {
                Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
                startActivity(intent);

        }

        if (v == searchByTagsHomeButton) {
            Intent intent = new Intent(this, SearchByTagsOnlyActivity.class);
            startActivity(intent);
        }*/
        if (v == searchByLocationImageView || v == searchByLocationTextView) {
            Intent intent = new Intent(v.getContext(), RecommendationListActivity.class);
            startActivity(intent);
        }

        if (v == searchByTagImageView || v == searchByTagTextView) {
            Intent intent = new Intent(this, SearchByTagsOnlyActivity.class);
            startActivity(intent);
        }

        if (v == recommendingImageView || v == recommendingTextView) {
            startActivity(new Intent(this, RecommendingActivity.class));
        }

        if (v == questionMark) {
            new AlertDialog.Builder(HomePageActivity.this)
                    .setTitle("Time Limited Activity")
                    .setMessage("Activities only available for a certain period of time are " +
                            "shown here at the main page. This allows users to notice them more quickly" +
                            " and thus not miss possible events that they might be interested in.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }

    }


}
