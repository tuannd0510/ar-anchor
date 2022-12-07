package com.example.arcore411.communications;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Service {

    private static final String TAG = "SERVICE_DEBUG_TAG";

    public static class Caller extends Thread {

        private final BluetoothSocket m_socket;
        private final OutputStream m_outputStream;

        public Caller(BluetoothSocket socket) {
            m_socket = socket;

            OutputStream outputStream = null;

            try {
                outputStream = socket.getOutputStream();
                Log.e(TAG, "-- output stream created");
            } catch (IOException e) {
                Log.e(TAG, "-- failed to create output stream!", e);
            }
            m_outputStream = outputStream;
        }

        public void run() {
            // send pose message
            String message = " Ohio-Senpai ";
            byte[] buffer =  message.getBytes();

            try {
                m_outputStream.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
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
}
