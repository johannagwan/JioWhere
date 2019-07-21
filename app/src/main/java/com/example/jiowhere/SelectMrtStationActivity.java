package com.example.jiowhere;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.jiowhere.SearchByLocationActivity.ALLMRTSTATIONS;

public class SelectMrtStationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchMrtEditText;
    ListView selectMrtListView;
    Button submitSelectionButton;
    //CheckBox checkBox;

    CustomAdaptor adaptor;

    ArrayList<String> arrayList;

    String selectedFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mrt_station);

        selectedFromList = "";

        arrayList = new ArrayList<>();
        for(String s : ALLMRTSTATIONS) {
            arrayList.add(s);
        }


        //search
        setFilter();

        //listview
        selectMrtListView = findViewById(R.id.selectMrtListView);

        submitSelectionButton = findViewById(R.id.submitSelectionButton);
        submitSelectionButton.setOnClickListener(this);

        adaptor = new CustomAdaptor(this, arrayList);
        selectMrtListView.setAdapter(adaptor);
    }

    public void setFilter() {
        searchMrtEditText = findViewById(R.id.searchMrtEditText);
        searchMrtEditText.addTextChangedListener(new TextWatcher() {
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

    public void selectCheckedBoxes() {
        //String selectedFromList = "";
        List<CheckBox> items = new ArrayList<>();

        int sizeOfList = ALLMRTSTATIONS.length;


        View v;
        CheckBox et;
        for (int i = 0; i < sizeOfList; i++) {
            v = selectMrtListView.getAdapter().getView(i, null, null);
            et = (CheckBox) v.findViewById(R.id.mrtStationcheckBox);
            if (et.isChecked()) {
                selectedFromList = selectedFromList + et.getText().toString() + "d";
            }
            //items.add(et);
        }



        String text = "";

        /*
        for (CheckBox item : items){
            if(item.isChecked()) {
                text = item.getText().toString();
                selectedFromList = selectedFromList + "/" + text + " ";
            }

        }
        */


        Intent intent = new Intent();
        intent.putExtra("selectedMRTs", "size is : " + selectedFromList + ".");
        //intent.putExtra("selectedMRTs", selectedFromList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == submitSelectionButton) {
            selectCheckedBoxes();
        }
    }

    class CustomAdaptor extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        List<String> list;
        ArrayList<String> arrayList;

        public CustomAdaptor(Context mContext, List<String> list) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
            this.list = list;

            this.arrayList = new ArrayList<>();
            this.arrayList.addAll(list);
        }

        private class ViewHolderr {
            CheckBox checkBox;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String dataModel = list.get(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolderr viewHolder;

            final View result;

            if (convertView == null) {
                viewHolder = new ViewHolderr();

                convertView = inflater.inflate(R.layout.mrt_list_checkbox_layout, parent, false);
                viewHolder.checkBox = convertView.findViewById(R.id.mrtStationcheckBox);
                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderr) convertView.getTag();
                result = convertView;
            }

            viewHolder.checkBox.setText(dataModel);
            if(viewHolder.checkBox.isChecked()) {
                String text = viewHolder.checkBox.getText().toString();
                selectedFromList = selectedFromList + "/" + text + " ";
            }

            // Return the completed view to render on screen
            return convertView;
        }


        //filter function
        public void filter (String charText) {
            //currentLocation = charText;

            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();

            if (charText.length() == 0) {
                list.addAll(arrayList);
            } else {
                for (String rc : arrayList) {
                    if (rc.toLowerCase(Locale.getDefault()).contains(charText)) {
                        list.add(rc);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
