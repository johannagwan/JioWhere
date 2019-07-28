package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference reff;
    private DatabaseReference databaseReference;

    private static int CHOOSE = 566;

    TextView displayNameTextView;
    TextView emailTextView;
    ImageView profilePicture;

    ListView savedActivitiesListView;
    ArrayList<RecommendationDetails> savedActivitiesList = new ArrayList<>();
    ArrayList<RecommendationInfo> arrayList = new ArrayList<>();
    SavedActivityAdaptor savedActivityAdaptor;

    String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    ImageView refresh;

    String signInValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayNameTextView = findViewById(R.id.displayNameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        profilePicture = findViewById(R.id.profilePicture);
        profilePicture.setOnClickListener(this);
        refresh = findViewById(R.id.refreshButton);
        refresh.setOnClickListener(this);

        savedActivitiesListView = findViewById(R.id.savedActivitiesListView);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        reff = FirebaseDatabase.getInstance().getReference().child("users").child(uID);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                emailTextView.setText(email);

                String username = dataSnapshot.child("username").getValue().toString();
                displayNameTextView.setText(username);

                if ((dataSnapshot.child("profile").getValue()) != null) {
                    String profileUrl = dataSnapshot.child("profile").getValue().toString();
                    Picasso.get().load(profileUrl).into(profilePicture);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        retrieveSavedActivitiesData();
    }



    public ArrayList<RecommendationDetails> retrieveSavedActivitiesData() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uID).child("Saved Activities");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchSavedActivitiesDetails(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchSavedActivitiesDetails(dataSnapshot);
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

        return savedActivitiesList;
    }


    private void fetchSavedActivitiesDetails(DataSnapshot dataSnapshot) {
        RecommendationDetails rev = dataSnapshot.getValue(RecommendationDetails.class);

        String nearestMRT = rev.getNearestMRT();
        String timePeriod = rev.getTimePeriod();
        String nameOfActivity = rev.getNameOfActivity();
        String tags = rev.getTags();

        String imageUrl = rev.getImageUrl();

        RecommendationInfo ri = new RecommendationInfo(nearestMRT, timePeriod, nameOfActivity, tags, imageUrl);
        arrayList.add(ri);


        savedActivityAdaptor = new SavedActivityAdaptor(this, arrayList);
        savedActivitiesListView.setAdapter(savedActivityAdaptor);
    }

    public void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, CHOOSE); //opening gallery
    }


    public void saveProfile() {

        final StorageReference filePath = storageReference.child("images/"+ UUID.randomUUID().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Uploading profile picture...");
        progressDialog.show();

        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        progressDialog.dismiss();
                        Toast.makeText(UserProfileActivity.this, "Profile Picture set successfully", Toast.LENGTH_SHORT).show();

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        databaseReference.child(uID).child("profile").setValue(downloadUrl);

                    }
                });
            }
        });


        //finish();
        //startActivity(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePicture.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //saveProfile();
            confirmProfile();
        }
    }

    public void confirmProfile() {
        new android.support.v7.app.AlertDialog.Builder(UserProfileActivity.this)
                .setTitle("Change profile picture")
                .setMessage("Set current profile picture as profile?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveProfile();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                        openGallery();
                    }
                })
                .show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == profilePicture) {
            new android.support.v7.app.AlertDialog.Builder(UserProfileActivity.this)
                    .setTitle("Change profile picture")
                    .setMessage("Would you like to change your profile picture?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            openGallery();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // user doesn't want to logout
                        }
                    })
                    .show();


        }

        if ( v== refresh) {
            finish();
            startActivity(getIntent());
        }
    }
}
