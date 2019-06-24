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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FlavorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.FlavorView);
        recyclerView.setHasFixedSize(true);
        adapter = new FlavorAdapter(this);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        //this will be a list of flavor names (not colors at this time)
        List<FlavorItem> values = new ArrayList<FlavorItem>();

        //add the first one saying what this is
        FlavorItem item = new FlavorItem("Today's Italian Ice Flavors", "#ffffff");
        values.add(item);


        //check to see if connected to the internet
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            //this activity will call the google API when we do the thread
            FlavorGetter flavors = new FlavorGetter((ArrayList<FlavorItem>) values, this);
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
        }
        else {
            //this activity will call the google API when we do the thread
            FlavorGetterFromPrefs flavors = new FlavorGetterFromPrefs((ArrayList<FlavorItem>) values, this);
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

        //this is putting the flavor names into the recyclerView. No colors right now (they're in the FlavorItem though)
        adapter.setData(values);
        adapter.notifyDataSetChanged();
    }


    /**
     * This is for testing the punch card stuff. It can be changed to whatever menu thing we have.
     * Just make sure that whatever button is supposed to go to the punch card screen does this
     * method.
     **/
    public void toPunch(View view) {
        Intent intent = new Intent(this, PunchCardActivity.class);
        startActivity(intent);
    }

    public void toBuild(View view) {
        Intent intent = new Intent(this, BuildActivity.class);
        startActivity(intent);
    }

    public void toMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
