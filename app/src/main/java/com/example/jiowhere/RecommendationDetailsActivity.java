package com.example.jiowhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class RecommendationDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    //This is the full recommendation -> the detailed recommendation

    private ListView mListView;
    private Button leaveReviewButton;

    //for Firebase database retrieval
    private DatabaseReference reff;


    int[] images = {R.drawable.sentosa, R.drawable.two, R.drawable.three, R.drawable.four};
    //String[] reviews = {"first", "2nd", "sdddddddddddddddddddddddddddddddddddddddddddddddddddddddd", "nxw"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_layout);

        mListView = (ListView) findViewById(R.id.reviewsList);
        leaveReviewButton = (Button) findViewById(R.id.leaveReviewButton);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);

    }

    @Override
    public void onClick(View v) {
        if (v == leaveReviewButton) {
            startActivity(new Intent(this, LeaveReviewActivity.class));
        }
    }

    //adaptor for the listview
    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.review_layout, null);

            ImageView mImageView = (ImageView) view.findViewById(R.id.profile_picture);
            final TextView aTextView = (TextView) view.findViewById(R.id.reviewBox);

            reff = FirebaseDatabase.getInstance().getReference().child("recommendations").child("NUS");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String reviews = dataSnapshot.child("reviews").getValue().toString();
                    aTextView.setText(reviews);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mImageView.setImageResource(images[position]);
            //aTextView.setText(reviews[position]);

            return view;
        }
    }
}