package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecommendationDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    //This is the full recommendation -> the detailed recommendation

    private ListView mListView;
    private Button leaveReviewButton;

    //for Firebase database retrieval
    private DatabaseReference reff;

    TextView name;
    TextView location;
    TextView time;
    TextView tagTextView;
    TextView googleMaps;
    TextView durationTextView;
    ImageView image;

    ArrayList<Review> reviewList;
    CustomAdaptor adapter;

    String activityName;


    public static final int LeavingReview = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.reviewsList);

        leaveReviewButton = (Button) findViewById(R.id.leaveReviewButton);
        leaveReviewButton.setOnClickListener(this);

        //Passing data inside
        name = (TextView) findViewById(R.id.activityNameTitle);
        location = (TextView) findViewById(R.id.mrtName);
        time = (TextView) findViewById(R.id.timePeriod);
        tagTextView = findViewById(R.id.tagTextView);
        image = (ImageView) findViewById(R.id.imageView);
        googleMaps = findViewById(R.id.googleMaps);
        durationTextView = findViewById(R.id.durationTextView);


        // Receiving value into activity using intent.
        activityName = getIntent().getStringExtra("name");
        String activityLocation = getIntent().getStringExtra("location");
        String activityTime = getIntent().getStringExtra("time");
        String tags = getIntent().getStringExtra("tags");
        String picture = getIntent().getStringExtra("picture");

        /*
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int picture = bundle.getInt("picture");
            image.setImageResource(picture);
        }
        */

        // Setting up received value into EditText.
        name.setText(activityName);
        location.setText(activityLocation);
        time.setText(activityTime);
        tagTextView.setText(tags);
        Picasso.get().load(picture).into(image);

        reviewList = new ArrayList<>();

        //retrieveData();
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
                String duration = dataSnapshot.child("openingHours").getValue().toString();

                durationTextView.setText(duration);
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
        //reff = FirebaseDatabase.getInstance().getReference().child("recommendations");
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