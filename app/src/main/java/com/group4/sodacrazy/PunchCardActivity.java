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
        Intent intent = getIntent();
        punches = 0;

    }

    public void punchCard(View view) {
        boolean ready = false;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }

        while(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        }
        ready = true;
        if (ready) {
            Intent intent = new Intent(this, AddPunchActivity.class);
            startActivityForResult(intent, ADD_PUNCH_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PUNCH_REQUEST) {
            if(resultCode == RESULT_OK) {
                txtView = findViewById(R.id.textView);
                punches += data.getIntExtra("Punch", 0);
                if (punches >= 10) {
                    punches -= 10;
                }
                txtView.setText("Punches Remaining: " + (10 - punches));
            }
        }
    }
}
