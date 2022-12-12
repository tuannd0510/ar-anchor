package com.example.arcore411.communications;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.arcore411.DataHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

public class BluetoothReceive extends Thread {

    private static final String TAG = "BLUETOOTH_RECEIVE_DEBUG_TAG";

    private final BluetoothSocket m_socket;
    private final InputStream m_inputStream;

    BluetoothReceive(BluetoothSocket socket){
        m_socket = socket;
        InputStream inputStream = null;

        try {
            inputStream = m_socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "-- failed to create input stream!", e);
        }
        m_inputStream = inputStream;
    }

    public void run() {
        byte[] buffer = new byte[1024];

        while (true) {
            try {
                // read buffer and check its size
                int bufferSize = m_inputStream.read(buffer);
                Log.i(TAG, "receive: " + bufferSize);
//                // validate buffer size
//                if (bufferSize != 0) {
//
//                }
            } catch (IOException e) {
                Log.e(TAG, "-- failed to read input stream!", e);
                break;
            }
        }
    }
}
