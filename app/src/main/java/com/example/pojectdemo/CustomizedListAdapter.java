package com.example.pojectdemo;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomizedListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] productnames;
    private final String[] productprices;

    public CustomizedListAdapter(Activity context, String[] productnames,String[] productprices) {
        super(context, R.layout.customizedlist, productnames);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.productnames=productnames;
        this.productprices=productprices;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customizedlist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.product_name);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.product_price);

        titleText.setText(productnames[position]);
        subtitleText.setText(productprices[position]);

        return rowView;

    }
}