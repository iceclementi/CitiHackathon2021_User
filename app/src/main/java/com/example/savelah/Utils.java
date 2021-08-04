package com.example.savelah;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public static String getDate() {
        return df.format(Calendar.getInstance().getTime());
    }

    public static String getExpiryDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);

        return df.format(calendar.getTime());
    }

    public static long getDaysTo(String date) {
        try {
            Date startDate = Calendar.getInstance().getTime();
            Date endDate = df.parse(date);
            long difference = endDate.getTime() - startDate.getTime();
            return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return -1;
        }
    }

    public static String generateRandomString(int length) {
        final int lowerLimit = 48; // '0'
        final int upperLimit = 122; // 'z'

        Random random = new Random();

        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = lowerLimit + (int)
                    (random.nextFloat() * (upperLimit - lowerLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
