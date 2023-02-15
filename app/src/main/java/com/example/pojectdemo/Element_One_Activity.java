package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Element_One_Activity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_one);

        imageView = (ImageView)findViewById(R.id.ElementPic);
        textView = (TextView)findViewById(R.id.ElementDescription);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String productName = bundle.getString("product");
            ShowDescription(productName);
        }
    }

    private void ShowDescription(String productName) {
        if(productName.equals("rice")){
            imageView.setImageResource(R.drawable.brr);
            textView.setText(R.string.riceChalDal);
        }
        else if(productName.equals("egg")){
            imageView.setImageResource(R.drawable.egg);
            textView.setText(R.string.eggChalDal);
        }
        else if(productName.equals("potato")){
            imageView.setImageResource(R.drawable.potato);
            textView.setText(R.string.potatoChalDal);
        }
        else if(productName.equals("bag")){
            imageView.setImageResource(R.drawable.bag);
            textView.setText(R.string.bagChalDal);
        }
        else if(productName.equals("bottle")){
            imageView.setImageResource(R.drawable.bottle);
            textView.setText(R.string.bottleChalDal);
        }
    }
}