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

public class RecommendationDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ListView mListView;

    int[] images = {R.drawable.sentosa, R.drawable.two, R.drawable.three, R.drawable.four};
    String[] reviews = {"first", "2nd", "sdddddddddddddddddddddddddddddddddddddddddddddddddddddddd", "nxw"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_layout);

        mListView = (ListView) findViewById(R.id.reviewsList);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);

    }


    @Override
    public void onClick(View v) {
        /*
        if (v == textViewSignIn) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        */
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
            TextView aTextView = (TextView) view.findViewById(R.id.reviewBox);

            mImageView.setImageResource(images[position]);
            aTextView.setText(reviews[position]);

            return view;
        }
    }
}


/*
package com.example.jiowhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RecommendationDetailsActivity extends AppCompatActivity {

    ListView mListView;

    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four};
    String[] reviews = {"first", "2nd", "sdddddddddddddddddddddddddddddddddddddddddddddddddddddddd", "nxw"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_layout);

        mListView = (ListView) findViewById(R.id.reviewsList);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        mListView.setAdapter(customAdaptor);


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

            View view = getLayoutInflater().inflate(R.layout.custom_listview_layout, null);

            ImageView mImageView = (ImageView) view.findViewById(R.id.profile_picture);
            TextView aTextView = (TextView) view.findViewById(R.id.reviewBox);

            mImageView.setImageResource(images[position]);
            aTextView.setText(reviews[position]);

            return view;
        }
    }
}
*/
