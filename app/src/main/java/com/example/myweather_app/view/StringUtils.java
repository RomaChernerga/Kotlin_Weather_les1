package com.example.myweather_app.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    private StringUtils() {
    }

    public static String toDateString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(date);
    }
}
