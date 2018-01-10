package com.longfor.core.utils.toast;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.longfor.core.app.LongFor;

/**
 * Toast工具类
 * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
 * @version 2012-5-21 下午9:21:01
 * @function Toast 封装，避免Toast消息覆盖，替换系统Toast最好用的封装
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:10
     * @param msg
     */
    public static void showMessage(final String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:10
     * @param msg
     */
    public static void showMessageLong(final String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:35
     * @param msg
     */
    public static void showMessage(final int msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:13:35
     * @param msg
     */
    public static void showMessageLong(final int msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:09
     * @param msg
     * @param len
     */
    public static void showMessage(final int msg,
                                   final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObj) {
                            cancelCurrentToast();
                            toast = Toast.makeText(LongFor.getApplicationContext(), msg, len);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Toast发送消息
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:27
     * @param msg
     * @param len
     */
    public static void showMessage(final String msg,
                                   final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (synObj) {
                            cancelCurrentToast();
                            toast = Toast.makeText(LongFor.getApplicationContext(), msg, len);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 关闭当前Toast
     * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:45
     */
    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}

