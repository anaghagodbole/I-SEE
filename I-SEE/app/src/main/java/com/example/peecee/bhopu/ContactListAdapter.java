package com.example.peecee.bhopu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shree on 25/09/2018.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    List<NavigationPojo> list = new ArrayList<>();
    List<NavigationPojo> checkedList=new ArrayList<>();
    Context context;
    NavigationPojo navigationPojo;

    public ContactListAdapter(List<NavigationPojo> navigationPojoList, Context context) {
        this.list = navigationPojoList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_contact.setText(list.get(position).getTitle());
        if (list.get(position).isChecked) {
            holder.chBox.setChecked(true);

        } else {
            holder.chBox.setChecked(false);
        }

        holder.tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationPojo = list.get(position);

                if (!holder.chBox.isChecked()) {
                    holder.chBox.setChecked(true);
                    list.get(position).isChecked = true;
                    addList(position);


                } else {

                    holder.chBox.setChecked(false);
                    list.get(position).isChecked = false;
                    removeData(position);

                }
            }

        });

        holder.chBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigationPojo = list.get(position);
                if (holder.chBox.isChecked()) {
                    holder.chBox.setChecked(false);
                } else {
                    holder.chBox.setChecked(true);
                }
            }
        });


    }

    private void removeData(int position)
    {
        navigationPojo=new NavigationPojo();
        try {

            if (checkedList.size() != 0 || !checkedList.isEmpty()) {
                if (!list.get(position).isChecked) ;
                {
                    checkedList.remove(navigationPojo);
                }
            }
        }
        catch (Exception e)
        {

        }
    }


    public List<NavigationPojo> addList(int postion) {
        SharedPreferences sharedpreferences = null;



        if (list.get(postion).isChecked) {
            checkedList.add(navigationPojo);

            try {
                String httpParamJSONList = new Gson().toJson(checkedList);

                sharedpreferences = context.getSharedPreferences("Key", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor = sharedpreferences.edit();
                editor.putString("data", httpParamJSONList);

                editor.apply();
            } catch (Exception e) {

            }


        }
        return checkedList;

    }


    public List<NavigationPojo> retreveList()
    {
        checkedList=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).isChecked)
            {
                checkedList.add(list.get(i));
            }
        }
        return checkedList;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact;
        CheckBox chBox;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_contact=(TextView)itemView.findViewById(R.id.tv_contact);
            chBox=(CheckBox)itemView.findViewById(R.id.chBox);
        }
    }
}

