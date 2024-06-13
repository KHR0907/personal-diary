package com.example.personal_diary_2022551022;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DeviceStatus extends AppCompatActivity {

    boolean isWifiConn = false;
    boolean isMobileConn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_status);
        ImageButton backBtn = findViewById(R.id.btn_rtnHome_status);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        TextView versionTxt = findViewById(R.id.text_andoridVersion_status);
        versionTxt.setText(android.os.Build.VERSION.RELEASE);

        checkNetworkConn();
        TextView wifiStatus = findViewById(R.id.text_wifi_status);
        wifiStatus.setText(isWifiConn? "켜짐" : "꺼짐");
        TextView cellularStatus = findViewById(R.id.text_cellular_status);
        cellularStatus.setText(isMobileConn? "켜짐" : "꺼짐");

        TextView storageStatus = findViewById(R.id.text_storage_status);
        storageStatus.setText(getStorageStatus());

        TextView appStatus = findViewById(R.id.text_appCount_status);
        appStatus.setText(getInstalledPackage());

//        wifiStatus.setText(String.valueOf(isWifiEnabled()));
//
//        cellularStatus.setText(String.valueOf(isCellularEnabled()));
//


    }

    private void checkNetworkConn(){
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d("Device_STAT", "Wifi connected: " + isWifiConn);
        Log.d("Device_STAT", "Mobile connected: " + isMobileConn);
    }
    private String isWifiEnabled() {
        WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int status = wifiManager.getWifiState();
        if (status == WifiManager.WIFI_STATE_ENABLED) {
            return "켜짐";
        } else {
            return "꺼짐";
        }
    }
    private boolean isCellularEnabled() {
        // Get the Connectivity Manager
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get active network info
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // Check if there is an active network and it's mobile
        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            // Check if the mobile network is actually connected (data is enabled)
            TelephonyManager telManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telManager.getDataState() == TelephonyManager.DATA_CONNECTED;
        }

        return false;
    }
    private String getStorageStatus() {
        //get device storage size
        long totalSpace = StorageUtils.getTotalStorageSpaceInBytes();
        long freeSpace = StorageUtils.getFreeStorageSpaceInBytes();

        String totalSpaceFormatted = StorageUtils.bytesToHumanReadable(totalSpace);
        String freeSpaceFormatted = StorageUtils.bytesToHumanReadable(freeSpace);

        return freeSpaceFormatted+"/"+totalSpaceFormatted;
    }

    private String getInstalledPackage(){
        //get installed package list
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        PackageManager packageManager = getApplicationContext().getPackageManager();
        return processes.size()+"/"+packageManager.getInstalledPackages(0).size();

    }

}