package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CategorySegment extends AppCompatActivity implements View.OnClickListener{
//    private TextView textView;
//    private TextView textView2;
    private Intent intent;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_segment);

        button1 = (Button)findViewById(R.id.ITEM1);
        button2 = (Button)findViewById(R.id.ITEM2);
        button3 = (Button)findViewById(R.id.ITEM3);
        button4 = (Button)findViewById(R.id.ITEM4);
        button5 = (Button)findViewById(R.id.ITEM5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

//        textView = (TextView)findViewById(R.id.pp);
//        textView2 = (TextView)findViewById(R.id.qq);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String productName = bundle.getString("category");
            ShowDescription(productName);
        }
    }
    private void ShowDescription(String productName) {
        if(productName.equals("meat_fish")){
            button1.setText("beef");
            button2.setText("mutton");
            button3.setText("chicken");
            button4.setText("egg");
            button5.setText("fish");
        }
        else if(productName.equals("personal_care")){
            button1.setText("beef2");
            button2.setText("mutton");
            button3.setText("chicken");
            button4.setText("egg");
            button5.setText("fish");
        }
        else if(productName.equals("stationary")){
            button1.setText("beef3");
            button2.setText("mutton");
            button3.setText("chicken");
            button4.setText("egg");
            button5.setText("fish");
        }
        else if(productName.equals("processed_food")){
            button1.setText("beef4");
            button2.setText("mutton");
            button3.setText("chicken");
            button4.setText("egg");
            button5.setText("fish");
        }
        else if(productName.equals("vegetables")){
            button1.setText("beef5");
            button2.setText("mutton");
            button3.setText("chicken");
            button4.setText("egg");
            button5.setText("fish");
        }
    }

    @Override
    public void onClick(View view) {

    }
}