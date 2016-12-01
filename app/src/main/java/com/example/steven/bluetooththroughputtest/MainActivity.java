package com.example.steven.bluetooththroughputtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final int BLUETOOTH_REQUEST_CODE = 1;
    BluetoothManager mBtManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.steven.bluetooththroughputtest.R.layout.activity_main);
        SetupBluetoothConnection();
    }

    public void SetupBluetoothConnection() {

        //Get device bluetooth adapter and save as class member
        BluetoothAdapter lBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //If bluetooth is not enabled, see if the user wants to enable it
        if (!lBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, BLUETOOTH_REQUEST_CODE);
            ((RadioButton) findViewById(R.id.radioButton1)).setEnabled(false);
            ((RadioButton) findViewById(R.id.radioButton2)).setEnabled(false);
        }

        mBtManager = new BluetoothManager(lBluetoothAdapter);

    }

    public void onButtonClick(View v) {
        switch(v.getId()) {
            case R.id.pairView:

                Set<BluetoothDevice> lPairedDeviceSet = mBtManager.GetPairedDevices();
                TextView lConsoleTextView = (TextView) findViewById(R.id.androidConsoleTV);
                String lPairedDeviceNames = "";
                if(lPairedDeviceSet.size() > 0) {
                    for (BluetoothDevice device : lPairedDeviceSet) {
                        lPairedDeviceNames = lPairedDeviceNames.concat(device.getName());
                    }
                }
                else {
                    lPairedDeviceNames = "No paired devices.";
                }
                lConsoleTextView.setText(lPairedDeviceNames);

        }
    }

    public void onRadioButton(View v) {

        switch (v.getId()) {
            case R.id.radioButton1:

                mBtManager.SetMasterOrSlave(true);
                break;

            case R.id.radioButton2:

                mBtManager.SetMasterOrSlave(false);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BLUETOOTH_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                ((RadioButton) findViewById(R.id.radioButton1)).setEnabled(true);
                ((RadioButton) findViewById(R.id.radioButton2)).setEnabled(true);
            }
        }
    }
}