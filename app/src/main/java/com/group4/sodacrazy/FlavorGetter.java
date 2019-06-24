package com.group4.sodacrazy;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GetFlavorsActivity parses json data into the Flavors class, makes a list of FlavorItems, and
 * changes the list of flavor names so that MainActivity can access it (if you change that, change this comment)
 * */
public class FlavorGetter implements Runnable {

    //a list of flavor NAMES that we're going to alter. The changes will apply to the list in MainActivity, too
    ArrayList<FlavorItem> flavors;
    Context context;
    SharedPreferences prefs;

    /**
     * non-default constructor allows the changeable flavors string to go back to MainActivity
     * */
    public FlavorGetter(ArrayList<FlavorItem> flavors, Context context) {
        this.flavors = flavors;
        this.context = context;
    }


    @Override
    public void run() {
        //the url we're requesting from
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1T6u5dMxYl_pfRkTOTCeA5HXFi1lVj-KvG3Y17wn_oHs/values/A2:B?key=AIzaSyCNoLqFA99dS-CtcOf-MFxA4xNhUPoMtDs";

        //create an input stream with Json Data in it
        InputStream response = null;
        try {
            response = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create a string of Json data to put in shared preferences
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //this is our string
        String jsonFlavors = stringBuilder.toString();

        //open shared preferences and add the string
        prefs = context.getSharedPreferences(
                "com.group4.sodacrazy.PREFERENCES", Context.MODE_PRIVATE);
        //we need to get this to work.
        prefs.edit().putString("flavors", jsonFlavors).apply();


        //reset the inputStream since we already used it to build the string
        response = new ByteArrayInputStream(jsonFlavors.getBytes());

        //scanner to parse data
        Scanner scanner = new Scanner(response);
        //this is what we're going to parse
        String responseBody = scanner.useDelimiter("\\A").next();

        //use gson
        Gson gson = new Gson();

        //calling this "list" is a little deceptive. It's not a list, but it has one
        Flavors list = gson.fromJson(responseBody, Flavors.class);

        //actually populate the list
        for (ArrayList<String> i : list.values) {
            //the if statement lets us ignore empty flavor names
            if (!(i.get(0).equals("")))
            {
                FlavorItem f = new FlavorItem(i.get(0), i.get(1));
                flavors.add(f); //append to the list
                //for (String s : i) {
                //     System.out.println(s);
                //}
            }
        }

    }
}
