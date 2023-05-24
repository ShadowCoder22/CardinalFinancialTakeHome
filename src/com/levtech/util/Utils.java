package com.levtech.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;

public class Utils {
    private static final DayOfWeek[] WEEKEND = {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    public static DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    }

    public static boolean isWeekend(LocalDate date) {
        return Arrays.stream(WEEKEND).anyMatch(d -> d.equals(date.getDayOfWeek()));
    }

    public static boolean isHoliday(LocalDate date) {
        return isFourthOFJuly(date) || isLaborDay(date);
    }

    private static boolean isFourthOFJuly(LocalDate date) {
        return (date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 4 && !isWeekend(date)) ||
                (date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 3 && date.getDayOfWeek().equals(DayOfWeek.FRIDAY) )||
                (date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 5 && date.getDayOfWeek().equals(DayOfWeek.MONDAY));
    }

    private static boolean isLaborDay(LocalDate date) {
        return date.getMonth().equals(Month.SEPTEMBER) && date.getDayOfWeek().equals(DayOfWeek.MONDAY) && date.getDayOfMonth() <= 7;
    }
}