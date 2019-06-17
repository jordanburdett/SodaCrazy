package com.group4.sodacrazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        List<String> test = new ArrayList<String>();

        //Just testing the recycler view
        for (int i = 0; i < 100; i++) {
            String temp = "sodaCrazy " + i;
            test.add(temp);
        }

        adapter.setData(test);
        adapter.notifyDataSetChanged();
    }


    /**
     * This is for testing the punch card stuff. It can be changed to whatever menu thing we have.
     * Just make sure that whatever button is supposed to go to the punch card screen does this
     * method.
     * **/
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
