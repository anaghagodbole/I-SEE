package com.example.peecee.bhopu;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.shree.mycontact.ContactListAdapter;
import com.example.shree.mycontact.NavigationPojo;
import com.example.shree.mycontact.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragMyContact extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    View view;
    RecyclerView listContact;
    RecyclerView.LayoutManager layoutManager;
    ContactListAdapter adapter;
    Button btn_add;
    private static final int CONTACTS_LOADER_ID=2;
    List<NavigationPojo>navigationPojoList=new ArrayList<>();
    List<String> list;
    List<NavigationPojo>checkedList;
    SharedPreferences sharedpreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_frag_my_contact, null);
        // Inflate the layout for this fragment

        initView();
        return view ;


    }

    private void initView()
    {

        listContact=(RecyclerView)view.findViewById(R.id.contactList);
        btn_add=(Button) view.findViewById(R.id.btn_add);

        listContact.setLayoutManager(new LinearLayoutManager(getActivity()));
        listContact.setHasFixedSize(true);
        // sharedData();
        getLoaderManager().initLoader(CONTACTS_LOADER_ID,
                null,
                this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList=new ArrayList<>();
                checkedList=adapter.retreveList();


                try {
                    String httpParamJSONList = new Gson().toJson(checkedList);

                    sharedpreferences = getActivity().getSharedPreferences("Key", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor = sharedpreferences.edit();
                    editor.putString("data", httpParamJSONList);

                    editor.apply();
                } catch (Exception e) {

                }
                try {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    FragSpeedDial llf = new FragSpeedDial();
                    ft.replace(R.id.container, llf);

                    ft.commit();
                }
                catch (Exception e)
                {

                }

            }
        });


    }

    public List<NavigationPojo> sharedData() {
        try {
            SharedPreferences prefs = getActivity().getSharedPreferences("Key", Context.MODE_PRIVATE);
            String httpParamJSONList = prefs.getString("data", "");
            List<NavigationPojo> httpParamList =
                    new Gson().fromJson(httpParamJSONList, new TypeToken<List<NavigationPojo>>() {
                    }.getType());
            for (int i = 0; i < navigationPojoList.size(); i++) {
                navigationPojoList.get(i).setChecked(false);

                for (int j = 0; j < httpParamList.size(); j++) {
                    if (navigationPojoList.get(i).getTitle().equals(httpParamList.get(j).getTitle())) {
                        navigationPojoList.get(i).setChecked(true);
                    }

                }

            }
        }
        catch(Exception e)
        {

        }
        return navigationPojoList;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
// This is called when a new Loader needs to be created.

        if (id == CONTACTS_LOADER_ID) {
            return contactsLoader();
        }
        return null;    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //The framework will take care of closing the
        // old cursor once we return.
        list=contactsFromCursor(data);
        Collections.sort(list);

        for(int i=0;i<list.size();i++)
        {
            NavigationPojo navigationPojo=new NavigationPojo();
            navigationPojo.setTitle(list.get(i));
            navigationPojoList.add(navigationPojo);

        }
        sharedData();
        adapter=new ContactListAdapter(navigationPojoList,getActivity());

        listContact.setAdapter(adapter );
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.

    }

    private Loader<Cursor> contactsLoader() {
        Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // The content URI of the phone contacts


        String[] projection = {                                  // The columns to return for each row
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER

        } ;

        String selection = null;                                 //Selection criteria
        String[] selectionArgs = {};                             //Selection criteria
        String sortOrder = null;                                 //The sort order for the returned rows

        return new CursorLoader(
                getActivity(),
                contactsUri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }


    private List<String> contactsFromCursor(Cursor cursor) {
        List<String> contacts = new ArrayList<String>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                try {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNo = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    contacts.add(name+"\n"+phoneNo );
                }
                catch (Exception e)
                {

                }
            } while (cursor.moveToNext());
        }

        return contacts;
    }





}
