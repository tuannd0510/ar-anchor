package com.example.arcore411.communications;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.example.arcore411.DataHolder;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Client extends Thread {

    BluetoothAdapter m_bluetoothAdapter;
    private final BluetoothSocket m_socket;
    private static final String TAG = "CLIENT_DEBUG_TAG";
    private OutputStream m_outputStream;

    public Client(BluetoothAdapter bluetoothAdapter, BluetoothDevice device) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        m_bluetoothAdapter = bluetoothAdapter;

        int channel = 20; // channel on Linux system
        Method m = device.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
        m_socket = (BluetoothSocket) m.invoke(device, channel);

        OutputStream outputStream = null;
        try {
            outputStream = m_socket.getOutputStream();
            Log.e(TAG, "-- output stream created");
        } catch (IOException e) {
            Log.e(TAG, "-- failed to create output stream!", e);
        }
        m_outputStream = outputStream;
    }

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

        /// service ///

        while (true){
            // send pose message
            String message =
                    String.valueOf(DataHolder.getInstance().getCtx()) + String.valueOf(",") +
                            String.valueOf(DataHolder.getInstance().getCty()) + String.valueOf(",") +
                            String.valueOf(DataHolder.getInstance().getCtz());
            System.out.println("message :"+ message);
            byte[] buffer =  message.getBytes();
            System.out.println("buffer: " + buffer);
            System.out.println(Arrays.toString(buffer));

            try {
                m_outputStream.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // pause
            try {
                Thread.sleep(200); // The number of milliseconds that the program will pause.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public void closeSocket() {
        try {
            m_socket.close();
        } catch (IOException e) {
            Log.e(TAG, "-- failed to close socket", e);
        }
    }
}
