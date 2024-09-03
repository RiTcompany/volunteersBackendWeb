package org.example.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int age(Date birthdayDate) {
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(birthdayDate);

        return today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
    }
}
