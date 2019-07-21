package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecommendationDetailsActivity<string> extends AppCompatActivity implements View.OnClickListener {
    //This is the full recommendation -> the detailed recommendation

    private ListView mListView;
    private Button leaveReviewButton;
    private Button saveButton;

    //for Firebase database retrieval
    private DatabaseReference reff;

    TextView name;
    TextView location;

    TextView googleMaps;
    TextView timePeriod;
    TextView openingHours;
    TextView tags;

    ImageView image;

    ArrayList<Review> reviewList;
    CustomAdaptor adapter;

    String activityName;

    public static final int LeavingReview = 33;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.reviewsList);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        leaveReviewButton = (Button) findViewById(R.id.leaveReviewButton);
        leaveReviewButton.setOnClickListener(this);

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uId).child("Saved Activities");

        //Passing data inside
        name = (TextView) findViewById(R.id.activityNameTitle);
        location = (TextView) findViewById(R.id.mrtNameTextView);
        timePeriod = (TextView) findViewById(R.id.timePeriodTextView);
        openingHours = (TextView) findViewById(R.id.openingHoursTextView);
        tags = findViewById(R.id.tagTextView);
        image = (ImageView) findViewById(R.id.imageView);
        googleMaps = findViewById(R.id.googleMaps);


        // Receiving value into activity using intent.
        activityName = getIntent().getStringExtra("name");
        String activityNearestMRT = getIntent().getStringExtra("location");
        String activityTimePeriod = getIntent().getStringExtra("timePeriod");
        //String activityOpeningHours = getIntent().getStringExtra("openingHours");
        String allTags = getIntent().getStringExtra("tags");
        String picture = getIntent().getStringExtra("picture");


        // Setting up received value into EditText.
        name.setText(activityName);
        location.setText(activityNearestMRT);
        timePeriod.setText(activityTimePeriod);
        //openingHours.setText(activityOpeningHours);
        tags.setText(allTags);
        Picasso.get().load(picture).into(image);

        reviewList = new ArrayList<>();

        getAndSetData();

        retrieve();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    public void getAndSetData(){
        reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child(activityName);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String address = dataSnapshot.child("address").getValue().toString();
                String openHours = dataSnapshot.child("openingHours").getValue().toString();

                openingHours.setText(openHours);
                googleMaps.setText(address);

                //String email = dataSnapshot.child("email").getValue().toString();
                //emailTextView.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Review> retrieve() {
        reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child(activityName).child("reviews");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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

        return reviewList;
    }


    private void fetchData(DataSnapshot dataSnapshot)
    {

        Review rev = dataSnapshot.getValue(Review.class);
        //String rev = dataSnapshot.child("nameOfActivity").getValue(String.class);

        reviewList.add(rev);

        adapter = new CustomAdaptor(this, reviewList);
        mListView.setAdapter(adapter);
    }


    public void onClick(View v) {
        if (v == leaveReviewButton) {
            Intent intent = new Intent(this, LeaveReviewActivity.class);
            intent.putExtra("nameOfActivity", name.getText().toString());
            startActivityForResult(intent, LeavingReview);
        }

        if (v == saveButton) {
            saveActivity();
        }
    }


    private void saveActivity() {
        String nameOfActivity = name.getText().toString();
        reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child(nameOfActivity);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.child("id").getValue().toString();
                String nameOfActivity = dataSnapshot.child("nameOfActivity").getValue().toString();
                String nearestMRT = dataSnapshot.child("nearestMRT").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String timePer = dataSnapshot.child("timePeriod").getValue().toString();
                String openHours = dataSnapshot.child("openingHours").getValue().toString();
                String allTags = dataSnapshot.child("tags").getValue().toString();
                String imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                RecommendationDetails recommendationDetails =
                        new RecommendationDetails(id, nameOfActivity, nearestMRT, address,
                                timePer, openHours, allTags, imageUrl);
              
                databaseReference.child(nameOfActivity).setValue(recommendationDetails);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("reviews").getChildren()) {
                    Review rev = dataSnapshot1.getValue(Review.class);
                    String username = dataSnapshot1.child("username").getValue().toString();
                    databaseReference.child(nameOfActivity).child("reviews").child(username).setValue(rev);
                }

                Toast.makeText(RecommendationDetailsActivity.this, "Activity Saved...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //for the review
    class CustomAdaptor extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        List<Review> list;
        ArrayList<Review> arrayList;

        public CustomAdaptor(Context mContext, List<Review> list) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
            this.list = list;

            this.arrayList = new ArrayList<>();
            this.arrayList.addAll(list);
        }

        private class ViewHolderr {
            TextView username;
            TextView email;
            TextView review;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Review dataModel = list.get(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolderr viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolderr();
                //LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.review_layout, parent, false);
                viewHolder.username = (TextView) convertView.findViewById(R.id.username);
                viewHolder.email = (TextView) convertView.findViewById(R.id.displayEmail);
                viewHolder.review = convertView.findViewById(R.id.reviewBox);

                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderr) convertView.getTag();
                result=convertView;
            }

            viewHolder.email.setText(dataModel.getUserEmail());
            viewHolder.username.setText(dataModel.getUsername());
            viewHolder.review.setText(dataModel.getReview());

            // Return the completed view to render on screen
            return convertView;
        }
    }

}