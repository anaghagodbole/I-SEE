package com.example.peecee.bhopu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrefBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_back);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new AddContact()).commit();
    }
}
