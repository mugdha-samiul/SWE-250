package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pojectdemo.home.Featured_Adapter;
import com.example.pojectdemo.home.featuredHelper;

import java.util.ArrayList;

public class MainDashboard extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

    private LinearLayout MeatFishButton;
    private LinearLayout PersonalCareButton;
    private LinearLayout StationaryButton;
    private LinearLayout ProcessedFoodButton;
    private LinearLayout VegetablesButton;

    RecyclerView featured_recycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_dashboard);

        MeatFishButton = (LinearLayout)this.findViewById(R.id.meatandfish);
        PersonalCareButton = (LinearLayout)this.findViewById(R.id.personalcare);
        StationaryButton = (LinearLayout)this.findViewById(R.id.stationary);
        ProcessedFoodButton = (LinearLayout)this.findViewById(R.id.processedfood);
        VegetablesButton = (LinearLayout)this.findViewById(R.id.vegetables);
        featured_recycler = this.findViewById(R.id.Featured_Recycler);

        MeatFishButton.setOnClickListener(this);
        PersonalCareButton.setOnClickListener(this);
        StationaryButton.setOnClickListener(this);
        ProcessedFoodButton.setOnClickListener(this);
        VegetablesButton.setOnClickListener(this);

        featured_recycler();
    }

    private void featured_recycler() {
        featured_recycler.setHasFixedSize(true);
        featured_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<featuredHelper> featuredLocations = new ArrayList<>();

        featuredLocations.add(new featuredHelper(R.drawable.featured_chaldal, "CHALDAL", "CHALDAL is currently the biggest online grocary marketplace in Bangladesh"));
        featuredLocations.add(new featuredHelper(R.drawable.featured_othoba, "OTHOBA", "OTHOBA is one of the fastest growing online marketplace in Bangladesh"));
        featuredLocations.add(new featuredHelper(R.drawable.featured_sobjibazaar, "SOBJIBAZAAR", "sobjibazar is also a potential newcomer online shop in BD"));

        adapter = new Featured_Adapter(featuredLocations);
        featured_recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.meatandfish) {
            intent = new Intent(MainDashboard.this, CategorySegment.class);
            intent.putExtra("category", "meat_fish");
            startActivity(intent);
        }
        if(view.getId() == R.id.personalcare){
            intent = new Intent(MainDashboard.this, CategorySegment.class);
            intent.putExtra("category", "personal_care");
            startActivity(intent);
        }
        if(view.getId() == R.id.stationary){
            intent = new Intent(MainDashboard.this, CategorySegment.class);
            intent.putExtra("category", "stationary");
            startActivity(intent);
        }
        if(view.getId() == R.id.processedfood){
            intent = new Intent(MainDashboard.this, CategorySegment.class);
            intent.putExtra("category", "processed_food");
            startActivity(intent);
        }
        if(view.getId() == R.id.vegetables){
            intent = new Intent(MainDashboard.this, CategorySegment.class);
            intent.putExtra("category", "vegetables");
            startActivity(intent);
        }
    }
}