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

        File root = new File(getFilesDir() + FILENAME);
        if (!root.exists()) {
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

        Content(Context context) {
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
            //scraping data for beef

            //scraping from chaldal
            String record = "";
            String url = "";
            try {
                url = "https://chaldal.com/meat-new";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Beef") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            String FILENAME = "beef-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=beef&c=771&minprice=NaN&maxprice=NaN"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            Elements name = doc.select(".product-title");
            Elements price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            int n = name.size();
            for (int i = 0; i < n; i++) {
                String productname = name.get(i).text();
                if (productname.indexOf("Beef") != -1) {
                    record += url;
                    record += " ,";
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(3);
                    record += '\n';
                }

            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=beef&adv=true&cid=1&mid=0&pf=&pt=&adv=false&isc=false&sid=false#/pageSize=6&orderBy=0"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            Elements productTitle = doc.select(".product-title");
            String[] nameString;
            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            //scraping data for chicken

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/chicken-poultry";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Chicken") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "chicken-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=chicken&c=822&minprice=NaN&maxprice=NaN"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(3);
                record += '\n';
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=chicken&adv=true&cid=1&mid=0&pf=&pt=&adv=false&isc=false&sid=false#/pageSize=6&orderBy=0"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //scraping data for mutton

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/meat-new";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Mutton") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    Log.d("mutton chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    //Log.d("mutton price size", discountedPrice.size()+"");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replace(",", "")));
                                    Log.d("price value mutton", priceValue + "\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    Log.d("price value mutton", priceValue + "\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "mutton-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }
            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/meat"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                String productname = name.get(i).text();
                if (productname.indexOf("Mutton") != -1) {
                    record += url;
                    record += " ,";
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    //record += price_element_text.substring(3);
                    record += price_element_text;
                    record += '\n';
                }

            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=Mutton&adv=true&cid=1&mid=0&pf=&pt=&adv=false&isc=false&sid=false"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");
            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                price_element_text = price_element_text.substring(0, price_element_text.length() - 1);
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            //scraping data for egg
            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/eggs";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Egg") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "egg-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=eggs&c=713&minprice=NaN&maxprice=NaN"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(3);
                record += '\n';
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar: chicken egg
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=chicken%20egg&adv=true&cid=0&mid=0&pf=&pt=&adv=false&isc=false&sid=false"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //sobjibazaar: duck egg
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=duck%20egg&adv=true&cid=0&isc=true&mid=0&pf=&pt=&adv=false&isc=false&sid=false"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            //scraping data for fish

            //scraping from chaldal: frozen fish
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/frozen-fish";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Fish") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "fish-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //dried fish
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/dried-fish";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Fish") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "fish-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                //url = "https://www.othoba.com/src?q=eggs&c=713&minprice=NaN&maxprice=NaN"; //othoba
                url = "https://www.othoba.com/fish";
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            Log.d("othoba name size", "name size " + name.size());
            Log.d("othoba price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                Log.d("othoba fish price", price_element_text);
                //record += price_element_text.substring(3);
                record += price_element_text;
                record += '\n';
            }


            Log.d("othoba fish", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //sobjibazaar fish
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/fish#/pageSize=20&orderBy=0"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            FILENAME = "fish-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                //url = "https://www.othoba.com/src?q=eggs&c=713&minprice=NaN&maxprice=NaN"; //othoba
                url = "https://www.othoba.com/fish";
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            Log.d("othoba name size", "name size " + name.size());
            Log.d("othoba price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                Log.d("othoba fish price", price_element_text);
                //record += price_element_text.substring(3);
                record += price_element_text;
                record += '\n';
            }


            Log.d("othoba fish", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //sobjibazaar fish
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/fish#/pageSize=20&orderBy=0"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //scraping data for toothpaste

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/toothpastes";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Chicken") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "toothpaste-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=toothpaste"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";
                //adding name
                record += name.get(i).text();
                record += ",";

                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(3);
                record += '\n';
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=toothpaste#/pageSize=6&orderBy=0"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";
                record += url;
                record += " ,";
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    record += name_value;
                    record += ",";
                }


                //adding price
                String price_element_text = price.get(i).text();
                record += price_element_text.substring(0, price_element_text.length() - 1);
                record += '\n';
                nameString[i] = name_value;
                i++;
            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //scraping data for soap

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/search/soap";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if (productname.indexOf("Soap") != -1) {
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if (discountedPrice.size() == 0) {
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    } else {
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",", "")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "soap-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=soap"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for (int i = 0; i < n; i++) {
                record += url;
                record += " ,";

                if (name.get(i).text().indexOf("Handwash") == -1) {
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(3);
                    record += '\n';
                }

            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=soap&cid=0&mid=0&pf=&pt=&adv=false&isc=false&sid=false#/pageSize=6&orderBy=0"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for (Element element : productTitle) {
                int i = 0;
                String name_value = "";


                Boolean soap = false;
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    if (name_value.indexOf("Handwash") == -1) {
                        record += url;
                        record += " ,";
                        record += name_value;
                        record += ",";
                        soap = true;
                    }

                }

                if (soap) {
                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(0, price_element_text.length() - 1);
                    record += '\n';
                    nameString[i] = name_value;
                    i++;
                }

            }

            //Log.d("habijabi", record);

            try {

                FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            //scraping data for shampoo

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/search/shampoo";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if(productname.indexOf("Shampoo") != -1){
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if(discountedPrice.size() == 0){
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                    else{
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "shampoo-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=shampoo"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for(int i=0; i<n; i++){
                record += url;
                record += " ,";
                if(name.get(i).text().indexOf("Shampoo") != -1){
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(3);
                    record += '\n';
                }
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=soap&cid=0&mid=0&pf=&pt=&adv=false&isc=false&sid=false#/pageSize=6&orderBy=0"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for(Element element:productTitle) {
                int i = 0;
                String name_value = "";


                Boolean shampoo = false;
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    if (name_value.indexOf("Shampoo") != -1) {
                        record += url;
                        record += " ,";
                        record += name_value;
                        record += ",";
                        shampoo = true;
                    }

                }

                if (shampoo) {
                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(0, price_element_text.length() - 1);
                    record += '\n';
                    nameString[i] = name_value;
                    i++;
                }

                //Log.d("habijabi", record);

                try {

                    FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                    out.write(record.getBytes(StandardCharsets.UTF_8));
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            //scraping data for

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/search/handwash";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if(productname.indexOf("Handwash") != -1){
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if(discountedPrice.size() == 0){
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                    else{
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "handwash-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src?q=handwash"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for(int i=0; i<n; i++){
                record += url;
                record += " ,";
                if(name.get(i).text().indexOf("Handwash") != -1){
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(3);
                    record += '\n';
                }
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=handwash&adv=true&cid=0&mid=0&pf=&pt=&adv=false&isc=false&sid=false"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for(Element element:productTitle) {
                int i = 0;
                String name_value = "";


                Boolean handwash = false;
                Elements a = element.getElementsByTag("a");
                for (Element a_element : a) {
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    if (name_value.indexOf("Handwash") != -1) {
                        record += url;
                        record += " ,";
                        record += name_value;
                        record += ",";
                        handwash = true;
                    }

                }

                if (handwash) {
                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(0, price_element_text.length() - 1);
                    record += '\n';
                    nameString[i] = name_value;
                    i++;
                }

                //Log.d("habijabi", record);

                try {

                    FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                    out.write(record.getBytes(StandardCharsets.UTF_8));
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //scraping data for

            //scraping from chaldal
            record = "";
            url = "";
            try {
                url = "https://chaldal.com/search/sanitizer";
                doc = Jsoup.connect(url).get();
                //Log.d("lkj", "beef scraped");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".product");
            //Log.d("number of products", String.valueOf(products.size()));
            //Log.d("number of products", "number of products " + String.valueOf(products.size()));


            for (Element product : products) {
                //String name = product.select(".name").text();
                //productname += name;
                //productname += ',';
                String productname = product.select(".name").text();
                //Log.d("lfjs",productname.indexOf("Beef")+"");
                if(productname.indexOf("Sanitizer") != -1){
                    record += url + " ,";
                    record += product.select(".name").text();
                    record += ',';

                    //Log.d("beef chaldal", record);
                    Elements discountedPrice = product.select(".discountedPrice");
                    if(discountedPrice.size() == 0){
                        discountedPrice = product.select(".price");
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                    else{
                        for (Element discountedValue : discountedPrice) {
                            Elements insidePrice = discountedValue.getElementsByTag("span");
                            for (Element priceElement : insidePrice) {

                                try {
                                    String priceValue = String.valueOf(Integer.parseInt(priceElement.text().replaceAll(",","")));
                                    //Log.d("price value beef", priceValue+"\n");
                                    record += priceValue;
                                } catch (NumberFormatException nfe) {
                                    nfe.printStackTrace();
                                }
                            }
                            record += "\n";
                        }
                    }
                }
            }


            //Log.d("habijabi", record);
            //Log.d("productname", productname);

            FILENAME = "sanitizer-data.csv";

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_PRIVATE);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }


            //scraping data from othoba.com
            record = "";
            url = "";

            try {
                url = "https://www.othoba.com/src/sanitizers-disinfectants?q=sanitizer"; //othoba
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            products = doc.select(".details"); //othoba

            //adding name and prices of othoba
            name = doc.select(".product-title");
            price = doc.select(".price.actual-price");
            //Log.d("name size", "name size " + name.size() );
            //Log.d("price size", "price size " + price.size());

            n = name.size();
            for(int i=0; i<n; i++){

                if(name.get(i).text().indexOf("Sanitizer") != -1){
                    record += url;
                    record += " ,";
                    //adding name
                    record += name.get(i).text();
                    record += ",";

                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(3);
                    record += '\n';
                }
            }


            //Log.d("habijabi", record);

            try {
                FileOutputStream out = ContentContext.openFileOutput(FILENAME,MODE_APPEND);
                //Log.d("file created", "file created");
                out.write(record.getBytes(StandardCharsets.UTF_8));
                out.close();

            } catch (Exception e) {
                //Log.d("file not create", "file not created");
                //Log.d("file not create", e.toString());
                e.printStackTrace();
            }

            //scraping data from sobjibazaar
            record = "";
            url = "";

            try {
                url = "https://sobjibazaar.com/filterSearch?q=sanitizer&adv=true&cid=0&mid=0&pf=&pt=&adv=false&isc=false&sid=false#/pageSize=6&orderBy=0"; //sobjibazaar
                doc = Jsoup.connect(url).get();
                //Log.d("scraped successfully", "sobijbazaar scraped successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding name and prices of sobjibazaar
            productTitle = doc.select(".product-title");

            price = doc.select(".price.actual-price");

            n = price.size();
            nameString = new String[n];//contains names of the products of sobijbazaar

            //retrieving names from product-title
            for(Element element:productTitle){
                int i=0;
                String name_value = "";


                Boolean sanitizer = false;
                Elements a = element.getElementsByTag("a");
                for(Element a_element: a){
                    name_value = a.text();
                    //Log.d("sflsk", "sobjibazaar product " + name_value);
                    if(name_value.indexOf("Sanitizer") != -1){
                        record += url;
                        record += " ,";
                        record += name_value;
                        record += ",";
                        sanitizer = true;
                    }

                }

                if(sanitizer){
                    //adding price
                    String price_element_text = price.get(i).text();
                    record += price_element_text.substring(0, price_element_text.length()-1);
                    record += '\n';
                    nameString[i] = name_value;
                    i++;
                }

                //Log.d("habijabi", record);

                try{

                    FileOutputStream out = ContentContext.openFileOutput(FILENAME, MODE_APPEND);
                    out.write(record.getBytes(StandardCharsets.UTF_8));
                    out.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            return null;
        }
    }
}