package com.example.peecee.bhopu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    Button ocr,speed,mag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ocr=view.findViewById(R.id.ocr);
        speed=view.findViewById(R.id.speed);
        mag=view.findViewById(R.id.mag);
        ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iocr=new Intent(getActivity(),Imag.class);
                startActivity(iocr);
            }
        });
        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagni=new Intent(getActivity(),Magnify.class);
                startActivity(imagni);
            }
        });
    }
}
