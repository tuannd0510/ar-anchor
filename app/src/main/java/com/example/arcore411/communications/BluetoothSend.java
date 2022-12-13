package com.example.arcore411.communications;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.arcore411.DataHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BluetoothSend extends Thread {

    private static final String TAG = "BLUETOOTH_SEND_DEBUG_TAG";

    private final BluetoothSocket m_socket;
    private OutputStream m_outputStream;

    String message;

    BluetoothSend(BluetoothSocket socket){
        m_socket = socket;
        OutputStream outputStream = null;
        try {
            outputStream = m_socket.getOutputStream();
            Log.i(TAG, "-- output stream created");
        } catch (IOException e) {
            Log.e(TAG, "-- failed to create output stream!", e);
        }
        m_outputStream = outputStream;
    }

    public void run() {


        while (true){
            // send pose message
            if (DataHolder.getInstance().getNewTap()){
                message =
                    String.valueOf("2,")+ String.valueOf(DataHolder.getInstance().getHtx()) +
                    String.valueOf(",") + String.valueOf(DataHolder.getInstance().getHty()) +
                    String.valueOf(",") + String.valueOf(DataHolder.getInstance().getHtz());
            }else {
                message =
                        String.valueOf("1,")+ String.valueOf(DataHolder.getInstance().getCtx()) +
                        String.valueOf(",") + String.valueOf(DataHolder.getInstance().getCty()) +
                        String.valueOf(",") + String.valueOf(DataHolder.getInstance().getCtz());
            }
            byte[] buffer =  message.getBytes();
            try {
                m_outputStream.write(buffer);
                Log.i(TAG, "message = " + message);
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

}
