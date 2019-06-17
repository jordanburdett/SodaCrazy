package com.group4.sodacrazy;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GetFlavorsActivity parses json data into the Flavors class, makes a list of FlavorItems, and
 * changes the list of flavor names so that MainActivity can access it (if you change that, change this comment)
 * */
public class FlavorGetter implements Runnable {

    //a list of flavor NAMES that we're going to alter. The changes will apply to the list in MainActivity, too
    ArrayList<String> flavors;

    /**
     * non-default constructor allows the changeable flavors string to go back to MainActivity
     * */
    public FlavorGetter(ArrayList<String> flavors) {
        this.flavors = flavors;
    }


    @Override
    public void run() {
        //the url we're requesting from
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1T6u5dMxYl_pfRkTOTCeA5HXFi1lVj-KvG3Y17wn_oHs/values/A2:B?key=AIzaSyCNoLqFA99dS-CtcOf-MFxA4xNhUPoMtDs";

        //create an input stream
        InputStream response = null;
        try {
            response = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //scanner to parse data
        Scanner scanner = new Scanner(response);
        //this is what we're going to parse
        String responseBody = scanner.useDelimiter("\\A").next();

        //use gson
        Gson gson = new Gson();

        //calling this "list" is a little deceptive. It's not a list, but it has one
        Flavors list = gson.fromJson(responseBody, Flavors.class);

        //making a list of individual flavors (which contain a name and color)
        List<FlavorItem> allFlavors = new ArrayList<FlavorItem>();

        //actually populate the list
        for (ArrayList<String> i : list.values) {
            //the if statement lets us ignore empty flavor names
            if (!(i.get(0).equals("")))
            {
                FlavorItem f = new FlavorItem();
                f.name = i.get(0);
                f.color = i.get(1);
                allFlavors.add(f); //append to the list
                //for (String s : i) {
                //     System.out.println(s);
                //}
            }
        }

        //we're now adding all the flavor names to the string that was passed in as a parameter
        //this is how we get them back to MainActivity
        for (FlavorItem f : allFlavors) {
            flavors.add(f.name);
        }
    }
}
