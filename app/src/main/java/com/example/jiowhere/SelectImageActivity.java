package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener {
    Button chooseButton;
    Button uploadButton;
    TextView activityNameTextView;
    ImageView chosenImage;

    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    public static final int GalleryPick = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseButton = findViewById(R.id.chooseButton);
        uploadButton = findViewById(R.id.uploadButton);
        chosenImage = findViewById(R.id.chosenImage);
        activityNameTextView = findViewById(R.id.activityNameTextView);

        String activityName = getIntent().getStringExtra("activityName");
        activityNameTextView.setText(activityName);

        chooseButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReferenceFromUrl("gs://jiowhere-4f215.appspot.com/images");
        storageReference = storage.getReference();
        //StorageReference storageRef = storage.getReferenceFromUrl("gs:<bucket address>");
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

    private void uploadImage() {

        if(imageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SelectImageActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                            //UploadImage uploadImage = new UploadImage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SelectImageActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(SelectImageActivity.this, "Error Error", Toast.LENGTH_SHORT).show();
        }
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
                chosenImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == chooseButton) {
            openGallery();
        }

        if (v == uploadButton) {
            uploadImage();
        }
    }
}


