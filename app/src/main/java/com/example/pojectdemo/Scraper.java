package com.example.pojectdemo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class Scraper extends Activity {

    String productname = "";
    String FILENAME = "chicken-data.csv";
    File chicken;
    Document doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setVisible(false);

        File root = new File(getFilesDir()+FILENAME);
        if(!root.exists()){
            root.mkdirs();
        }
        chicken = new File(root, FILENAME);

    }



    public void scrape(Context context) {
        Content content = new Content(context);
        content.execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {
        Context ContentContext;
        Content(Context context){
            ContentContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        protected Void doInBackground(Void... voids) {

            //scraping data for chicken

            //scraping from chaldal
            String record = "";
            String url = "";
            try {
                url = "https://chaldal.com/chicken";
                doc = Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements products = doc.select(".product");
            Log.d("number of products", String.valueOf(products.size()));
            Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                record += url + " ";
                record += product.select(".name").text();
                record += ',';


                Elements discountedPrice = product.select(".discountedPrice");
                for (Element discountedValue : discountedPrice) {
                    Elements insidePrice = discountedValue.getElementsByTag("span");
                    for (Element priceElement : insidePrice) {

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

            String FILENAME = "chicken-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_PRIVATE);
                Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                Log.d("file not create", "file not created");
                Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=chicken&c=822&minprice=NaN&maxprice=NaN"; //othoba
                doc = Jsoup.connect(url).get();
                Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            Elements name = doc.select(".product-title");
            Elements price = doc.select(".price.actual-price");
            Log.d("name size", "name size " + name.size() );
            Log.d("price size", "price size " + price.size());

            int n = name.size();
            for(int i=0; i<n; i++){
                record += url;
                record += " ";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(3);
                record += '\n';
            }


            Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_APPEND);
                Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                Log.d("file not create", "file not created");
                Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            return null;
        }
    }

}
