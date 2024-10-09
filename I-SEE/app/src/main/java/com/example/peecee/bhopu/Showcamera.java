package com.example.peecee.bhopu;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class Showcamera extends SurfaceView implements SurfaceHolder.Callback {
    static Camera c;
    SurfaceHolder holder;
    static Camera.Parameters params;

    public Showcamera(Context context, Camera camera) {
        super(context);
        this.c = camera;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        params = c.getParameters();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            params.set("orientation", "portrait");
            c.setDisplayOrientation(90);
            params.setRotation(90);

        } else {
            params.set("orientation", "landscape");
            c.setDisplayOrientation(0);
            params.setRotation(0);
        }
        params.setZoom(params.getMaxZoom());

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }


        c.setParameters(params);

        try {
            c.setPreviewDisplay(holder);
            c.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}