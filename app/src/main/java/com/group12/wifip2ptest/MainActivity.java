package com.group12.wifip2ptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WifiP2pManager.ActionListener, WifiP2pManager.PeerListListener {

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter intentFilter;

    Button discoverPeersButton;
    RecyclerView recyclerView;
    String originalDeviceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        discoverPeersButton = findViewById(R.id.discoverPeersButton);
        discoverPeersButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);

        Toast.makeText(this, "DEVICE: " + getOriginalDeviceName(), Toast.LENGTH_SHORT).show();

//        String originalDeviceName =
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Peer discovery successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int reason) {
        Toast.makeText(this, "Peer discovery failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Starting discovery of peers", Toast.LENGTH_SHORT).show();
        mManager.discoverPeers(mChannel, this);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        Toast.makeText(this, "INSIDE ON PEERS AVAILABLE", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Peer list size: " + peers.getDeviceList().size(), Toast.LENGTH_SHORT).show();


        ArrayList<WifiP2pDevice> devices = new ArrayList<>(peers.getDeviceList());
//        ArrayList<WifiP2pDevice> devices = peers.getDeviceList();
        for (WifiP2pDevice device: devices)
            if(!device.deviceName.contains(":JS:"))
                devices.remove(device);


            MyAdapter myAdapter = new MyAdapter(devices, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    public void setOriginalDeviceName(String deviceName)
    {
        originalDeviceName = deviceName;

        try {
            Method method = mManager.getClass().getMethod("setDeviceName", WifiP2pManager.Channel.class, String.class, WifiP2pManager.ActionListener.class);

            method.invoke(mManager, mChannel, ":JS: Dublin --> Cork", new WifiP2pManager.ActionListener() {
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Device name changed successfully", Toast.LENGTH_SHORT).show();
                }

                public void onFailure(int reason) {
                    Toast.makeText(MainActivity.this, "Device name change failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e)   {}
//        Toast.makeText(MainActivity.this, "MY device name: " + , Toast.LENGTH_SHORT).show();

    }
    public String getOriginalDeviceName()
    {
        return originalDeviceName;
    }
}
