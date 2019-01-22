package com.serviceprovider;

import android.util.Log;

public class LogsUtils {
    private static boolean PRINT_LOGS = true;
    public static void printLog(String mTag, String mMessage) {
        if (PRINT_LOGS){
            Log.e(mTag, ">>"+mMessage);
        }
    }
}
