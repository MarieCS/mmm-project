package com.example.mcs.mmm_project.helper;

import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import android.content.Context;

import com.example.mcs.mmm_project.MainActivity;

public class StringHelper {
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

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

    /**
     * @return Retourne l'identifiant unique du téléphone (changera en cas de factory reset)
     */
    public synchronized static String getUniqueID() {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs;
            try {
                sharedPrefs = MainActivity.getAppContext().getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
                uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            } catch (Exception e) {
                return "NO_UNIQUE_ID";
            }

            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }

        return uniqueID;
    }
}
