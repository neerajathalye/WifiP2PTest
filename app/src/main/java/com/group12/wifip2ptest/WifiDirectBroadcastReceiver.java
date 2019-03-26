package com.group12.wifip2ptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by Neeraj Athalye on 11-Mar-19.
 */
public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WifiDirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mActivity) {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mActivity = mActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
//                Toast.makeText(mActivity, "WIFI P2P ENABLED", Toast.LENGTH_SHORT).show();
            } else {
                // Wi-Fi P2P is not enabled
//                Toast.makeText(mActivity, "WIFI P2P DISABLED", Toast.LENGTH_SHORT).show();
            }
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            // Call WifiP2pManager.requestPeers() to get a list of current peers

            Toast.makeText(mActivity, "PEERS CHANGED ACTION", Toast.LENGTH_SHORT).show();
            if (mManager != null)
                mManager.requestPeers(mChannel, mActivity);
        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {
            // Respond to new connection or disconnections
        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
            // Respond to this device's wifi state changing
            WifiP2pDevice myDevice = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            mActivity.setOriginalDeviceName(myDevice.deviceName);
            Log.d(TAG, myDevice.deviceName);
//            Toast.makeText(mActivity, "MY device name: " + myDevice.deviceName, Toast.LENGTH_SHORT).show();



        }

    }
}
