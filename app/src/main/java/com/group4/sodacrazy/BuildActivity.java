package com.group4.sodacrazy;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class BuildActivity extends AppCompatActivity {

    // declare array adapters that will change the value of the
    // spinners in the activity
    private ArrayAdapter<CharSequence>bev_size_adapter;
    private int bev_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);

        bev_price = 0;

        Spinner beverage_type = (Spinner) findViewById(R.id.beverage_spinner);
        final Spinner bev_size = (Spinner) findViewById(R.id.bev_size_spinner);

        beverage_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                // switch of options that will determine the next spinner's options
                switch (pos) {
                    case  1: // Italian Ice
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.italian_ice_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  2: // The Craze
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.craze_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  3: // Shake It Up
                    case  4: // Crazy Ice Drinks
                    case  5: // Floats
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.shake_crazy_float_sizes, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  6: // Custard Shake
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.custard_shake_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  7: // Custard
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.custard_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  8: // Italian Soda
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.italian_drink_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    case  9: // Coffee
                    case 10: // Hot Chocolate
                    case 11: // Steamers
                    case 12: // Chi Tea
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.hot_drink_sizes, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        break;
                    default:
                        onNothingSelected(parentView);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}
