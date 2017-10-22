package com.benedridge.my.nmealog;

import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class UsbActivity extends AppCompatActivity {

    //USB setup for streaming
    private UsbManager usbManager;
    private UsbDevice usbDevice;
    private UsbConfiguration usbConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Getting our USB manager service for streaming over USB
        usbManager = (UsbManager) this.getSystemService(this.USB_SERVICE);

        if(usbManager.getDeviceList().isEmpty()){
            Toast.makeText(this, "No USB devices plugged into device", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Devices is plugged into USB device", Toast.LENGTH_SHORT).show();
        }


        System.out.println("USB:"+usbManager.getDeviceList().toString());
    }

}
