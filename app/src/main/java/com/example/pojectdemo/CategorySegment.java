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
import android.widget.LinearLayout;

public class CategorySegment extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;

    private LinearLayout button1;
    private LinearLayout button2;
    private LinearLayout button3;
    private LinearLayout button4;
    private LinearLayout button5;
    private TextView name1;
    private TextView name2;
    private TextView name3;
    private TextView name4;
    private TextView name5;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private String str1 = "";
    private String str2 = "";
    private String str3 = "";
    private String str4 = "";
    private String str5 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_category_segment);

        button1 = (LinearLayout)this.findViewById(R.id.ITEM1);
        button2 = (LinearLayout)this.findViewById(R.id.ITEM2);
        button3 = (LinearLayout)this.findViewById(R.id.ITEM3);
        button4 = (LinearLayout)this.findViewById(R.id.ITEM4);
        button5 = (LinearLayout)this.findViewById(R.id.ITEM5);

        name1 = (TextView) this.findViewById(R.id.ITEM1name);
        name2 = (TextView) this.findViewById(R.id.ITEM2name);
        name3 = (TextView) this.findViewById(R.id.ITEM3name);
        name4 = (TextView) this.findViewById(R.id.ITEM4name);
        name5 = (TextView) this.findViewById(R.id.ITEM5name);

        img1 = (ImageView)this.findViewById(R.id.IMG1);
        img2 = (ImageView)this.findViewById(R.id.IMG2);
        img3 = (ImageView)this.findViewById(R.id.IMG3);
        img4 = (ImageView)this.findViewById(R.id.IMG4);
        img5 = (ImageView)this.findViewById(R.id.IMG5);

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
            name1.setText("beef");
            name2.setText("mutton");
            name3.setText("chicken");
            name4.setText("egg");
            name5.setText("fish");
            str1 = "beef";
            str2 = "mutton";
            str3 = "chicken";
            str4 = "egg";
            str5 = "fish";
            img1.setImageResource(R.drawable.beef);
            img2.setImageResource(R.drawable.mutton);
            img3.setImageResource(R.drawable.chicken);
            img4.setImageResource(R.drawable.eggs);
            img5.setImageResource(R.drawable.fish);
        }
        else if(productName.equals("personal_care")){
            name1.setText("toothpaste");
            name2.setText("soap");
            name3.setText("shampoo");
            name4.setText("handwash");
            name5.setText("sanitizer");
            str1 = "toothpaste";
            str2 = "soap";
            str3 = "shampoo";
            str4 = "handwash";
            str5 = "sanitizer";
            img1.setImageResource(R.drawable.toothbrush);
            img2.setImageResource(R.drawable.soap);
            img3.setImageResource(R.drawable.shampoo);
            img4.setImageResource(R.drawable.handwash);
            img5.setImageResource(R.drawable.hand_sanitizer);
        }
        else if(productName.equals("stationary")){
            name1.setText("notebook");
            name2.setText("pen");
            name3.setText("pencil");
            name4.setText("color-pencil");
            name5.setText("eraser");
            str1 = "notebook";
            str2 = "pen";
            str3 = "pencil";
            str4 = "color-pen";
            str5 = "eraser";
            img1.setImageResource(R.drawable.book);
            img2.setImageResource(R.drawable.pen);
            img3.setImageResource(R.drawable.pencil);
            img4.setImageResource(R.drawable.eraser);
            img5.setImageResource(R.drawable.colored_pencils);
        }
        else if(productName.equals("processed_food")){
            name1.setText("chips");
            name2.setText("chanachur");
            name3.setText("beverage");
            name4.setText("cake");
            name5.setText("biscuit");
            str1 = "chips";
            str2 = "chanachur";
            str3 = "beverage";
            str4 = "cake";
            str5 = "biscuit";
        }
        else if(productName.equals("vegetables")){
            name1.setText("aloo");
            name2.setText("fulkopi");
            name3.setText("misti-kumra");
            name4.setText("tomato");
            name5.setText("lebu");
            str1 = "aloo";
            str2 = "fulkopi";
            str3 = "misti-kumra";
            str4 = "tomato";
            str5 = "lebu";
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ITEM1){
            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", str1);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM2){
            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", str2);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM3){
            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", str3);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM4){
            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", str4);
            startActivity(intent);
        }
        if(view.getId() == R.id.ITEM5){
            intent = new Intent(CategorySegment.this, ProductInfo.class);
            intent.putExtra("product", str5);
            startActivity(intent);
        }
    }
}