package com.example.peecee.bhopu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Camera;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.security.Policy;


public class Magnify extends AppCompatActivity {
    Camera camera;
    Camera.Parameters cp;
    FrameLayout framel;
    Showcamera sc;
    ToggleButton tb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnify);
        framel = findViewById(R.id.camm);

        tb = findViewById(R.id.flash);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flashlightOn();
                } else {
                    flashlightOff();
                }
            }
        });


        camera = Camera.open();
        sc = new Showcamera(this, camera);

        //  String s= camera.getParameters().getMaxZoom()+"";
        // Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        framel.addView(sc);
        //  cp= camera.getParameters();
    }

    public void flashlightOn() {
        Showcamera.params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        Showcamera.c.setParameters(Showcamera.params);
        Showcamera.c.startPreview();

    }

    public void flashlightOff() {
        Showcamera.params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        Showcamera.c.setParameters(Showcamera.params);
        Showcamera.c.startPreview();
    }
}