package com.group4.sodacrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * MainActivity
 * <p>
 * This is the entry point to our application.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * When the app is first started we want to set up the recyclerView
     * Call the flavor api and display updated flavors.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        FlavorAdapter adapter;

        recyclerView = findViewById(R.id.FlavorView);
        recyclerView.setHasFixedSize(true);
        adapter = new FlavorAdapter(this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //this will be a list of flavor names (not colors at this time)
        ArrayList<FlavorItem> values = new ArrayList<>();

        //add the first one saying what this is
        FlavorItem item = new FlavorItem("Today's Italian Ice Flavors", "#ffffff");
        values.add(item);


        //check to see if connected to the internet
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            //this activity will call the google API when we do the thread
            FlavorGetter flavors = new FlavorGetter(values, this);
            //start the thread
            Thread thread = new Thread(flavors, "Get Flavors");
            thread.start();
            try {
                //this could have problems if internet connection is lost during the thread (or if there
                //is a problem connecting to the api) A better way to do it might be a start activity for result
                //(like how PunchCardActivity starts AddPunchActivity)
                //except it's not an activity... so not sure if that would work
                thread.join(); //this is a waiting thing that may or may not be smart to use
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //this activity will call the google API when we do the thread
            FlavorGetterFromPrefs flavors = new FlavorGetterFromPrefs(values, this);
            //start the thread
            Thread thread2 = new Thread(flavors, "Get Saved Flavors");
            thread2.start();
            try {
                thread2.join(); //this is a waiting thing that may or may not be smart to use
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Unable to Update Flavors", Toast.LENGTH_LONG);
            toast.show();
        }

        //Tell the adapter what to display
        adapter.setData(values);

        //Tell the adapter to update the View
        adapter.notifyDataSetChanged();
    }

    /**
     * toPunch
     *
     * Starts the punchCardActivity
     * @param view to allow the onclick in xml
     */
    public void toPunch(View view) {
        Intent intent = new Intent(this, PunchCardActivity.class);
        startActivity(intent);
    }

    /**
     * toBuild
     *
     * Starts the BuildActivity
     * @param view to allow the onclick in xml
     */
    public void toBuild(View view) {
        Intent intent = new Intent(this, BuildActivity.class);
        startActivity(intent);
    }

    /**
     * toMenu
     *
     * Starts the MenuActivity
     * @param view to allow the onclick in xml
     */
    public void toMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
