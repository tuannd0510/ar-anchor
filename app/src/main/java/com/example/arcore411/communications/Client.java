package com.example.arcore411.communications;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Client extends Thread {

    Handler m_handler;
    Service.Caller m_service;
    BluetoothAdapter m_bluetoothAdapter;
    private final BluetoothSocket m_socket;
    private static final String TAG = "CLIENT_DEBUG_TAG";

    public Client(BluetoothAdapter bluetoothAdapter, BluetoothDevice device) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        m_bluetoothAdapter = bluetoothAdapter;

        int channel = 20; // channel on Linux system
        Method m = device.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
        m_socket = (BluetoothSocket) m.invoke(device, channel);
    }

//    public Client(Handler handler, BluetoothAdapter bluetoothAdapter, BluetoothDevice device) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        m_handler = handler;
//        m_bluetoothAdapter = bluetoothAdapter;
//
//        int channel = 20; // channel on Linux system
//        Method m = device.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
//        m_socket = (BluetoothSocket) m.invoke(device, channel);
//    }

    public void run() {
        m_bluetoothAdapter.cancelDiscovery();
        boolean connected = false;

        // connect to remote server
        int attempts = 10;
        while(attempts > 0 && !connected){
            try {
                m_socket.connect();
                Log.e(TAG, "-- connected to server socket successfully");
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
        service(m_socket);
    }

    public void service(BluetoothSocket socket){
        m_service = new Service.Caller(socket);
        m_service.start();
    }

    public void closeSocket() {
        m_service.closeSocket();
        try {
            m_socket.close();
        } catch (IOException e) {
            Log.e(TAG, "-- failed to close socket!", e);
        }
    }
}
