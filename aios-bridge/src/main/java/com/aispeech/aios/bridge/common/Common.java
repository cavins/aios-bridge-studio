package com.aispeech.aios.bridge.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class Common {
    public static boolean isAIOSOpen;
    private static final String ISFIRST_BOOT = "isfirstboot";
    public static final int NOT_FIRST_BOOT = 1;
    public static final int FIRST_BOOT = 2;

    public static void setIsFirstBoot(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ISFIRST_BOOT,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(ISFIRST_BOOT, NOT_FIRST_BOOT);
        ed.commit();
    }

    public static int getIsFirstBoot(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ISFIRST_BOOT,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ISFIRST_BOOT, FIRST_BOOT);
    }
}
