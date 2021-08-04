package com.example.ahben;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM-yyyy");

    public static String getDate() {
        return df.format(Calendar.getInstance().getTime());
    }

    public static String getExpiryDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);

        return df.format(calendar.getTime());
    }


}
