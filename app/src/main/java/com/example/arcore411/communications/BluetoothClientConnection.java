package com.example.arcore411.communications;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class BluetoothClientConnection extends Thread {

    private static final String TAG = "THREAD_CONNECTION";

    BluetoothAdapter m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket m_socket;
    BluetoothDevice m_device = null; // todo: refactor into list


    BluetoothReceive m_ThreadReceive = null;
    BluetoothSend m_ThreadSend = null;

    public void run() {

        Set<BluetoothDevice> pairedDevices = m_bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // todo: for now, just make sure you only have the 1 paired device
            for (BluetoothDevice device : pairedDevices) {
                m_device = device;
            }
        }

        int channel = 20; // channel on Linux system
        Method m = null;
        try {
            m = m_device.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            m_socket = (BluetoothSocket) m.invoke(m_device, channel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        m_bluetoothAdapter.cancelDiscovery();
        boolean connected = false;
        // connect to remote server
        int attempts = 10;
        while(attempts > 0 && !connected){
            try {
                m_socket.connect();
                Log.i(TAG, "-- connected to server socket successfully");
                connected = true;

            } catch (IOException e) {
                Log.e(TAG, "-- failed to connect to server socket!", e);
                try {
                    m_socket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "-- unable to close socket", closeException);
                }
            }
            attempts--;
        }

        m_ThreadReceive = new BluetoothReceive(m_socket);
        m_ThreadReceive.start();
        m_ThreadSend = new BluetoothSend(m_socket);
        m_ThreadSend.start();

    }

    public void closeSocket() {
        try {
            m_socket.close();
        } catch (IOException e) {
            Log.e(TAG, "-- failed to close socket!", e);
        }
    }


}
