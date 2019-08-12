package com.aphrodite.framework.view.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Aphrodite on 2017/9/1. 统一管理Activity
 */

public class ActivitysManager {

    private static ActivitysManager sInstance = null;

    private static Stack<Activity> mStacks;

    /**
     * 单一实例
     *
     * @return Object of ActivitysManager
     */
    public static ActivitysManager getInstance() {
        if (null == sInstance) {
            synchronized (ActivitysManager.class) {
                if (null == sInstance) {
                    sInstance = new ActivitysManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity Object of Activity
     */
    public void addActivity(Activity activity) {
        if (null == mStacks) {
            mStacks = new Stack<Activity>();
        }
        mStacks.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     *
     * @return The current activity
     */
    public Activity getCurrentActivity() {
        Activity activity = mStacks.lastElement();
        return activity;
    }

    /**
     * 获取指定类名的Activity
     *
     * @param cls class
     * @return The specifc of Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : mStacks) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    public boolean isFrontActivity(Activity activity) {
        if (mStacks.isEmpty()) {
            return false;
        }
        return mStacks.indexOf(activity) == 0;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishLastActivity() {
        Activity activity = mStacks.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mStacks.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls class
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mStacks) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mStacks.size(); i < size; i++) {
            if (null != mStacks.get(i)) {
                mStacks.get(i).finish();
            }
        }
        mStacks.clear();
    }

    /**
     * 退出应用程序
     *
     * @param context context
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

}
