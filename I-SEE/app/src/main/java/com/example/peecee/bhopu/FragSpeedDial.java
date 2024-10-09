package com.example.peecee.bhopu;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class FragSpeedDial extends Fragment {
    SharedPreferences sharedPreferences;
    RecyclerView dialList;
    View view;
    DialListAdapter adapter;
    NavigationPojo navigationPojo;
    List<NavigationPojo>list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_frag_speed_dial,null);
        initView();
        return view;
    }

    private void initView() {
        dialList=(RecyclerView)view.findViewById(R.id.contactList);
        dialList.setLayoutManager(new LinearLayoutManager(getActivity()));


        SharedPreferences prefs = getActivity().getSharedPreferences("Key", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("data", "");

        List<NavigationPojo> httpParamList =
                new Gson().fromJson(httpParamJSONList, new TypeToken<List<NavigationPojo>>() {
                }.getType());



        for(int i=0;i<httpParamList.size();i++)
        {
            navigationPojo=new NavigationPojo();
            navigationPojo.setTitle(httpParamList.get(i).getTitle().toString());
            list.add(navigationPojo);
        }
        adapter=new DialListAdapter(getActivity(),list);
        dialList.setAdapter(adapter);
        dialList.setHasFixedSize(true);
    }


}


