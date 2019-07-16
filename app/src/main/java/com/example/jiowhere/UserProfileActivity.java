package com.example.jiowhere;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private DatabaseReference reff;
    private DatabaseReference databaseReference;

    TextView displayNameTextView;
    TextView emailTextView;

    ListView savedActivities;

    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        displayNameTextView = findViewById(R.id.displayNameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("users").child(uID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                emailTextView.setText(email);

                String username = dataSnapshot.child("username").getValue().toString();
                displayNameTextView.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        





        /*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("recommendations");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //null
                    String name = dataSnapshot.child("nameOfActivity").getValue().toString();
                    list.add(name);
                }

                int siz = list.size();
                testtest.setText(siz);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        */


    }

}
