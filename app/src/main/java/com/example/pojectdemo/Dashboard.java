package com.example.pojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

    private Button MeatFishButton;
    private Button PersonalCareButton;
    private Button StationaryButton;
    private Button ProcessedFoodButton;
    private Button VegetablesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        MeatFishButton = (Button)findViewById(R.id.MEATFISH);
        PersonalCareButton = (Button)findViewById(R.id.PERSONAL_CARE);
        StationaryButton = (Button)findViewById(R.id.STATIONARY);
        ProcessedFoodButton = (Button)findViewById(R.id.PROCESSED_FOOD);
        VegetablesButton = (Button)findViewById(R.id.VEGETABLES);

        MeatFishButton.setOnClickListener(this);
        PersonalCareButton.setOnClickListener(this);
        StationaryButton.setOnClickListener(this);
        ProcessedFoodButton.setOnClickListener(this);
        VegetablesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.MEATFISH){
            intent = new Intent(Dashboard.this, CategorySegment.class);
            intent.putExtra("category", "meat_fish");
            startActivity(intent);
        }
        if(view.getId() == R.id.PERSONAL_CARE){
            intent = new Intent(Dashboard.this, CategorySegment.class);
            intent.putExtra("category", "personal_care");
            startActivity(intent);
        }
        if(view.getId() == R.id.STATIONARY){
            intent = new Intent(Dashboard.this, CategorySegment.class);
            intent.putExtra("category", "stationary");
            startActivity(intent);
        }
        if(view.getId() == R.id.PROCESSED_FOOD){
            intent = new Intent(Dashboard.this, CategorySegment.class);
            intent.putExtra("category", "processed_food");
            startActivity(intent);
        }
        if(view.getId() == R.id.VEGETABLES){
            intent = new Intent(Dashboard.this, CategorySegment.class);
            intent.putExtra("category", "vegetables");
            startActivity(intent);
        }
    }
}