package com.group4.sodacrazy;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onNothingSelected(AdapterView<?> parent) {
        // doing nothing?
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // switch of options that will determine the next spinner's options
        switch (pos) {
            case 1: // Italian Ice

            case 2: // The Craze

            case 3: // Shake It Up

            case 4: // Crazy Ice Drinks

            case 5: // Custard Shake

            case 6: // Custard

            case 7: // Floats

            case 8: // Italian Soda

            case 9: // Coffee or Espresso

            case 10: // Hot Chocolate

            case 11: // Steamers

            case 12: // Chi Tea

        }
    }
}
