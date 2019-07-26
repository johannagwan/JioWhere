package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private TextView resetPasswordLogin;

    private CheckBox keepSignedIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();

        ifIsKeepedSignedIn();
        /*
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(SignInActivity.this, "Account detected, logging in automatically", Toast.LENGTH_LONG).show();
            //user is already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }
        */

        keepSignedIn = findViewById(R.id.keepSignedInCheckBox);
        keepSignedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (keepSignedIn.isChecked()) {
                    new AlertDialog.Builder(SignInActivity.this)
                            .setTitle("Keep account logged in?")
                            .setMessage("Would you like to keep account logged in on this device?\n" + "Make sure that this device " +
                                    "is only used by you to prevent security issues")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // logout
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // user doesn't want to logout
                                    keepSignedIn.setChecked(false);
                                }
                            })
                            .show();
                }
            }
        });

        editTextEmail = (EditText) findViewById(R.id.emailResetPW);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignIn = (TextView) findViewById(R.id.SignInText);
        resetPasswordLogin = (TextView) findViewById(R.id.resetPasswordLogin);

        //buttonSignIn.setOnClickListener(this);


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonSignIn){
                    userLogin();
                }
            }
        });
        textViewSignIn.setOnClickListener(this);
        resetPasswordLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);


    }



    private void ifIsKeepedSignedIn() {
        if (firebaseAuth.getCurrentUser() != null) {
            String uID = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("users").child(uID);

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String yesORno = dataSnapshot.child("keepSignedIn").getValue().toString();

                    if (yesORno.equals("yes")) {
                        Toast.makeText(SignInActivity.this, "Account detected, logging in automatically", Toast.LENGTH_LONG).show();
                        //user is already logged in
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }


    private void checkSignIn() {
        if(keepSignedIn.isChecked()) {
            String uID = "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            //databaseReference.child(uID).child("keepSignedIn").removeValue();
            databaseReference.child(uID).child("keepSignedIn").setValue("yes");

        } else {
            //idk
        }
    }


    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //String email = inputEmail.getText().toString();
        //final String password = inputPassword.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if both email and password are entered, show a progressDialogue
        progressDialog.setMessage("Logging in...");
        progressDialog.show();



        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        //user is successfully registered and logged in
                        checkSignIn();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    } else {
                        Toast.makeText(SignInActivity.this, "Could not sign in. Please register again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }


    @Override
    public void onClick(View v) {
        if (v == buttonSignIn) {
            userLogin();
        }

        if (v == textViewSignIn) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }

        if (v == resetPasswordLogin) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }
}
