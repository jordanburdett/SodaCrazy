package com.group4.sodacrazy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PunchCardActivity extends Activity {

    static final int ADD_PUNCH_REQUEST = 1;
    int punches;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_card);
        Intent intent = getIntent(); //this might do nothing. Not sure.

        //for TESTING, this is automatically put to 0. In real life, it will need to be a value
        //that is stored even when the app is closed.
        punches = 0;

    }

    /**
     * punchCard is called when the button to punch is pressed. It calls AddPunchActivity (the
     * barcode screen) with the intention of getting a result, which will be the number of
     * punches to add (right now always 1).
     */
    public void punchCard(View view) {
        boolean ready = false;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }

        // this loop ensures that the new activity won't start until the user has given permission to
        // use the camera. Unfortunately, it currently causes a freeze if the user clicks "Deny," so
        // we probably need a better solution for this
        while(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        }

        ready = true;
        if (ready) {
            Intent intent = new Intent(this, AddPunchActivity.class);
            // this enables us to get whatever information BACK that we want (it receives an intent
            // back)
            startActivityForResult(intent, ADD_PUNCH_REQUEST);
        }
    }

    /**
     * This is what the activity will do when it gets a result from a called activity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if the result is from the Add Punch activity...
        if (requestCode == ADD_PUNCH_REQUEST) {
            //RESULT_OK is passed back from the other activity
            if(resultCode == RESULT_OK) {
                txtView = findViewById(R.id.textView);
                punches += data.getIntExtra("Punch", 0);

                //this is temporary--just to show a countdown
                if (punches >= 10) {
                    punches -= 10;
                }
                txtView.setText("Punches Remaining: " + (10 - punches));
            }
        }
    }
}
