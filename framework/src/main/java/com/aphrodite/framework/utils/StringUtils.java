package com.aphrodite.framework.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;

import com.aphrodite.framework.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aphrodite on 2019/5/13.
 * 字符串操作工具类
 */
public class StringUtils {
    /**
     * 特殊字符规则
     */
    public static final String SPECIALCHARREGEX
            = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]";

    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX
            = "^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX
            = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    /**
     * 身份证号码正则
     */
    public static final String ID_CARD_NUM_REGEX = "([0-9]{17}([0-9]|X))|([0-9]{15})";

    /**
     * 匹配中文字符和空格正则
     */
    public static final String CHINESE_OR_BLANK_REGEX = "[\\u4e00-\\u9fa5]|\\s";

    /**
     * 匹配数字正则
     */
    public static String DIGIT_REGEX = "\\S*[0-9]+\\S*";

    /**
     * 匹配英文字母正则
     */
    public static String LETTER_REGEX = "\\S*[a-zA-Z]+\\S*";

    /**
     * 匹配登录密码允许的特殊字符正则
     */
    public static String SYMBOL_REGEX = "\\S*[\\]\\[\\-&+*/',.:;|\"`~@%$#_<>{}=?!^()\\\\]+\\S*";

    /**
     * 数字汉语数组
     */
    public static String[] HAN_ARRAY = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * 单位汉语数组
     */
    public static String[] UNIT_ARRAY = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

    /**
     * 过滤指定字符串中的特殊字符
     *
     * @param src       元字符
     * @param codeRegex 特殊字符正则表达式，可为空
     * @return 字符
     */
    public static String specialStringFilter(String src, String codeRegex) {
        Matcher matcher = Pattern.compile(codeRegex).matcher(src);
        return matcher.replaceAll("");
    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles tell phone number
     * @return true or false
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile(PHONE_REGEX);
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 对指定字符串进行正则匹配
     *
     * @param src   原字符
     * @param regex 正则
     * @return true or false
     */
    public static boolean matches(String src, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(src);

        return m.matches();
    }

    /**
     * 对指定字符串进行正则匹配
     *
     * @param src   原字符
     * @param regex 正则
     * @return true or false
     */
    public static boolean find(String src, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(src);

        return m.find();
    }

    /**
     * 判断email格式是否正确
     *
     * @param email dest email
     * @return true or false
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 判断身份证号码格式是否正确
     *
     * @param cardNum ID card No.
     * @return true or false
     */
    public static boolean isIdCardNum(String cardNum) {
        Pattern p = Pattern.compile(ID_CARD_NUM_REGEX);
        Matcher m = p.matcher(cardNum);
        return m.matches();
    }

    /**
     * 将制定手机号格式化成 3 4 4 形式
     *
     * @param mobiles 指定的手机号
     * @return 格式化成 3 4 4 格式的手机号
     */
    public static String formatterMobileNO(String mobiles) {
        return mobiles.substring(0, 3) + " " + mobiles.substring(3, 7) + " " + mobiles.substring(7);
    }

    /**
     * 判断字符串不为空,如果字符串有值返回true，其他返回false
     *
     * @param str 需要判断是否非空的字符
     * @return true or false
     */
    public static boolean isNotEmpty(String str) {
        return !(null == str || "".equals(str.trim()) || str.length() <= 0);
    }

    /**
     * 判断字符串不为空,如果字符串有值返回true，其他返回false
     *
     * @param strings 需要判断是否非空的字符
     * @return true or false
     */
    public static boolean isNotEmpty(String... strings) {
        for (String str : strings) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串为空,如果字符串有值返回true，其他返回false
     *
     * @param str 需要判断是否非空的字符
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || str.length() <= 0;
    }

    /**
     * Description:显示前3位和@后3位。如12345678@qq.com转换后为：123*****@qq.***
     *
     * @param email dest email
     * @return String
     */
    public static String formatEmail(String email) {
        return email.replaceAll("(.{3}).*@(.{3}).*", "$1****@$2***");
    }

    /**
     * Description:显示前3位和后3位。如13812345678转换后为：138*****678
     *
     * @param num 需要隐藏的手机号
     * @return String
     */
    public static String hideMobileNO(String num) {
        if (StringUtils.isEmpty(num) || num.length() != 11) {
            return num;
        }
        return num.replaceAll("(.{3}).*(.{3})", "$1*****$2");
    }

    /**
     * Description:显示前3位和后4位。如13812345678转换后为：138****5678
     *
     * @param num 需要隐藏的手机号
     * @return String
     */
    public static String hideMobileNO2(String num) {
        if (StringUtils.isEmpty(num) || num.length() != 11) {
            return num;
        }
        return num.replaceAll("(.{3}).*(.{4})", "$1*****$2");
    }

    /**
     * Description:格式化登录密码，去除中文字符和空格
     *
     * @param psw 登录密码
     * @return String
     */
    public static String formatLoginPsw(String psw) {
        if (psw == null) {
            return "";
        }
        return psw.replaceAll(CHINESE_OR_BLANK_REGEX, "");
    }

    public static InputFilter[] getPasswordInputFilter() {
        return new InputFilter[]{DigitsKeyListener
                .getInstance(
                "[&(/',.:;)*~%$#_+-]~@%[]<>{}=|?!^\"\\`0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXZY"),
                new InputFilter.LengthFilter(6)};
    }

    public static InputFilter[] getSpecifyInputFilter(String input) {
        return new InputFilter[]{DigitsKeyListener.getInstance(input)};
    }

    /**
     * Description:校验登录密码格式 (字母、数字或字符至少两种组合)
     *
     * @param password password
     * @return 是否符合
     */
    public static boolean checkLoginPswFormat(String password) {
        Pattern p = Pattern.compile(DIGIT_REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m = p.matcher(password);

        Pattern p2 = Pattern.compile(LETTER_REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m2 = p2.matcher(password);

        Pattern p3 = Pattern.compile(SYMBOL_REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher m3 = p3.matcher(password);

        boolean hasDigit = m.find();
        boolean hasLetter = m2.find();
        boolean hasSymbol = m3.find();

        return (hasDigit && hasLetter) || (hasDigit && hasSymbol) || (hasLetter && hasSymbol);

    }

    /**
     * 格式化金额，将单位为分的金额格式化为两位小数单位为元的金额 如：1分 转换成 0.01元
     *
     * @param amt amt
     * @return format amt
     */
    public static String formatAmtFromFenToYuan(String amt) {
        if (StringUtils.isEmpty(amt)) {
            return "0.00";
        }
        String amtFormatStr = "";
        try {
            BigDecimal amount = new BigDecimal(amt);
            DecimalFormat nf = new DecimalFormat();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            amtFormatStr = nf.format(amount.divide(new BigDecimal(100)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return amtFormatStr;
    }

    /**
     * 格式化金额，将单位为厘的金额格式化为两位小数单位为元的金额，舍去厘位 如：1厘 到 0.00元 123456厘 到 123.45元
     *
     * @param amt amt
     * @return format amt
     */
    public static String formatAmtFromLiToYuan(String amt) {
        if (StringUtils.isEmail(amt)) {
            return "0.00";
        }
        String amtString = "";
        try {
            BigDecimal amtB = new BigDecimal(amt);
            amtString = amtB.divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_FLOOR).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return amtString;
    }

    /**
     * 格式化金额，将单位为元的金额格式化为两位小数单位为元的金额 如：1.1元 到 1.10元
     *
     * @param amt amt
     * @return format amt
     */
    public static String formatAmt(String amt) {
        if (StringUtils.isEmpty(amt)) {
            return "0.00";
        }
        String amtStr = amt.replaceAll(",", "");
        String amtFormatStr = "";
        try {
            BigDecimal amount = new BigDecimal(amtStr);
            DecimalFormat nf = new DecimalFormat();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            amtFormatStr = nf.format(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return amtFormatStr;
    }

    /**
     * 格式化金额，将单位为元的金额格式化单位为分的整数金额 如：0.01元 到 1分
     *
     * @param amount amt
     * @return format amt
     */
    public static String formatAmtFromYuanToFen(String amount) {
        if (StringUtils.isEmpty(amount)) {
            return "0";
        }

        String result = "";
        try {
            BigDecimal bigDecimal = new BigDecimal(amount);
            DecimalFormat myformat = new DecimalFormat("0");
            result = myformat.format(bigDecimal.multiply(new BigDecimal(100)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转化文件大小为字符串
     *
     * @param size 文件大小
     * @return Byte、KB、MB、GB、TB等尺寸字符串
     */
    public static String formatSize(double size) {
        if (0 == size)
            return "0";

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 转换阿拉伯数字为汉字
     *
     * @param strNum 阿拉伯数字
     * @return 汉字
     */
    public static String toHanString(String strNum) {
        String result = "";
        int numLen = strNum.length();
        for (int i = 0; i < numLen; i++) {
            int num = strNum.charAt(i) - 48;

            if (i != numLen - 1 && num != 0) {
                if (2 == numLen && 1 == num)
                    result += UNIT_ARRAY[numLen - 2 - i];
                else
                    result += HAN_ARRAY[num] + UNIT_ARRAY[numLen - 2 - i];
            } else {
                if (0 == num && (result.substring(result.length() - 1).equals(HAN_ARRAY[0]) || i == numLen - 1))
                    continue;
                result += HAN_ARRAY[num];
            }
        }

        if (result.substring(result.length() - 1).equals(HAN_ARRAY[0]))
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 格式化距离单位，根据实际距离格式化为米/公里/千公里/万公里
     *
     * @param context context
     * @param source  距离
     * @return 转换后距离单位
     */
    public static String formatDistance(Context context, String source) {
        float distance = parseFloat(source);
        if (distance < 1000) {
            return distance + context.getString(R.string.unit_m);
        } else if (distance < 1000 * 1000) {
            distance /= 1000f;
            return String.format("%.1f", distance) + context.getString(R.string.unit_km);
        } else if (distance < 1000 * 1000 * 1000) {
            distance /= (1000f * 1000f);
            return String.format("%.1f", distance) + context.getString(R.string.unit_qian_km);
        } else {
            distance /= (1000 * 1000 * 1000);
            return String.format("%.1f", distance) + context.getString(R.string.unit_wan_km);
        }
    }

    /**
     * 解析字符串为整型
     *
     * @param strNum 字符串
     * @return 整型
     */
    public static int parseInt(String strNum) {
        if (TextUtils.isEmpty(strNum))
            return 0;

        int pIndex = strNum.indexOf(".");
        if (pIndex > 0) {
            strNum = strNum.substring(0, pIndex);
        }

        int num = 0;
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 解析字符串为float
     *
     * @param strNum 字符串
     * @return float
     */
    public static float parseFloat(String strNum) {
        if (TextUtils.isEmpty(strNum))
            return 0;

        float num = 0;
        try {
            num = Float.parseFloat(strNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 解析字符串为double
     *
     * @param strNum 字符串
     * @return double
     */
    public static double parseDouble(String strNum) {
        if (TextUtils.isEmpty(strNum))
            return 0;

        double num = 0;
        try {
            num = Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }

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

}
