package com.group4.sodacrazy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class AddPunchActivity extends AppCompatActivity {

    SurfaceView surfaceView; //where the camera will show up
    CameraSource cameraSource; //this lets us use the camera on a very basic level (which is all we need)
    TextView txtView; //display text (eventually might have instructions or something)
    BarcodeDetector detector; //so we can read barcodes. This is part of the google play services thing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_punch);

        Intent intent = getIntent(); //I don't know if this does anything for us

        surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        txtView = (TextView) findViewById(R.id.txtview);

        //make a detector that will look for qr codes
        detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        //give details about what we want from our camera source
        cameraSource = new CameraSource.Builder(this, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();

        //create our SurfaceView
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //if we don't have permission, we can't do anything. This will show a black screen
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    //access the camera
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //extra functions just have to be overridden, so they can't be deleted. They don't do anything.
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //stop the camera
                cameraSource.stop();

            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //extra functions just have to be overridden, so they can't be deleted. They don't do anything.
            }

            //react to the qr codes we see
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                //if we have any qr code(s) on the screen...
                if (qrCodes.size() != 0) {
                    txtView.post(new Runnable() {
                        @Override
                        public void run() { //Ok. I DO NOT want to do anything with a textview. I'll fix this later.
                            txtView.setText(qrCodes.valueAt(0).rawValue); // yeah this will def change
                            //if the 1st (usually only) qr code on the screen matches the secret code...
                            if (qrCodes.valueAt(0).rawValue.equals("soda34234298432075892780324932849038249032890483290crazy")) {
                                //vibrate a bit to let the user know it worked
                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(100);
                                //stop the camera source (this might not be necessary)
                                cameraSource.stop();

                                //give information back to the activity that called this one
                                Intent output = new Intent();
                                output.putExtra("Punch", 1); //right now this will always be 1
                                setResult(RESULT_OK, output); //say things went well, pass back the intent

                                //exit this activity (and return to the one that called it)
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}
