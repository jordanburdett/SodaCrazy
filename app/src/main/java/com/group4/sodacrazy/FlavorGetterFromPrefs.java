package com.group4.sodacrazy;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
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
public class FlavorGetterFromPrefs implements Runnable {

    //a list of flavor NAMES that we're going to alter. The changes will apply to the list in MainActivity, too
    ArrayList<String> flavors;

    SharedPreferences prefs;
    Context context;

    /**
     * non-default constructor allows the changeable flavors string to go back to MainActivity
     * */
    public FlavorGetterFromPrefs(ArrayList<String> flavors, Context context) {
        this.flavors = flavors;
        this.context = context;
    }

    @Override
    public void run() {

        //open shared preferences
        prefs = context.getSharedPreferences(
                "com.group4.sodacrazy.PREFERENCES", Context.MODE_PRIVATE);

        //put the information in a local string (I tried skipping this: didn't work out well
        String savedStuff = prefs.getString("flavors", "");

        //this should put this message if on the first ever use of the app there is no network
        //I haven't tested it.
        if (savedStuff.equals("")) {
            flavors.add("Unable to display flavors; check your connection");
        }

        //create an input stream using our string of Json data
        InputStream response = new ByteArrayInputStream(savedStuff.getBytes());

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
