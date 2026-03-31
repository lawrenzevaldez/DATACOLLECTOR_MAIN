package com.srs.datacollector_rcv.device;

import android.app.Activity;
import android.os.Build;

public class DeviceManager {

    public static IDataCollector getDevice(Activity activity) {

        String manufacturer = Build.MANUFACTURER.toLowerCase();
        String display = Build.DISPLAY.toLowerCase();

        String deviceName = manufacturer+'-'+display;

        if (deviceName.equals("ciontek-a52_v0.19_20220914g")) {
            return new CS50BlueDevice();
        } else if (deviceName.equals("ciontek-a52_v0.32_20250912g")) {
            return new CS50BlackDevice();
        } else if(manufacturer.contains("cilico")) {
            return new CilicoDevice();
        }

        throw new RuntimeException("Unsupported device");
    }
}