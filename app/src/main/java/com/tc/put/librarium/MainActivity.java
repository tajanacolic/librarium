package com.tc.put.librarium;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tc.put.librarium.models.DeviceListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TCL";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void exit(View v){
        System.exit(0);
    }

    public void openReviewList(View v){
        startActivity(new Intent(MainActivity.this, ReviewListActivity.class));
    }

    public void openNewReview (View v){
        startActivity(new Intent(MainActivity.this, NewReviewActivity.class));
    }

    public void openBT(View v){
        startActivity(new Intent(MainActivity.this, BTActivity.class));
    }



}