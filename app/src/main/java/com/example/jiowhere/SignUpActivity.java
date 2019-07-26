package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    //Main Activity -> SignUp Activity

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextDisplayName;
    private TextView textViewSignIn;
    private TextView resetPasswordSignup;
    private CheckBox acceptionCheckBox;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference reff;

    //private ArrayList<String>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        /*if (firebaseAuth.getCurrentUser() != null) {
            //user is already registered
            finish();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }*/

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.resetPwButton);

        editTextEmail = (EditText) findViewById(R.id.emailResetPW);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextDisplayName = findViewById(R.id.editTextDisplayName);
        textViewSignIn = (TextView) findViewById(R.id.SignInText);
        resetPasswordSignup = (TextView) findViewById(R.id.resetPasswordSignup);
        //acceptionCheckBox = findViewById(R.id.acceptionCheckBox);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        resetPasswordSignup.setOnClickListener(this);
    }


    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        //String password = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String displayName = editTextDisplayName.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }

        //checkUsername();

        //if both email and password are entered, show a progressDialogue
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserInformation userInformation = new UserInformation(email, displayName);
                            databaseReference.child(uID).setValue(userInformation);
                            databaseReference.child(uID).child("keepSignedIn").setValue("no");

                            finish();
                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Could not register. Please register again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }


    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewSignIn) {
            //will open Sign in activity here
            startActivity(new Intent(this, SignInActivity.class));
        }

        if (v == resetPasswordSignup) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }
}
