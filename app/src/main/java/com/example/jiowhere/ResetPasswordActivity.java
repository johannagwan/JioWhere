package com.example.jiowhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView SignInText;
    private Button resetPwButton;
    private EditText emailResetPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        SignInText = findViewById(R.id.SignInText);
        resetPwButton = findViewById(R.id.resetPwButton);
        emailResetPW = findViewById(R.id.emailResetPW);

        SignInText.setOnClickListener(this);
        resetPwButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void resetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == SignInText) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        if (v == resetPwButton) {
            String email = emailResetPW.getText().toString().trim();

            resetPassword(email);
        }
    }
}
