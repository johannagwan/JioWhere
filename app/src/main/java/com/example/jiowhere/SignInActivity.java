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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();

        /*if (firebaseAuth.getCurrentUser() != null) {
            //user is already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }*/


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

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

        progressDialog = new ProgressDialog(this);



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
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
