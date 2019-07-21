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
import com.google.firebase.auth.FirebaseAuth;
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
    private Switch permanentSwitch;
    private CheckBox familyCheckBox;
    private CheckBox friendsCheckBox;
    private CheckBox loverCheckBox;
    private CheckBox soloCheckBox;
    private CheckBox indoorCheckBox;
    private CheckBox outdoorCheckBox;
    private CheckBox romanceCheckBox;
    private CheckBox culinaryCheckBox;
    private ImageView uploadedImage;

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

    //making the checkbox
    //String[] listItems; ALLMRTSTATIONS
    boolean[] checkedItems;
    ArrayList<Integer> selectedMRT = new ArrayList<>(); //mUserIrems
    //ALLMRTSTATIONS = listitems
    String[] allMrts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommending);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializing firebase authentication object
        //firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("recommendations");

        //initializing view
        nameOfActivityEditText = (EditText) findViewById(R.id.nameOfActivityEditText);
        nearestMRTEditText = (EditText) findViewById(R.id.nearestMRTEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        timePeriodEditText = (EditText) findViewById(R.id.timePeriodEditText);
        openingHoursEditText = (EditText) findViewById(R.id.openingHoursEditText);



        familyCheckBox = (CheckBox) findViewById(R.id.familyCheckBox);
        friendsCheckBox = (CheckBox) findViewById(R.id.friendsCheckBox);
        loverCheckBox = (CheckBox) findViewById(R.id.loverCheckBox);
        soloCheckBox = (CheckBox) findViewById(R.id.soloCheckBox);
        indoorCheckBox = (CheckBox) findViewById(R.id.indoorCheckBox);
        outdoorCheckBox = (CheckBox) findViewById(R.id.outdoorCheckBox);
        romanceCheckBox = (CheckBox) findViewById(R.id.romanceCheckBox);
        culinaryCheckBox = (CheckBox) findViewById(R.id.culinaryCheckBox);

        uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
        uploadedImage.setImageResource(R.drawable.empty);

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
        //boolean isPermanent = true; //dummy value => no longer needed, replaced with Opening Hours

        if (!TextUtils.isEmpty(nameOfActivity)
                && !TextUtils.isEmpty(nearestMRT)
                && !TextUtils.isEmpty(address)
                && !TextUtils.isEmpty(timePeriod)) {
            String id = databaseReference.push().getKey();
            uniqueID.add(id);

            uploadingInfo(id, nameOfActivity, nearestMRT, address, timePeriod, openingHours, allTags);

        } else {
            if (TextUtils.isEmpty(nameOfActivity)) {
                Toast.makeText(this, "Please fill up name of Activity", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(nearestMRT)) {
                Toast.makeText(this, "Please fill up the nearest MRT station", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(address)) {
                Toast.makeText(this, "Please fill up the address", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(timePeriod)) {
                Toast.makeText(this, "Please fill up the time period of the activity", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Please fill up the required fields", Toast.LENGTH_SHORT).show();
            }
        }

    }

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


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadingInfo(final String id, final String nameOfActivity,
                              final String nearestMRT, final String address,
                              final String timePeriod, final String openingHours, final String allTags) {
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
                                        timePeriod, openingHours, allTags, downloadUrl);

                        //getUid() is a built-in function in Firebase
                        databaseReference.child(nameOfActivity).setValue(recommendationDetails);

                        //databaseReference.child(activityName).child("imageUrl").setValue(downloadUrl);
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("allActivities");
                        reff.child(nameOfActivity).setValue(nameOfActivity);

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
            saveRecommendation();
        }

        if (v == uploadPicButton) {
            openGallery();
        }

        /*
        if (v == selectMRTButton) {
            Intent intent = new Intent(this, SelectMrtStationActivity.class);
            startActivityForResult(intent, selectMRT);
        }
        */
    }
}
