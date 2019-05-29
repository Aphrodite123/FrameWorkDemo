package com.aphrodite.framework.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Aphrodite on 2017/9/16.
 */

public class UIUtils {
    /**
     * Screen width
     */
    private static int sDisplayWidthPixels = 0;
    /**
     * Screen height
     */
    private static int sDisplayHeightPixels = 0;

    private static void getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        sDisplayWidthPixels = dm.widthPixels;
        sDisplayHeightPixels = dm.heightPixels;
    }

    public static int getDisplayWidthPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (sDisplayWidthPixels == 0) {
            getDisplayMetrics(context);
        }
        return sDisplayWidthPixels;
    }

    public static int getDisplayHeightPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (sDisplayHeightPixels == 0) {
            getDisplayMetrics(context);
        }
        return sDisplayHeightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static void openSoftKeyboard(EditText et) {
        InputMethodManager inputManager =
                (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et, 0);
    }

    public static void closeSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context Activity 上下文
     * @return int[] 长度为2
     */
    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        size[0] = metric.widthPixels;
        size[1] = metric.heightPixels;
        return size;
    }

    /**
     * 获取屏幕亮度
     *
     * @param activity 上下文
     * @return 亮度值
     */
    public static float getScreenBrightness(Activity activity) {
        float value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
            return value / 100;
        } catch (Settings.SettingNotFoundException e) {
            return 0.6f;
        }

    }

    /**
     * 设置当前窗口的亮度（activity）
     *
     * @param context    上下文
     * @param brightness 亮度值(0~1.0)
     */
    public static void setActivityBrightness(Activity context, float brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = brightness;
        context.getWindow().setAttributes(lp);
    }

    /**
     * 保持屏幕常亮
     *
     * @param context 上下文
     * @return PowerManager.WakeLock
     */
    public static PowerManager.WakeLock KeepScreenOn(Context context) {
        PowerManager manager = ((PowerManager) context.getSystemService(Context.POWER_SERVICE));
        PowerManager.WakeLock wakeLock =
                manager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ON_AFTER_RELEASE, "ATAAW");
        wakeLock.acquire();
        return wakeLock;
    }

    /**
     * 获取手机DPI
     *
     * @param activity 上下文
     * @return DIP值
     */
    public static String getPhoneDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        //ldpi  	0.75 倍
        //mdi		1	倍
        //hdpi		1.5	倍
        //xhdpi		2	倍
        //xxhdpi	3	倍
        //xxxhpdi	4	倍
        String dpiStr = "mdpi"; //默认mdpi值
        if (0.75 == density)
            dpiStr = "mdpi";
        else if (1 == density)
            dpiStr = "mdpi";
        else if (1.5 == density)
            dpiStr = "hdpi";
        else if (2 == density)
            dpiStr = "xhdpi";
        else if (3 == density)
            dpiStr = "xxhdpi";
        else if (4 == density)
            dpiStr = "xxhdpi";
        return dpiStr;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (null == context)
            return 0;

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
