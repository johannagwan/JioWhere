package com.example.jiowhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaveReviewActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText submitReviewEditText;
    private Button submitReviewButton;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        firebaseAuth = FirebaseAuth.getInstance();

        submitReviewEditText = (EditText) findViewById(R.id.submitReviewEditText);
        submitReviewButton = (Button) findViewById(R.id.submitReviewButton);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("recommendations").child("NUS");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    private void submitReview() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String review = submitReviewEditText.getText().toString().trim();

        databaseReference.child("reviews").setValue(review);
        Toast.makeText(this, "Review submitted...", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(getApplicationContext(), RecommendationDetailsActivity.class));
    }

    public void onClick(View v) {
        if (v == submitReviewButton) {
            submitReview();
        }
    }
}
