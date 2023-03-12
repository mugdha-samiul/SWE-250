package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CategorySegment extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            button1.setText("toothpaste");
            button2.setText("soap");
            button3.setText("shampoo");
            button4.setText("handwash");
            button5.setText("sanitizer");
        }
        else if(productName.equals("stationary")){
            button1.setText("notebook");
            button2.setText("pen");
            button3.setText("pencil");
            button4.setText("color-pencil");
            button5.setText("eraser");
        }
        else if(productName.equals("processed_food")){
            button1.setText("chips");
            button2.setText("chanachur");
            button3.setText("beverage");
            button4.setText("cake");
            button5.setText("biscuit");
        }
        else if(productName.equals("vegetables")){
            button1.setText("aloo");
            button2.setText("fulkopi");
            button3.setText("misti-kumra");
            button4.setText("tomato");
            button5.setText("lebu");
        }
    }

    @Override
    public void onClick(View view) {
        Button b;
        String specific_product_name = "";
        if(view.getId() == R.id.ITEM1){
            b = (Button) view;
            specific_product_name = b.getText().toString();

            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", specific_product_name);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM2){
            b = (Button) view;
            specific_product_name = b.getText().toString();

            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", specific_product_name);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM3){
            b = (Button) view;
            specific_product_name = b.getText().toString();

            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", specific_product_name);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM4){
            b = (Button) view;
            specific_product_name = b.getText().toString();

            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", specific_product_name);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM5){
            b = (Button) view;
            specific_product_name = b.getText().toString();

            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", specific_product_name);
            startActivity(intent);
        }
        //intent = new Intent(CategorySegment.this, ProductInfo.class);
        //intent.putExtra("product", specific_product_name);
        //startActivity(intent);
    }
}