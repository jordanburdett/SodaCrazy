package com.group4.sodacrazy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PunchCardActivity extends Activity {

    static final int ADD_PUNCH_REQUEST = 1;
    int punches;
    int redeemable;
    TextView txtView;   //number of punches left for a free italian ice
    TextView txtView2;  //number of redeemable italian ices
    ImageView imgView;
    public static final String SHARED_PREF = "com.group4.sodacrazy.PREFERENCES";
    public static final String TOTAL_PUNCHES = "TotalPunches";
    public static final String REDEEMABLE_ICE = "RedeemableIce";

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_card);

        prefs = this.getSharedPreferences(
                SHARED_PREF, Context.MODE_PRIVATE);
        punches = prefs.getInt(TOTAL_PUNCHES, 0);
        redeemable = prefs.getInt(REDEEMABLE_ICE, 0);

        txtView = findViewById(R.id.textView);      //punches remaining
        txtView2 = findViewById(R.id.textView2);    //redeemable ices

        String punchText = "Punches Remaining: " + (10 - (prefs.getInt(TOTAL_PUNCHES, 0)));
        String redeemText = "Free Italian Ices: " + prefs.getInt(REDEEMABLE_ICE, 0);
        txtView.setText(punchText);
        txtView2.setText(redeemText);

        imgView = findViewById(R.id.cupcake);

        //update the fill-up italian ice image
        setImage();

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
     * italian ices to remove (currently always 1). It will only call this activity if there
     * is at least one reward there to redeem
     */
    public void redeem(View view) {

        //don't go to the QR code screen if there's no italian ice to redeem. Instead, display error Toast
        if (redeemable <= 0) {
            //create Toast (YUM!)
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No Free Italian Ices Available :(", Toast.LENGTH_SHORT);
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
                            "Enjoy Your Free Italian Ice\nYou Earned It! :)", Toast.LENGTH_LONG);
                    TextView v = toast.getView().findViewById(android.R.id.message);
                    if( v != null) v.setGravity(Gravity.CENTER);
                    toast.show();
                }

                //this displays a countdown until a new coupon is available
                if (punches >= 10) {
                    punches -= 10;
                    redeemable++;
                }

                //update values in shared preferences
                prefs.edit().putInt(TOTAL_PUNCHES, punches).apply();
                prefs.edit().putInt(REDEEMABLE_ICE, redeemable).apply();

                //update numbers for the user

                String punchText = "Punches Remaining: " + (10 - (prefs.getInt(TOTAL_PUNCHES, 0)));
                String redeemText = "Free Italian Ices: " + prefs.getInt(REDEEMABLE_ICE, 0);
                txtView.setText(punchText);
                txtView2.setText(redeemText);

                //update the fill-up italian ice image
                setImage();
            }
        }
    }

    public void toBuild(View view) {
        Intent intent = new Intent(this, BuildActivity.class);
        startActivity(intent);
    }

    public void toMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


    /***
     * This will set the image based on how many punches/redeemable cards the user has
     * */
    public void setImage() {

        //if able to be redeemed, full image will appear
        if(redeemable >= 1) {
            imgView.setImageResource(R.drawable.ice1010);
        }

        //otherwise, it will be based on the number of punches (0-9, 0 is default)
        else {
            switch (punches)
            {
                case 1:
                    imgView.setImageResource(R.drawable.ice0110);
                    break;
                case 2:
                    imgView.setImageResource(R.drawable.ice0210);
                    break;
                case 3:
                    imgView.setImageResource(R.drawable.ice0310);
                    break;
                case 4:
                    imgView.setImageResource(R.drawable.ice0410);
                    break;
                case 5:
                    imgView.setImageResource(R.drawable.ice0510);
                    break;
                case 6:
                    imgView.setImageResource(R.drawable.ice0610);
                    break;
                case 7:
                    imgView.setImageResource(R.drawable.ice0710);
                    break;
                case 8:
                    imgView.setImageResource(R.drawable.ice0810);
                    break;
                case 9:
                    imgView.setImageResource(R.drawable.ice0910);
                    break;
                default: //if it's 0
                    imgView.setImageResource(R.drawable.ice0010);
            }
        }
    }
}
