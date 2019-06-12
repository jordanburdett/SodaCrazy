package com.group4.sodacrazy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PunchCardActivity extends Activity {

    static final int ADD_PUNCH_REQUEST = 1;
    int punches;
    int redeemable;
    TextView txtView; //number of punches left for a free drink
    TextView txtView2; //number of redeemable drinks

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_card);
        Intent intent = getIntent(); //this might do nothing. Not sure.

        prefs = this.getSharedPreferences(
                "com.group4.sodacrazy.PREFERENCES", Context.MODE_PRIVATE);
        punches = prefs.getInt("TotalPunches", 0);
        redeemable = prefs.getInt("RedeemableDrinks", 0);

        txtView = findViewById(R.id.textView); //punches remaining
        txtView2 = findViewById(R.id.textView2); //redeemable drinks

        txtView.setText("Punches Remaining: " + (10 - (prefs.getInt("TotalPunches", 0))));
        txtView2.setText("Redeemable Drinks: " + prefs.getInt("RedeemableDrinks", 0));

        //ask for permissions if not already granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }

    }


    /**
     * redeem() is called when the redeem button is pressed. It calls AddPunchActivity (the
     * barcode screen) with the intention of getting a result, which will be the number of
     * drinks to remove (currently always 1). It will only call this activity if there
     * is at least one reward there to redeem
     */
    public void redeem(View view) {

        //don't go to the QR code screen if there's no drink to redeem. Instead, display error Toast
        if (redeemable <= 0) {
            //create Toast (YUM!)
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No Free Drinks Available :(", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            String r = "redeem";
            Intent intent = new Intent(this, AddPunchActivity.class);
            intent.putExtra("purpose", r);
            // this enables us to get whatever information BACK that we want (it receives an intent
            // back)
            startActivityForResult(intent, ADD_PUNCH_REQUEST);
        }
    }


    /**
     * punchCard is called when the button to punch is pressed. It calls AddPunchActivity (the
     * barcode screen) with the intention of getting a result, which will be the number of
     * punches to add (right now always 1).
     */
    public void punchCard(View view) {
        String p = "punch";
        Intent intent = new Intent(this, AddPunchActivity.class);
        intent.putExtra("purpose", p);
        // this enables us to get whatever information BACK that we want (it receives an intent
        // back)
        startActivityForResult(intent, ADD_PUNCH_REQUEST);
    }

    /**
     * This is what the activity will do when it gets a result from a the addPunch activity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if the result is from the Add Punch activity...
        if (requestCode == ADD_PUNCH_REQUEST) {
            //RESULT_OK is passed back from the other activity
            if(resultCode == RESULT_OK) {
                //get information that was passed back from the activity
                punches += data.getIntExtra("Punch", 0);
                redeemable -= data.getIntExtra("Redeem", 0);

                //show user that punch was successful
                if (data.getIntExtra("Punch", 0) > 0) {
                    //create Toast (YUM!)
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Punch Added to Virtual Card! :)", Toast.LENGTH_LONG);
                    toast.show();
                }
                //show user that redeeming their card worked
                if (data.getIntExtra("Redeem", 0) > 0) {
                    //create Toast (YUM!)
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enjoy Your Free Drink! :)", Toast.LENGTH_LONG);
                    toast.show();
                }

                //this displays a countdown until a new coupon is available
                if (punches >= 10) {
                    punches -= 10;
                    redeemable++;
                }

                //update values in shared preferences
                prefs.edit().putInt("TotalPunches", punches).apply();
                prefs.edit().putInt("RedeemableDrinks", redeemable).apply();

                //update numbers for the user
                txtView.setText("Punches Remaining: " + (10 - (prefs.getInt("TotalPunches", 0))));
                txtView2.setText("Redeemable Drinks: " + prefs.getInt("RedeemableDrinks", 0));

            }
        }
    }
}
