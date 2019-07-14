package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    ImageView image;

    ArrayList<String> reviewList;
    CustomAdaptor adapter;


    int[] images = {R.drawable.sentosa, R.drawable.two, R.drawable.three, R.drawable.four};
    //String[] reviews = {"I loved this place!", "Went with my friends, really enjoyed it.", "Was worth the price", "Fun and memorable"};
    String[] reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.reviewsList);

        leaveReviewButton = (Button) findViewById(R.id.leaveReviewButton);
        leaveReviewButton.setOnClickListener(this);

        /*CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);*/

        //Passing data inside
        name = (TextView) findViewById(R.id.activityNameTitle);
        location = (TextView) findViewById(R.id.mrtName);
        time = (TextView) findViewById(R.id.timePeriod);
        image = (ImageView) findViewById(R.id.imageView);

        // Receiving value into activity using intent.
        String activityName = getIntent().getStringExtra("name");
        String activityLocation = getIntent().getStringExtra("location");
        String activityTime = getIntent().getStringExtra("time");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int picture = bundle.getInt("picture");
            image.setImageResource(picture);
        }

        // Setting up received value into EditText.
        name.setText(activityName);
        location.setText(activityLocation);
        time.setText(activityTime);

        reviewList = new ArrayList<>();

        /*reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child("A").child("reviews");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String reviews = dataSnapshot.child("rev 1").getValue().toString();
                reviewList.add(reviews);
                //aTextView.setText(reviews);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/

        //reviews = reviewList.toArray(new String[reviewList.size()]);

        retrieve();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    public ArrayList<String> retrieve() {
        reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child("A").child("reviews");
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
        String rev = dataSnapshot.getValue(String.class);
        reviewList.add(rev);

        adapter = new CustomAdaptor(this, reviewList);
        mListView.setAdapter(adapter);
    }


    public void onClick(View v) {
        if (v == leaveReviewButton) {
            startActivity(new Intent(this, LeaveReviewActivity.class));
        }
    }

    //adaptor for the listview
    class CustomAdaptor extends BaseAdapter {
        Context context;
        ArrayList<String> reviewList;

        public CustomAdaptor(Context context, ArrayList<String> reviewList) {
            this.context = context;
            this.reviewList = reviewList;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.review_layout, null);

            final TextView aTextView = (TextView) view.findViewById(R.id.reviewBox);


            /*reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child("A").child("reviews");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //String reviews = dataSnapshot.getValue().toString();
                    //reviewList.add(reviews);
                    //aTextView.setText(reviews);
                    //fetchData(dataSnapshot);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });*/

            reviews = reviewList.toArray(new String[reviewList.size()]);

            aTextView.setText(reviews[position]);
            //aTextView.setText(reviewList.get(position));
            return view;
        }
    }

}