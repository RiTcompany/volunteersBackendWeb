package org.example.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int age(Date birthdayDate) {
//        Calendar today = Calendar.getInstance();
//        Calendar birthday = Calendar.getInstance();
//        birthday.setTime(birthdayDate);

        return Period.between(
                birthdayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        ).getYears();
//        return today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
    }
}
