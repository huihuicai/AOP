package com.huihuicai.aspectj.log;

import android.util.Log;

/**
 * Created by ybm on 2017/8/30.
 */

public class LogUtil {

    public static void print(String tag, String message) {
        Log.e(tag, message);
    }
}
