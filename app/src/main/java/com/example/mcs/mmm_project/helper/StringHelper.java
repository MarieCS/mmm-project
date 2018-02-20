package com.example.mcs.mmm_project.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StringHelper {
    public static boolean empty(String... str) {
        boolean empty = false;
        for (String s : str) {
            if (s == null || s.length() == 0) {
                empty = true;
            }
        }
        return empty;
    }

    public static boolean notEmpty(String... str) {
        return !empty(str);
    }

    public static String toDate(Calendar calendar, String toFormat) {
        return new SimpleDateFormat(toFormat, Locale.FRANCE).format(calendar.getTime());
    }
}
