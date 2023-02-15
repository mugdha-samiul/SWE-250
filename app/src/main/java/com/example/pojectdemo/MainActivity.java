package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;

    private Button RiceButton;
    private Button EggButton;
    private Button PotatoButton;
    private Button BagButton;
    private Button BottleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RiceButton = (Button)findViewById(R.id.RICE);
        EggButton = (Button)findViewById(R.id.EGG);
        PotatoButton = (Button)findViewById(R.id.POTATO);
        BagButton = (Button)findViewById(R.id.BAG);
        BottleButton = (Button)findViewById(R.id.BOTTLE);

        RiceButton.setOnClickListener(this);
        EggButton.setOnClickListener(this);
        PotatoButton.setOnClickListener(this);
        BagButton.setOnClickListener(this);
        BottleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.RICE){
            intent = new Intent(MainActivity.this, Element_One_Activity.class);
            intent.putExtra("product", "rice");
            startActivity(intent);
        }
        if(view.getId() == R.id.EGG){
            intent = new Intent(MainActivity.this, Element_One_Activity.class);
            intent.putExtra("product", "egg");
            startActivity(intent);
        }
        if(view.getId() == R.id.POTATO){
            intent = new Intent(MainActivity.this, Element_One_Activity.class);
            intent.putExtra("product", "potato");
            startActivity(intent);
        }
        if(view.getId() == R.id.BAG){
            intent = new Intent(MainActivity.this, Element_One_Activity.class);
            intent.putExtra("product", "bag");
            startActivity(intent);
        }
        if(view.getId() == R.id.BOTTLE){
            intent = new Intent(MainActivity.this, Element_One_Activity.class);
            intent.putExtra("product", "bottle");
            startActivity(intent);
        }
    }
}