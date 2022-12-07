package com.example.arcore411.communications;

public interface Flags {

    String TOAST = "toast";
    String DEVICE_NAME = "device_name";

    int MESSAGE_READ = 2;
    int MESSAGE_WRITE = 3;
    int MESSAGE_TOAST = 5;
    int MESSAGE_DEVICE_NAME = 4;
    int MESSAGE_STATE_CHANGE = 1;

    int STATE_NONE = 0;       // todo: make self explanatory
    int STATE_LISTEN = 1;     // now listening for incoming connections
    int STATE_CONNECTING = 2; // now initiating an outgoing connection
    int STATE_CONNECTED = 3;  // now connected to a remote device
}
