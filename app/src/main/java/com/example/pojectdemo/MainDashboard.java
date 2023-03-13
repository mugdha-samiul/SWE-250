package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainDashboard extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

    private LinearLayout MeatFishButton;
    private LinearLayout PersonalCareButton;
    private LinearLayout StationaryButton;
    private LinearLayout ProcessedFoodButton;
    private LinearLayout VegetablesButton;

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

        MeatFishButton.setOnClickListener(this);
        PersonalCareButton.setOnClickListener(this);
        StationaryButton.setOnClickListener(this);
        ProcessedFoodButton.setOnClickListener(this);
        VegetablesButton.setOnClickListener(this);
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