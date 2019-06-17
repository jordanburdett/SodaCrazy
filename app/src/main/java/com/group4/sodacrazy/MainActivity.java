package com.group4.sodacrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        List<String> values = new ArrayList<>();

        //this activity will call the google API when we do the thread
        FlavorGetter flavors = new FlavorGetter((ArrayList<String>)values);

        //start the thread
        Thread thread = new Thread(flavors, "Get Flavors");
        thread.start();
        try {
            thread.join(); //this is a waiting thing that may or may not be smart to use
        } catch (InterruptedException e) {
            e.printStackTrace();
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
