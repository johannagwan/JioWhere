package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class TagSystem extends AppCompatActivity {

    private static final String[] TAGS = new String[] { "Family", "Friends", "Lover", "Solo", "Indoor", "Outdoor", "Culinary", "Romance" };
    Context mContext;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_system);

        final TextView testing = findViewById(R.id.testing);
        //final TextView searchTag = findViewById(R.id.filterByTags);
        //final SearchView searchTag = findViewById(R.id.tagSearchView);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TAGS);
        listView = (ListView) findViewById(R.id.tagsList);
        listView.setAdapter(itemsAdapter);

        //String selectedFromList;
        //final String searchedText = getIntent().getStringExtra("searchedText");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String) (listView.getItemAtPosition(position));
                testing.setText(selectedFromList);

                Intent intent = new Intent();
                intent.putExtra("keyName", selectedFromList);
                setResult(RESULT_OK, intent);
                finish();
                //finishActivity(1);

                /*
                Intent intent = new Intent();
                intent.putExtra("editTextValue", selectedFromList);
                setResult(RESULT_OK, intent);
                finish();
                */
            }});
    }

}
