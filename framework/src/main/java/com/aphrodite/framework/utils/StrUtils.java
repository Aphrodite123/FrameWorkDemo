package com.aphrodite.framework.utils;

import java.util.Map;

/**
 * Created by Aphrodite on 2019/4/1.
 */
public class StrUtils {

    /**
     * Checks if a String is whitespace, empty ("") or null.
     *
     * @param str str
     * @return true or false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断Map是否为空
     *
     * @param map map
     * @param <K> key
     * @param <V> value
     * @return true or false
     */
    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return (map == null || map.size() < 1);
    }

    /**
     * 判断数组是否为空
     *
     * @param obj array of obj
     * @return true or false
     */
    public static boolean isEmptyArray(Object[] obj) {
        return (obj == null || obj.length < 1);
    }

}
