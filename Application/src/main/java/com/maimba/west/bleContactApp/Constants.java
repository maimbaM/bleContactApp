

package com.maimba.west.bleContactApp;

import android.os.ParcelUuid;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Constants for use in the Bluetooth Advertisements sample
 */
public class Constants {

//    private String generateSafeToken() {
//        SecureRandom random = new SecureRandom();
//        byte bytes[] = new byte[20];
//        random.nextBytes(bytes);
//        Base64.Encoder encoder = android.util.Base64.getUrlEncoder().withoutPadding();
//        String token = encoder.encodeToString(bytes);
//        return token;
//    }
//    public static final String trialData= RandomStringUtils.random(20, 0, 0, true, false, null, new SecureRandom());



    public static final ParcelUuid Service_UUID = ParcelUuid
            .fromString("0000b81d-0000-1000-8000-00805f9b34fb");

    public static final String bleServiceData = RandomStringUtils.random(20, 0, 0, true, false, null, new SecureRandom());;

    public static final int REQUEST_ENABLE_BT = 1;

}
