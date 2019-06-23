package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.sql.Time;
import org.w3c.dom.Text;

public class RecommendingActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private EditText nameOfActivityEditText;
    private EditText nearestMRTEditText;
    private EditText addressEditText;
    private EditText timePeriodEditText;
    private Switch permanentSwitch;
    private CheckBox familyCheckBox;
    private CheckBox friendsCheckBox;
    private CheckBox loverCheckBox;
    private CheckBox soloCheckBox;
    private CheckBox indoorCheckBox;
    private CheckBox outdoorCheckBox;
    private CheckBox romanceCheckBox;
    private CheckBox culinaryCheckBox;
    private EditText reviewsEditText;

    //not done yet
    private Button uploadPicButton;

    private Button recommendActivityButton;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommending);

        //Initializing firebase authentication object
        //firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("recommendations");

        //initializing view
        nameOfActivityEditText = (EditText) findViewById(R.id.nameOfActivityEditText);
        nearestMRTEditText = (EditText) findViewById(R.id.nearestMRTEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        timePeriodEditText = (EditText) findViewById(R.id.timePeriodEditText);
        permanentSwitch = (Switch) findViewById(R.id.permanentSwitch);
        familyCheckBox = (CheckBox) findViewById(R.id.familyCheckBox);
        friendsCheckBox = (CheckBox) findViewById(R.id.friendsCheckBox);
        loverCheckBox = (CheckBox) findViewById(R.id.loverCheckBox);
        soloCheckBox = (CheckBox) findViewById(R.id.soloCheckBox);
        indoorCheckBox = (CheckBox) findViewById(R.id.indoorCheckBox);
        outdoorCheckBox = (CheckBox) findViewById(R.id.outdoorCheckBox);
        romanceCheckBox = (CheckBox) findViewById(R.id.romanceCheckBox);
        culinaryCheckBox = (CheckBox) findViewById(R.id.culinaryCheckBox);
        reviewsEditText = (EditText) findViewById(R.id.reviewsEditText);


        recommendActivityButton = (Button) findViewById(R.id.recommendActivityButton);

        //adding listener to button
        recommendActivityButton.setOnClickListener(this);
    }

    private void saveRecommendation() {
        String nameOfActivity = nameOfActivityEditText.getText().toString().trim();
        String nearestMRT = nearestMRTEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String timePeriod = timePeriodEditText.getText().toString().trim();
        boolean isPermanent = true; //dummy value
        String reviews = reviewsEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(nameOfActivity)) {
            String id = databaseReference.push().getKey();
            RecommendationDetails recommendationDetails =
                new RecommendationDetails(id, nameOfActivity, nearestMRT, address, timePeriod, isPermanent, reviews);

            //getUid() is a built-in function in Firebase
            databaseReference.child(recommendationDetails.getNameOfActivity()).setValue(recommendationDetails);
            Toast.makeText(this, "Recommendation Saved...", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        } else {
            Toast.makeText(this, "Please fill up the required fields", Toast.LENGTH_SHORT).show();
        }

        //FirebaseUser user = firebaseAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        if (v == recommendActivityButton) {
            saveRecommendation();
        }
    }
}
