package com.group4.sodacrazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
