package com.group4.sodacrazy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GetFlavorsActivity parses json data into the Flavors class, makes a list of FlavorItems, and
 * changes the list of flavor names so that MainActivity can access it (if you change that, change this comment)
 * */
public class FlavorGetterFromPrefs extends AsyncTask<String, String, String> {

    //a list of flavor NAMES that we're going to alter. The changes will apply to the list in MainActivity, too
    private ArrayList<FlavorItem> flavors;

    private WeakReference<Context> context;
    private AsyncTaskListener listener;

    /**
     * non-default constructor allows the changeable flavors string to go back to MainActivity
     * */
    FlavorGetterFromPrefs(ArrayList<FlavorItem> flavors, Context context) {
        this.flavors = flavors;
        this.context = new WeakReference<>(context);
        listener = (AsyncTaskListener)context;
    }

    @Override
    protected String doInBackground(String... strings) {

        //open shared preferences
        SharedPreferences prefs = context.get().getSharedPreferences(
                "com.group4.sodacrazy.PREFERENCES", Context.MODE_PRIVATE);

        //put the information in a local string (I tried skipping this: didn't work out well
        String savedStuff = prefs.getString("flavors", "");

        //this should put this message if on the first ever use of the app there is no network
        //I haven't tested it.
        assert savedStuff != null;
        if (savedStuff.equals("")) {
            FlavorItem errMessage = new FlavorItem("Unable to display flavors; check your connection", "#000000");
            flavors.add(errMessage);
            return null;
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

        //populate the list of flavors
        for (ArrayList<String> i : list.values) {
            //the if statement lets us ignore empty flavor names
            if (!(i.get(0).equals(""))) {
                FlavorItem f = new FlavorItem(i.get(0), i.get(1));
                flavors.add(f); //append to the list
                //for (String s : i) {
                //     System.out.println(s);
                //}
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        listener.updateResult();
    }
}
