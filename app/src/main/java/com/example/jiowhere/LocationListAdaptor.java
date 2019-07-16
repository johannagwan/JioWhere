package com.example.jiowhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationListAdaptor extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<LocationAndNumber> list;
    ArrayList<LocationAndNumber> arrayList;

    public LocationListAdaptor(Context mContext, List<LocationAndNumber> list) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;

        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(list);
    }

    private class ViewHolderr {
        TextView txtName;
        TextView numActivities;
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
        LocationAndNumber dataModel = list.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolderr viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolderr();
            //LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_location_listview, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.locationName);
            viewHolder.numActivities = (TextView) convertView.findViewById(R.id.numOfActivities);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderr) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(dataModel.getNameOfLocation());
        String numAct = dataModel.getNumberOfActivities() + "";
        viewHolder.numActivities.setText(numAct);
        // Return the completed view to render on screen
        return convertView;
    }

    public void filter (String charText) {
        //currentLocation = charText;

        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();

        if (charText.length() == 0) {
            list.addAll(arrayList);
        } else {
            for (LocationAndNumber rc : arrayList) {
                if (rc.getNameOfLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(rc);
                }
            }
        }
        notifyDataSetChanged();
    }
}
