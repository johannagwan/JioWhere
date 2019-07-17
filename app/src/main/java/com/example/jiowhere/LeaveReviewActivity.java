package com.example.jiowhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeaveReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private EditText submitReviewEditText;
    private Button submitReviewButton;
    private TextView name;
    private TextView useremailTextView;
    private TextView usernameTextView;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    String uID;
    String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        firebaseAuth = FirebaseAuth.getInstance();

        name = (TextView) findViewById(R.id.activityNameReview);
        submitReviewEditText = (EditText) findViewById(R.id.submitReviewEditText);

        useremailTextView = findViewById(R.id.useremailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);

        submitReviewButton = (Button) findViewById(R.id.submitReviewButton);
        submitReviewButton.setOnClickListener(this);

        activityName = getIntent().getStringExtra("nameOfActivity");
        name.setText(activityName);

        getUserInfo();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("recommendations"); //.child("A");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    private void getUserInfo() {
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //String email;

        reff = FirebaseDatabase.getInstance().getReference().child("users").child(uID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                useremailTextView.setText(email);

                String username = dataSnapshot.child("username").getValue().toString();
                usernameTextView.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    private void submitReview() {
        String theReview = submitReviewEditText.getText().toString();
        String userEmail = useremailTextView.getText().toString();
        String username = usernameTextView.getText().toString();

        Review reviewClass = new Review(theReview, userEmail, username);

        //databaseReference.child("reviews").child(username).setValue(reviewClass);
        databaseReference.child(activityName).child("reviews").child(username).setValue(reviewClass);
        Toast.makeText(this, "Review submitted...", Toast.LENGTH_LONG).show();

        finish();
        //startActivity(new Intent(getApplicationContext(), RecommendationDetailsActivity.class));
    }

    public void onClick(View v) {
        if (v == submitReviewButton) {
            submitReview();
        }
    }
}

