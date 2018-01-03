package com.longfor.core.utils.UI;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: gaomei
 * @date: 2017/8/18
 * @function: 通用的界面工具类
 *
 */
public class UiUtil {

    private  static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static Handler mHandler=new Handler(Looper.getMainLooper());

    private static Toast sToast=null;

    public static Handler getHandler() {
        return mHandler;
    }

    public static void runInThread(Runnable task){
        threadPool.execute(task);
    }

    public static void myRunOnUIThread(Runnable task){
        mHandler.post(task);
    }

    public static void showToast(final Context context, final String text){
        myRunOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sToast==null){
                    sToast= Toast.makeText(context,"", Toast.LENGTH_SHORT);
                }
                sToast.setText(text);
                sToast.show();
            }
        });
    }
}
