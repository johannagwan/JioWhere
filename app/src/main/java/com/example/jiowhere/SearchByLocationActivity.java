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

import static com.example.jiowhere.ListViewAdaptor.MRTLOCATIONS;


public class SearchByLocationActivity extends AppCompatActivity {

    public static String[] ALLMRTSTATIONS = new String[] {
            //Red Line -> North South Line
            "Jurong East", "Bukit Batok", "Bukit Gombak", "Choa Chu Kang", "Yew Tee", "Kranji",
            "Marsiling", "Woodlands", "Admiralty", "Sembawang", "Canberra", "Yishun", "Khatib",
            "Yio Chu Kang", "Ang Mo Kio", "Bishan", "Braddell", "Toa Payoh", "Novena", "Newton",
            "Orchard", "Somerset", "Dhoby Ghaut", "City Hall", "Raffles Place", "Marina Bay", "Marina South Pier",

            //Green line -> East West Line
            "Tuas Link", "Tuas West Road", "Tuas Crescent", "Gul Circle", "Joo Koon", "Pioneer",
            "Boon Lay", "Lakeside", "Chinese Garden", "Clementi", "Dover", "Buona Vista", "Commonwealth",
            "Queenstown", "Redhill", "Tiong Bahru", "Outram Park", "Tanjong Pagar", "Bugis", "Lavender",
            "Kallang", "Aljunied", "Paya Lebar", "Eunos", "Kembangan", "Bedok", "Tanah Merah", "Simei",
            "Tampines", "Pasir Ris", "Expo", "Changi Airport",

            //Purple Line -> North East Line
            "HarbourFront", "Chinatown", "Clarke Quay", "Little India", "Farrer Park", "Boon Keng", "Potong Pasir",
            "Woodleigh", "Serangoon", "Kovan", "Hougang", "Buangkok", "Sengkang", "Punggol",

            //Yellow line -> Circle Line
            "Bras Basah", "Esplanade", "Promenade", "Nicoll Highway", "Stadium", "Mountbatten", "Dakota",
            "MacPherson", "Tai Seng", "Bartley", "Lorong Chuan", "Marymount", "Caldecott", "Botanic Gardens",
            "Farrer Road", "Holland Village", "One North", "Kent Ridge", "Haw Par Villa", "Pasir Panjang", "Labrador Park",
            "Telok Blangah", "Keppel", "Cantonment", "Prince Edward Road", "Bayfront",

            //Blue Line -> Downtown Line
            "Bukit Panjang", "Cashew", "Hillview", "Hume", "Beauty World", "King Albert Park", "Sixth Avenue",
            "Tan Kah Kee", "Stevens", "Rochor", "Downtown", "Telok Ayer", "Fort Canning", "Bencoolen", "Jalan Besar",
            "Bendemeer", "Geylang Bahru", "Mattar", "Ubi", "Kaki Bukit", "Bedok North", "Bedok Reservoir",
            "Tampines West", "Tampines East", "Upper Changi", "Expo", "Xilin", "Sungei Bedok"
    };




    //"Harbourfront", "Kent Ridge", "Buona Vista", "Clarke Quay", "Expo", "Serangoon"
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



        for (int i = 0; i < ALLMRTSTATIONS.length; i++) {
            int numAct = 0;
            for(String s : MRTLOCATIONS) { //MRTLOCATIONS is the mrt locations that have the activity -> can repeat
                if (s.contains(ALLMRTSTATIONS[i])) {
                    numAct++;
                }
            }
            LocationAndNumber lan = new LocationAndNumber(ALLMRTSTATIONS[i], numAct);
            arrayList.add(lan);
        }

        /*
        for (int i = 0; i < mrtUsed.length; i++) {

            LocationAndNumber lan = new LocationAndNumber(mrtUsed[i], 0);
            arrayList.add(lan);
        }
        */

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
