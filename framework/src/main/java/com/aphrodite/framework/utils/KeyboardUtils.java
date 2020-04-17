package com.aphrodite.framework.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Description:虚拟键盘操作类
 * Name:         KeyboardUtils
 * Author:       zhangjingming
 * Date:         2016-09-13
 */

public class KeyboardUtils {
    /**
     * 显示键盘
     *
     * @param context
     */
    public static void showKeyboard(Context context) {
        if (null == context)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param editText 键盘关联的编辑框
     */
    public static void hideKeyboard(Context context, EditText editText) {
        if (null == context || null == editText)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
