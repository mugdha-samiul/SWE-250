package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductInfo extends AppCompatActivity {
    /*
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    */
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_info);
        /*
        textView1 = (TextView)findViewById(R.id.detail1);
        textView2 = (TextView)findViewById(R.id.detail2);
        textView3 = (TextView)findViewById(R.id.detail3);
        textView4 = (TextView)findViewById(R.id.detail4);
        textView5 = (TextView)findViewById(R.id.detail5);
        */

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String productName = bundle.getString("product");
            Log.d("inside product info", "inside product info "+productName);
            String contextName = bundle.getString("context");
            ShowDescription(productName);
        }
    }
    private void ShowDescription(String productName) {
        /*
        if(productName.equals("item1")){
            String buttonLabel = "";
            buttonLabel = getString(R.string.potatoChalDal);
            textView1.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView2.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView3.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView4.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView5.setText(buttonLabel);
        }
        if(productName.equals("item2")){
            String buttonLabel = "";
            buttonLabel = getString(R.string.potatoChalDal);
            textView1.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView2.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView3.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView4.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView5.setText(buttonLabel);
        }
        if(productName.equals("item3")){
            String buttonLabel = "";
            buttonLabel = getString(R.string.riceChalDal);
            textView1.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView2.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView3.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView4.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView5.setText(buttonLabel);
        }
        if(productName.equals("item4")){
            String buttonLabel = "";
            buttonLabel = getString(R.string.riceChalDal);
            textView1.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView2.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView3.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView4.setText(buttonLabel);
            buttonLabel = getString(R.string.riceChalDal);
            textView5.setText(buttonLabel);
        }
        if(productName.equals("item5")){
            String buttonLabel = "";
            buttonLabel = getString(R.string.potatoChalDal);
            textView1.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView2.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView3.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView4.setText(buttonLabel);
            buttonLabel = getString(R.string.potatoChalDal);
            textView5.setText(buttonLabel);
        }
        */


        String filename = getFilesDir()+"/"+productName+ "-data.csv";

        Log.d("filename", filename);

        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                //records[i] = line;
                i++;
                records.add(line);
                Log.d("reading from csv", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        listView = findViewById(R.id.lv_customerList);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ProductInfo.this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(arrayAdapter);

    }
}