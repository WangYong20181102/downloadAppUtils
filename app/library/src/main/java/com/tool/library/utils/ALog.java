package com.tool.library.utils;

import android.util.Log;

import com.tool.library.core.AllenChecker;


public class ALog {
    public static void e(String msg) {
        if (AllenChecker.isDebug()) {
            if (msg != null && !msg.isEmpty())
                Log.e("Allen Checker", msg);
        }
    }
}
