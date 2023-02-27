package com.example.pojectdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Scraper extends AppCompatActivity {

    String productname="";

    Document doc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void scrape(){
        Content content = new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        protected Void doInBackground(Void... voids) {

            String record = "";
            String url = "";
            try {
                url = "https://chaldal.com/chicken";
                doc = Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            //Elements products = doc.select(".product");
            Elements products = doc.select(".product");
            Log.d("number of products", String.valueOf(products.size()));
            Log.d("number of products", "number of products " + String.valueOf(products.size()));



            for(Element product : products){
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                record += product.select(".name").text();
                record += ',';


                Elements discountedPrice = product.select(".discountedPrice");
                for(Element discountedValue : discountedPrice){
                    Elements insidePrice = discountedValue.getElementsByTag("span");
                    for(Element priceElement: insidePrice){

                        try {
                            String priceValue = String.valueOf(Integer.parseInt(priceElement.text()));
                            record += priceValue;
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        }
                    }
                }
                record += "\n";


            }


            Log.d("habijabi", record);
            //Log.d("productname", productname);

            String FILENAME = "product_name_file.csv";

            try{

                FileOutputStream out = openFileOutput(FILENAME, MODE_PRIVATE);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

}