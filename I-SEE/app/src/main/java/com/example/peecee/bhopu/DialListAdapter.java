package com.example.peecee.bhopu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shree on 26/09/2018.
 */

public class DialListAdapter  extends RecyclerView.Adapter<DialListAdapter.ViewHolder>
{
    Context context;
    List<NavigationPojo> list=new ArrayList<>();

    public DialListAdapter(Context context, List<NavigationPojo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DialListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dial_row_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialListAdapter.ViewHolder holder, final int position) {
        holder.tv_contact.setText(list.get(position).getTitle());
        holder.tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeCall(position);



            }
        });
        holder.img_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(position);
            }
        });

    }

    private void makeCall(int position) {
        String contact=list.get(position).getTitle();
        String []number=contact.split("\n");

        Log.i("Make call", number[1]);
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:"+number[1].toString()));
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            context.startActivity(phoneIntent);

            Log.i("Finished making a call", "");
        } catch (Exception ex) {
            Toast.makeText(context, "Call faild, please try again later."+ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact;
        ImageView img_dial;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_contact=(TextView)itemView.findViewById(R.id.tv_contact);
            img_dial=(ImageView)itemView.findViewById(R.id.img_dial);
        }


    }
}
