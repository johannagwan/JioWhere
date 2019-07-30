package com.example.jiowhere;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.jiowhere.SearchByLocationActivity.ALLMRTSTATIONS;

public class RecommendingActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private EditText nameOfActivityEditText;
    private EditText nearestMRTEditText;
    private EditText addressEditText;
    private EditText openingHoursEditText;
    private EditText timePeriodEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private Switch permanentSwitch;
    private Switch costSwitch;

    private CheckBox familyCheckBox;
    private CheckBox friendsCheckBox;
    private CheckBox loverCheckBox;
    private CheckBox soloCheckBox;
    private CheckBox indoorCheckBox;
    private CheckBox outdoorCheckBox;

    private CheckBox romanceCheckBox;
    private CheckBox culinaryCheckBox;
    private CheckBox natureCheckBox;
    private CheckBox gamesCheckBox;
    private CheckBox culturalCheckBox;
    private CheckBox museumCheckBox;
    private CheckBox parksCheckBox;
    private CheckBox shoppingCheckBox;
    private CheckBox theaterCheckBox;
    private CheckBox sportsCheckBox;
    private CheckBox concertCheckBox;
    private CheckBox historicCheckBox;
    private CheckBox sightseeingCheckBox;
    private CheckBox bikingCheckBox;
    private CheckBox zooCheckBox;
    private CheckBox architecturalCheckBox;
    private CheckBox monumentCheckBox;
    private CheckBox religiousCheckBox;
    private CheckBox themeparkCheckBox;
    private CheckBox beachCheckBox;

    private ImageView uploadedImage;
    private CheckBox TORCheckBox;

    private String allTags;
    //Saving image
    private Button uploadPicButton;
    private Button selectMRTButton;

    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    public static final int GalleryPick = 99;
    public static final int selectMRT = 22;

    private Button recommendActivityButton;
    private DatabaseReference databaseReference;

    public static List<String> uniqueID = new ArrayList<>();
    boolean[] checkedItems;
    ArrayList<Integer> selectedMRT = new ArrayList<>(); //mUserIrems
    String[] allMrts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommending);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("recommendations");

        //initializing view
        nameOfActivityEditText = (EditText) findViewById(R.id.nameOfActivityEditText);
        nearestMRTEditText = (EditText) findViewById(R.id.nearestMRTEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        timePeriodEditText = (EditText) findViewById(R.id.timePeriodEditText);
        openingHoursEditText = (EditText) findViewById(R.id.openingHoursEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);

        familyCheckBox = (CheckBox) findViewById(R.id.familyCheckBox);
        friendsCheckBox = (CheckBox) findViewById(R.id.friendsCheckBox);
        loverCheckBox = (CheckBox) findViewById(R.id.loverCheckBox);
        soloCheckBox = (CheckBox) findViewById(R.id.soloCheckBox);
        indoorCheckBox = (CheckBox) findViewById(R.id.indoorCheckBox);
        outdoorCheckBox = (CheckBox) findViewById(R.id.outdoorCheckBox);

        romanceCheckBox = (CheckBox) findViewById(R.id.romanceCheckBox);
        culinaryCheckBox = (CheckBox) findViewById(R.id.culinaryCheckBox);
        natureCheckBox = findViewById(R.id.natureCheckBox);
        gamesCheckBox = findViewById(R.id.gamesCheckBox);
        culturalCheckBox = findViewById(R.id.culturalCheckBox);
        museumCheckBox = findViewById(R.id.museumCheckBox);
        parksCheckBox = findViewById(R.id.parkCheckBox);
        shoppingCheckBox = findViewById(R.id.shoppingCheckBox);
        theaterCheckBox = findViewById(R.id.theaterCheckBox);
        sportsCheckBox = findViewById(R.id.sportsCheckBox);
        concertCheckBox = findViewById(R.id.concertCheckBox);
        historicCheckBox = findViewById(R.id.historicCheckBox);
        sightseeingCheckBox = findViewById(R.id.sightseeingCheckBox);
        bikingCheckBox = findViewById(R.id.bikingCheckBox);
        zooCheckBox = findViewById(R.id.zooCheckBox);
        architecturalCheckBox = findViewById(R.id.architecturalCheckBox);
        monumentCheckBox = findViewById(R.id.monumentCheckBox);
        religiousCheckBox = findViewById(R.id.religiousCheckBox);
        themeparkCheckBox = findViewById(R.id.themeparkCheckBox);
        beachCheckBox = findViewById(R.id.beachCheckBox);

        /*
        private CheckBox architecturalCheckBox;
        private CheckBox monumentCheckBox;
        private CheckBox religiousCheckBox;
        private CheckBox themeparkCheckBox;
        private CheckBox beachCheckBox;
        */

        uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
        uploadedImage.setImageResource(R.drawable.empty);

        TORCheckBox = (CheckBox) findViewById(R.id.TORCheckBox);

        //Buttons
        recommendActivityButton = (Button) findViewById(R.id.recommendActivityButton);
        uploadPicButton = (Button) findViewById(R.id.uploadPicButton);

        allTags = "";

        //switch
        permanentSwitch = (Switch) findViewById(R.id.permanentSwitch);
        permanentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) { //if true => if switch is on => is permanent
                    timePeriodEditText.setText("Permanent");
                } else {
                    //if ((timePeriodEditText.getText()).equals("Permanent")) {
                    String currentText = timePeriodEditText.getText().toString();
                    if (currentText.equals("Permanent")) {
                        timePeriodEditText.setText("");
                    }
                    //}
                }
            }
        });

        costSwitch = findViewById(R.id.freeSwitch);
        costSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) { //if true => if switch is on => is permanent
                    priceEditText.setText("Free");
                } else {
                    //if ((timePeriodEditText.getText()).equals("Permanent")) {
                    String currentText = priceEditText.getText().toString();
                    if (currentText.equals("Free")) {
                        priceEditText.setText("");
                    }
                    //}
                }
            }
        });

        //adding listener to button
        recommendActivityButton.setOnClickListener(this);
        uploadPicButton.setOnClickListener(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //selectMRT
        checkedItems = new boolean[ALLMRTSTATIONS.length];

        selectMRTButton = (Button) findViewById(R.id.selectMRTButton);
        thatDialogThingy();

    }

    private void checkBoxChecking() {
        List<CheckBox> items = new ArrayList<>();

        items.add(familyCheckBox);
        items.add(friendsCheckBox);
        items.add(loverCheckBox);
        items.add(soloCheckBox);
        items.add(indoorCheckBox);
        items.add(outdoorCheckBox);
        items.add(romanceCheckBox);
        items.add(culinaryCheckBox);

        items.add(natureCheckBox);
        items.add(gamesCheckBox);
        items.add(culturalCheckBox);
        items.add(museumCheckBox);
        items.add(parksCheckBox);
        items.add(shoppingCheckBox);
        items.add(theaterCheckBox);
        items.add(sportsCheckBox);
        items.add(concertCheckBox);
        items.add(historicCheckBox);
        items.add(sightseeingCheckBox);
        items.add(bikingCheckBox);
        items.add(zooCheckBox);
        items.add(architecturalCheckBox);
        items.add(monumentCheckBox);
        items.add(religiousCheckBox);
        items.add(themeparkCheckBox);
        items.add(beachCheckBox);

        String text = "";

        for (CheckBox item : items){
            if(item.isChecked()) {
                text = item.getText().toString();
                allTags = allTags + "#" + text + " ";
            }
        }
    }


    private void saveRecommendation() {
        checkBoxChecking();

        String nameOfActivity = nameOfActivityEditText.getText().toString().trim();
        String nearestMRT = nearestMRTEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String timePeriod = timePeriodEditText.getText().toString().trim();
        String openingHours = openingHoursEditText.getText().toString();
        String cost = priceEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        //boolean isPermanent = true; //dummy value => no longer needed, replaced with Opening Hours

        if (!TextUtils.isEmpty(nameOfActivity)
                && !TextUtils.isEmpty(nearestMRT)
                && !TextUtils.isEmpty(address)
                && !TextUtils.isEmpty(timePeriod)
                && !TextUtils.isEmpty(openingHours)
                && !TextUtils.isEmpty(timePeriod)
                && !TextUtils.isEmpty(cost)
                && (imageUri != null)) {
            String id = databaseReference.push().getKey();
            uniqueID.add(id);

            uploadingInfo(id, nameOfActivity, nearestMRT, address, timePeriod, openingHours, cost, allTags, description);

        } else {
            if (TextUtils.isEmpty(nameOfActivity)) {
                Toast.makeText(this, "Please fill up name of Activity", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(nearestMRT)) {
                Toast.makeText(this, "Please fill up the nearest MRT station", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(address)) {
                Toast.makeText(this, "Please fill up the address", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(timePeriod)) {
                Toast.makeText(this, "Please fill up the time period of the activity", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(description)) {
                Toast.makeText(this, "Please fill up the description of the activity", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(openingHours)) {
                Toast.makeText(this, "Please fill up the opening hours of the activity\"", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(cost)) {
                Toast.makeText(this, "Please fill up the cost of the activity\"", Toast.LENGTH_SHORT).show();
            } else if (imageUri == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill up the required fields", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //the all mrt stations
    public void thatDialogThingy() {
        allMrts = new String[ALLMRTSTATIONS.length];
        int counter = 0;
        for (String s : ALLMRTSTATIONS) {
            allMrts[counter] = s;
            counter++;
        }

        Arrays.sort(allMrts);

        selectMRTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RecommendingActivity.this);
                mBuilder.setTitle("Select nearby MRT Stations");
                mBuilder.setMultiChoiceItems(allMrts, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            if (! selectedMRT.contains(which)) {
                                selectedMRT.add(which);
                            } else {
                                if (selectedMRT.contains(which)) {
                                    selectedMRT.remove(selectedMRT.indexOf(which));
                                }
                            }
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < selectedMRT.size(); i++) {
                            item = item + allMrts[selectedMRT.get(i)];
                            if (i != selectedMRT.size() - 1) {
                                item = item + "/";
                            }
                        }
                        nearestMRTEditText.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            selectedMRT.clear();
                            nearestMRTEditText.setText("");

                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }


    public void uploadingInfo(final String id, final String nameOfActivity,
                              final String nearestMRT, final String address,
                              final String timePeriod, final String openingHours, final String cost, final String allTags, final String description) {
        //final StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");
        final StorageReference filePath = storageReference.child("images/"+ UUID.randomUUID().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Uploading image...");
        progressDialog.show();

        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        progressDialog.dismiss();
                        Toast.makeText(RecommendingActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();


                        //UploadImage uploadImage = new UploadImage(activityName, downloadUrl);
                        RecommendationDetails recommendationDetails =
                                new RecommendationDetails(id, nameOfActivity, nearestMRT, address,
                                        timePeriod, openingHours, allTags, cost, downloadUrl, description);

                        //getUid() is a built-in function in Firebase
                        databaseReference.child(nameOfActivity).setValue(recommendationDetails);

                        //databaseReference.child(activityName).child("imageUrl").setValue(downloadUrl);
                        //DatabaseReference reff = FirebaseDatabase.getInstance().getReference("allActivities");
                        //reff.child(nameOfActivity).setValue(nameOfActivity);

                        Toast.makeText(RecommendingActivity.this, "Recommendation Saved...", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    }
                });
            }
        });
    }



    public void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick); //opening gallery
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                uploadedImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        } else if (requestCode == selectMRT) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                String returnString = data.getStringExtra("selectedMRTs");

                // Set text view with string
                nearestMRTEditText = findViewById(R.id.nearestMRTEditText);
                nearestMRTEditText.setText(returnString);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == recommendActivityButton) {
            if (!TORCheckBox.isChecked()) {
                Toast.makeText(this, "You have to agree to the Terms and Conditions before proceeding", Toast.LENGTH_SHORT).show();
            } else {
                new android.support.v7.app.AlertDialog.Builder(RecommendingActivity.this)
                        .setTitle("Submit Recommendation")
                        .setMessage("Would you like to submit your recommendation?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                saveRecommendation();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();
            }
        }

        if (v == uploadPicButton) {
            openGallery();
        }
    }
}
