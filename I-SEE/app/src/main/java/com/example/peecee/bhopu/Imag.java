package com.example.peecee.bhopu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Imag extends AppCompatActivity {

    Button det, sp, cap, get;
    TextToSpeech tts;
    ImageView img;
    TextView t;
    private Bitmap imageBitmap;
    Uri outPutfileUri;
    Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imag);
        det = findViewById(R.id.detect);
        cap = findViewById(R.id.capture);
        sp = findViewById(R.id.speak);
        t = findViewById(R.id.read);
        get = findViewById(R.id.get);
        img = findViewById(R.id.captimage);
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }

        });

        det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectText();
            }
        });

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {
                                       openGallery();
                                   }
                               }
        );


    }

    private void dispatchTakePictureIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image = null;
        try {
            image = File.createTempFile("IMGG", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPutfileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", image);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                Drawable d = new BitmapDrawable(getResources(), imageBitmap);
                img.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            try {
                imageUri = data.getData();
                img.setImageURI(imageUri);
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                imageBitmap = drawable.getBitmap();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void detectText() {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(this, "No Text :(", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
            t.setTextSize(5);
            t.setText(txt);
            toSpeech();
        }

    }

    private void toSpeech() {

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Imag.this, "This Lang not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        tts.setPitch(0.6f);
                        tts.setSpeechRate(0.8f);
                        speak();

                    }
                }
            }
        });

    }

    private void speak() {
        String tospeak = t.getText().toString();
        tts.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null, null);

    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
        }
        super.onDestroy();
    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }



}
