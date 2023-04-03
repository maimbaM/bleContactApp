

package com.maimba.west.bleContactApp;

import android.os.ParcelUuid;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Constants for use in the Bluetooth Advertisements sample
 */
public class Constants {





    public static final ParcelUuid Service_UUID = ParcelUuid
            .fromString("0000b81d-0000-1000-8000-00805f9b34fb");
    public static String SERVICE_DATA;
    public static String currentDiseaseName;
    public static long currentDiseaseInc;


    public static final int REQUEST_ENABLE_BT = 1;

}
