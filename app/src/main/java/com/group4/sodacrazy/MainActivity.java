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

public class MainActivity extends AppCompatActivity implements AsyncTaskListener {

    private FlavorAdapter adapter;
    List<FlavorItem> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.FlavorView);
        recyclerView.setHasFixedSize(true);
        adapter = new FlavorAdapter(this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        //this will be a list of flavor names (not colors at this time)
        values = new ArrayList<>();

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
            flavors.execute();
        }
        else {
            //this activity will call the google API when we do the thread
            FlavorGetterFromPrefs flavors = new FlavorGetterFromPrefs((ArrayList<FlavorItem>) values, this);
            //start the thread
            flavors.execute();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Unable to Update Flavors", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    /*
    * These functions are for the menu bar
    * */
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

    public void toHome(View view) {
        //do nothing
    }

    @Override
    public void updateResult() {
        //this is putting the flavor names into the recyclerView. No colors right now (they're in the FlavorItem though)
        adapter.setData(values);
        adapter.notifyDataSetChanged();
    }
}
