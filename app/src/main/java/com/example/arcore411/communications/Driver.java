package com.example.arcore411.communications;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Driver {

    Context m_context;
    Client m_client = null;

    BluetoothDevice m_device = null; // todo: refactor into list
    BluetoothAdapter m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private static final String TAG = "DRIVER_DEBUG_TAG";

    // manage data between the UI, driver, client, and server
    Handler m_handler = new Handler(Looper.getMainLooper()) {
        final Activity activity = (Activity) m_context;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Flags.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    //
                    break;

                case Flags.MESSAGE_READ:
                    // construct a string from buffer
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
            }
        }
    };

    public Driver(Context context) {
        m_context = context;
        Set<BluetoothDevice> pairedDevices = m_bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // todo: for now, just make sure you only have the 1 paired device
            for (BluetoothDevice device : pairedDevices) {
                m_device = device;

            }
        }
    }

    public void call() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(m_device != null){
            m_client = new Client(m_handler, m_bluetoothAdapter, m_device);
            m_client.start();
            Utils.toast(m_context, "sending: \"Pose\"");
        } else {
            Log.e(TAG, "-- remote device not found!");
        }
    }


}
