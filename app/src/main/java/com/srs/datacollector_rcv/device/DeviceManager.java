package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.os.Build;

public class DeviceManager {

    public static IDataCollector getDevice(Activity activity) {

        String manufacturer = Build.MANUFACTURER.toLowerCase();
        String display = Build.DISPLAY.toLowerCase();

        String deviceName = manufacturer+'-'+display;

        if (deviceName.contains("ciontek-a52_v0.19") || deviceName.contains("ciontek-a52_v0.17")) {
            return new CS50BlueDevice();
        } else if (deviceName.contains("ciontek-a52_v0.32") || deviceName.contains("ciontek-a52_v0.31") || deviceName.contains("ciontek-a52_v0.33")) {
            return new CS50BlackDevice();
        } else if(manufacturer.contains("cilico")) {
            return new CilicoDevice();
        }

        throw new RuntimeException("Unsupported device");
    }
}