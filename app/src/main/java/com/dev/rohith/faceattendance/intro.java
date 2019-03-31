package com.dev.rohith.faceattendance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.rohith.faceattendance.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class intro extends AppCompatActivity{
    /*
        Permissions:
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
     */

    public Button button;
    public Button next_btn;
    public EditText editText;
    public BluetoothAdapter btAdapter;
    public BluetoothDevice btDevice;
    public BluetoothSocket btSocket;



    public static final String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID
    public static final String SERVICE_ADDRESS = "00:18:E4:40:00:06"; // HC-05 BT ADDRESS


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        next_btn = (Button) findViewById(R.id.button2);


        final TextView left_front = (TextView) findViewById(R.id.textView2);
        final TextView right_front = (TextView) findViewById(R.id.textView);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btDevice = btAdapter.getRemoteDevice(SERVICE_ADDRESS);

        if(btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not available", Toast.LENGTH_LONG).show();
        } else {
            if(!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 3);
            } else {
                ConnectThread connectThread = new ConnectThread(btDevice);
                connectThread.start();
            }
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket != null) {

                    try{
                        InputStream out = btSocket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int bytes;
                        int i = 1;
                        while(true){
                            bytes = out.read(buffer);
                            String incoming = new String(buffer,0,bytes);
                            System.out.println(incoming);
                            if(i%2==0){
                                //right_front.setText(incoming);
                            }
                            else{
                                //left_front.setText(incoming);
                            }
                            setContentView(R.layout.activity_main);

                        }


                    }catch(IOException e) {

                    }

                }
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity1();
            }
        });





    }

    private void launchActivity1() {

        Intent intent = new Intent(this,check.class);
        startActivity(intent);
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket thisSocket;
        private final BluetoothDevice thisDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            thisDevice = device;

            try {
                tmp = thisDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
            } catch (IOException e) {
                Log.e("TEST", "Can't connect to service");
            }
            thisSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            btAdapter.cancelDiscovery();

            try {
                thisSocket.connect();
                Log.d("TESTING", "Connected to shit");
            } catch (IOException connectException) {
                try {
                    thisSocket.close();
                } catch (IOException closeException) {
                    Log.e("TEST", "Can't close socket");
                }
                return;
            }

            btSocket = thisSocket;

        }
        public void cancel() {
            try {
                thisSocket.close();
            } catch (IOException e) {
                Log.e("TEST", "Can't close socket");
            }
        }
    }

}