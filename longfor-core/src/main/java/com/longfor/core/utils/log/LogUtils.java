package com.longfor.core.utils.log;

import android.util.Log;

public class LogUtils {

    private final static boolean DEBUG = true;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, checkMsg(msg));
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.v(tag, checkMsg(msg), tr);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, checkMsg(msg));
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, checkMsg(msg), tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, checkMsg(msg));
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, checkMsg(msg), tr);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, checkMsg(msg));
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.w(tag, checkMsg(msg), tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (DEBUG) {
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, checkMsg(msg));
//            printAllLogcate(tag, checkMsg(msg));
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(tag, checkMsg(msg), tr);
        }
    }

    private static String checkMsg(String msg) {
        return msg == null ? "null" : msg;
    }

    /**
     * 打印完整的logcat日志
     * @param bodyMsg
     */
    private static void printAllLogcate(String tag, String bodyMsg) {
        if (bodyMsg.length() > 4000) {
            for (int i = 0; i < bodyMsg.length(); i += 4000) {
                //当前截取的长度<总长度则继续截取最大的长度来打印
                if (i + 4000 < bodyMsg.length()) {
                    if (i == 0){
                        Log.e(tag, bodyMsg.substring(i, i + 4000));
                    }
                    else {
                        Log.e("",bodyMsg.substring(i, i + 4000));
                    }
                } else {
                    //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                    Log.e("", bodyMsg.substring(i, bodyMsg.length()));
                }
            }
        } else {
            //直接打印
            Log.e(tag, bodyMsg);
        }
    }
}
