package com.longfor.ui.view.editwatcher.utils;

/**
 * @author: tongzhenhua
 * @date: 2017/11/30
 * @function:
 */
public class TextWatcherFormatUtil {

    /**
     * 字符串转换为银行卡格式
     * @param s
     * @return
     */
    public static String toCardFormat(String s) {
        String newStr = s.toString();
        newStr = newStr.replace(" ", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newStr.length(); i += 4) {
            if (i > 0) {
                sb.append(" ");
            }
            if (i + 4 <= newStr.length()) {
                sb.append(newStr.substring(i, i + 4));
            } else {
                sb.append(newStr.substring(i, newStr.length()));
            }
        }
        return sb.toString();
    }
}
