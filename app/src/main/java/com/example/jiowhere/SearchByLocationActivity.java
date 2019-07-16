package com.example.jiowhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.jiowhere.RecommendationListActivity.LOCATION;

public class SearchByLocationActivity extends AppCompatActivity {

    private String[] locations = new String[] {"Harbourfront", "Kent Ridge", "Buona Vista", "Clarke Quay", "Expo", "Serangoon"};
    EditText locationSearch;
    ListView listView;
    LocationListAdaptor adaptor;
    ArrayList<LocationAndNumber> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationSearch = findViewById(R.id.locationEditText);

        listView = (ListView) findViewById(R.id.locationListView);

        for (int i = 0; i < locations.length; i++) {
            int numAct = 0;
            for(String s : LOCATION) {
                if (s.contains(locations[i])) {
                    numAct++;
                }
            }
            LocationAndNumber lan = new LocationAndNumber(locations[i], numAct);
            arrayList.add(lan);
        }

        adaptor = new LocationListAdaptor(this, arrayList);
        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view.findViewById(R.id.locationName);
                String selectedFromList = tv.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("locationStuff", selectedFromList);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        locationSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");


                adaptor.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }
}
