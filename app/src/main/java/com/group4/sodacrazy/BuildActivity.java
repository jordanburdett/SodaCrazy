package com.group4.sodacrazy;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class BuildActivity extends AppCompatActivity {

    // declare array adapters that will change the value of the
    // spinners in the activity
    private ArrayAdapter<String>beverage_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
    }

    Spinner beverage_type = (Spinner) findViewById(R.id.beverage_spinner);

    beverage_type.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // your code here
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }

    });
}
