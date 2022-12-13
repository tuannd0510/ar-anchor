package com.example.arcore411.communications;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.arcore411.DataHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
                byte[] buffer1 = new byte[bufferSize];
                for (int i=0; i<bufferSize;i++){
                    buffer1[i] = buffer[i];
                }
//                Log.i(TAG, "receive: " + bufferSize);

                // validate buffer size
                if (bufferSize != 0) {
                    saveTap(buffer1);
                    Log.i(TAG, "run: "+ buffer1);
                }
            } catch (IOException e) {
                Log.e(TAG, "-- failed to read input stream!", e);
                break;
            }
        }
    }

    private void saveTap(byte[] buffer) {
        String message = new String(buffer, StandardCharsets.UTF_8);
        String[] str = message.split(",");

        DataHolder.getInstance().setTapX(Float.parseFloat(str[0]));
        DataHolder.getInstance().setTapY(Float.parseFloat(str[1]));
        DataHolder.getInstance().setIsNewTap(true);

        Log.i(TAG, "saveTap: datax "+DataHolder.getInstance().getTapX());
        Log.i(TAG, "saveTap: datay "+DataHolder.getInstance().getTapY());
    }


}
