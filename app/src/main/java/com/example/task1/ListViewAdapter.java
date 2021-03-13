package com.example.task1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> usersList){
        super(context,listLayout,field,usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout,null, false);
        TextView name = listViewItem.findViewById(R.id.textViewName);
        TextView code = listViewItem.findViewById(R.id.textViewCode);
        TextView value = listViewItem.findViewById(R.id.textViewValue);
        TextView valueChange = listViewItem.findViewById(R.id.textViewValueChange);
        try {
            code.setText(usersList.get(position).getString("CharCode"));
            name.setText(usersList.get(position).getString("Name"));
            String v = usersList.get(position).getString("Value");
            value.setText(v);
            String vc = usersList.get(position).getString("Previous");
            if (Double.parseDouble(vc) < Double.parseDouble(v)){
                valueChange.setTextColor(Color.parseColor("#00ff00"));
                valueChange.setText("+"+String.format("%.4f",((Double.parseDouble(vc) - Double.parseDouble(v))*(-1))));
            }
            else {
                valueChange.setTextColor(Color.parseColor("#e20000"));
                valueChange.setText(String.format("%.4f",((Double.parseDouble(v) - Double.parseDouble(vc)))));
            }
        }catch (JSONException je){
            je.printStackTrace();
        }
        return listViewItem;

    }


}
