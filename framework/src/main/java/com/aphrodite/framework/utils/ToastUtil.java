package com.aphrodite.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Aphrodite on 2017/9/16.
 */

public class ToastUtil {
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    /**
     * Application Context，否则可能出现内存泄漏
     */
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void showMessage(int msg) {
        showMessage(mContext, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(String msg) {
        showMessage(mContext, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(Context act, final int msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final Context act, final String msg, final int len) {
        if (null == act) {
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast = Toast.makeText(act, msg, len);
                            } else {
                                toast = Toast.makeText(act, msg, len);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public static void showMessage(final Context act, final int msg, final int len) {
        if (null == act) {
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast = Toast.makeText(act, msg, len);
                            } else {
                                toast = Toast.makeText(act, msg, len);
                            }
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }
}
