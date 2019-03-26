package com.group12.wifip2ptest;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Neeraj Athalye on 11-Mar-19.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    ArrayList<WifiP2pDevice> deviceList;
    Context context;

    public MyAdapter(ArrayList<WifiP2pDevice> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.nameTextView.setText(deviceList.get(i).deviceName);
        myViewHolder.macTextView.setText(deviceList.get(i).deviceAddress);

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}
