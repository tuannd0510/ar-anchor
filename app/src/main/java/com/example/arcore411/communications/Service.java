package com.example.arcore411.communications;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.arcore411.DataHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

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
            while (true){
                // send pose message
                String message =
                        String.valueOf(DataHolder.getInstance().getCtx()) + String.valueOf(",") +
                        String.valueOf(DataHolder.getInstance().getCty()) + String.valueOf(",") +
                        String.valueOf(DataHolder.getInstance().getCtz());
                System.out.println("message :"+ message);
                byte[] buffer =  message.getBytes();
                System.out.println("buffer: " + buffer);

//            byte[] bytes = new byte[8];
//            ByteBuffer.wrap(bytes).putDouble(DataHolder.getInstance().getCtx());
//            System.out.println("byte 8: " + bytes);
//
//            byte [] bytes1 = ByteBuffer.allocate(8).putDouble(DataHolder.getInstance().getCtx()).array();
//            System.out.println("bytes1 8: " + bytes1);

                System.out.println(Arrays.toString(buffer));

                try {
                    m_outputStream.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(2000);
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
}
