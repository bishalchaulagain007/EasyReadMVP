package com.comlu.foodnepal.easyreadmvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context c;
    String[] players;

    public SpinnerAdapter(Context ctx, String[] players) {
        super(ctx, R.layout.custom_spinner_layout, players);

        //initializing
        this.c = ctx;
        this.players = players;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner_layout, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tvName);

        //setting data
        textView.setText(players[position]);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner_layout, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tvName);

        //setting data
        textView.setText(players[position]);

        return convertView;
    }
}
