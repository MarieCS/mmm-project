package com.example.mcs.mmm_project.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;

public class DateHelper {
    public static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final int ISO8601_LENGTH = ISO8601_PATTERN.length() + 1;

    public static String toDate(Calendar calendar, String toFormat) {
        return new SimpleDateFormat(toFormat, Locale.FRANCE).format(calendar.getTime());
    }

    public static Calendar getFrom(String isoDate) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(ISO8601_PATTERN, Locale.FRANCE);
        try {
            cal.setTime(sdf.parse(isoDate));
            return cal;
        } catch (ParseException e) {
            Logger.getGlobal().warning(e.toString());
            return null;
        }
    }
}
