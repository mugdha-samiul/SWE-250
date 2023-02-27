package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ProductInfo extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        textView1 = (TextView)findViewById(R.id.detail1);
        textView2 = (TextView)findViewById(R.id.detail2);
        textView3 = (TextView)findViewById(R.id.detail3);
        textView4 = (TextView)findViewById(R.id.detail4);
        textView5 = (TextView)findViewById(R.id.detail5);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String productName = bundle.getString("product");
            ShowDescription(productName);
        }
    }
    private void ShowDescription(String productName) {
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
    }
}