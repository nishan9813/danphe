package com.example.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class DateUtils {

    public static synchronized long getTimeAsNanoSecond() {
        return System.nanoTime();
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static String getDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    public static String now() {
        return LocalDateTime.now().toString();
    }

    public static String getTodayAsUSFormat() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return now.format(formatter);
    }

    public static String getTodayAsNormal() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    public static String toISODateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalDateTime toLocalDateTime(String stringDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(stringDate, formatter);
    }

    public static LocalDateTime toNowLocalDateTime(String time) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return LocalDateTime.parse(
                currentDate + " " + time, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }

    public static LocalDateTime toISODate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(stringDate, formatter);
    }

    public static LocalDateTime toStartOfDay() {
        return toNowLocalDateTime("00:00:00");
    }

    public static LocalDateTime toEndOfDay() {
        return toNowLocalDateTime("23:59:59");
    }

    public static LocalDateTime toStartLocalDateTime() {
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime toEndLocalDateTime() {
        return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
    }

    public static LocalDateTime toLastMonthLocalDateTime() {
        return LocalDateTime.now().minusDays(30L).withHour(0).withMinute(0).withSecond(0);
    }

    public static Boolean isValidDate(String date, String pattern) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException var2) {
            return false;
        }

        return true;
    }

    public static LocalDate toLocalDate(String stringDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(stringDate, formatter);
    }

    public static boolean isValidTime(String time, String format) {
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static String startOfDate(String date) {
        try {
            return toLocalDateTime(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss").toString();
        } catch (Exception var2) {
            throw new IllegalArgumentException("Invalid Date");
        }
    }

    public static String endOfDate(String date) {
        try {
            return toLocalDateTime(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss").toString();
        } catch (Exception var2) {
            throw new IllegalArgumentException("Invalid Date");
        }
    }

    public static boolean isValidYear(String input) {
        DateTimeFormatter formatter =
                new DateTimeFormatterBuilder()
                        .appendPattern("yyyy")
                        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                        .toFormatter();
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate getToday() {
        return LocalDate.now();
    }

    public static LocalDate toDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime toTime(String time, String pattern) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static String getTime(String time, String format, int duration) {
        try {
            return toTime(time, format).plusMinutes(duration).toString();
        } catch (Exception var3) {
            throw new RuntimeException("Time Error");
        }
    }

    public static LocalDateTime toDateTime(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static boolean isTodayBetween(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isBefore(toDate) || fromDate.isEqual(toDate)) {
            if (isToday(fromDate) || isToday(toDate)) {
                return true;
            }
            return isAfterToday(toDate) && isBeforeToday(fromDate);
        }
        return false;
    }

    public static boolean isAfterToday(LocalDate date) {
        return date.isAfter(getToday());
    }

    public static boolean isPastIncludingToday(LocalDate date) {
        return isToday(date) || date.isBefore(getToday());
    }

    public static boolean isPast(LocalDate date) {
        return date.isBefore(getToday());
    }

    public static boolean isFutureIncludingToday(LocalDate date) {
        return isToday(date) || date.isAfter(getToday());
    }

    public static String capitalize(String value) {
        if (StringUtils.isBlankOrNull(value)) {
            return "";
        }
        String[] values = value.split(" ");
        StringBuilder builder = new StringBuilder();
        Arrays.stream(values)
                .forEach(
                        val -> {
                            builder.append(StringUtils.capitalize(val.toLowerCase()));
                            builder.append(" ");
                        });

        return builder.toString().trim();
    }

    public static boolean isBeforeToday(LocalDate date) {
        return date.isBefore(getToday());
    }

    public static boolean isToday(LocalDate date) {
        return date.isEqual(getToday());
    }

    public static boolean isExpired(String date) {
        LocalDateTime tokenIssueDate = LocalDateTime.parse(date);
        LocalDateTime tokenExpireDate = tokenIssueDate.plusMinutes(10L);
        LocalDateTime currentTime = LocalDateTime.now();
        return !tokenIssueDate.isBefore(currentTime) || !currentTime.isBefore(tokenExpireDate);
    }

    public static String yearStart(String year) {
        if (!StringUtils.isBlankOrNull(year) && DateUtils.isValidYear(year)) {
            return year + "-01-01 00:00:00";
        }
        throw new RuntimeException("ERROR");
    }

    public static String yearEnd(String year) {
        if (!StringUtils.isBlankOrNull(year) && DateUtils.isValidYear(year)) {
            return year + "-12-31 23:59:59";
        }
        throw new RuntimeException("ERROR");
    }

    public static boolean isBefore(LocalDateTime d1, LocalDateTime d2) {
        return d1.isBefore(d2);
    }

    public static boolean isAfter(LocalDateTime d1, LocalDateTime d2) {
        return d1.isAfter(d2);
    }

    public static boolean isBeforeIncludeToday(LocalDateTime d1, LocalDateTime d2) {
        return d1.isBefore(d2) || d1.equals(d2);
    }

    public static String getDateString(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String getLocalDateTimeString(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(formatter);
    }

    public static LocalDateTime getFormatterLocalDateTime(String pattern) {
        return LocalDateTime.parse(
                getLocalDateTimeString(pattern), DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCreditCardExpiryDate(String date) {
        if (!StringUtils.isBlankOrNull(date)) {
            try {
                date = date.trim().replace(" ", "");
                Date date1 = new SimpleDateFormat("MM/yy").parse(date);
                DateFormat s = new SimpleDateFormat("yyyy-MM");
                return s.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static int getDays(String lastDate) {
        if (lastDate.length() > 19) lastDate = lastDate.substring(0, 19);
        LocalDateTime date1 = toLocalDateTime(lastDate, "yyyy-MM-dd HH:mm:ss");
        LocalDateTime date2 = LocalDateTime.now();
        return (int) Duration.between(date1, date2).toDays();
    }

    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static String getDay() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public static String getMonthName() {
        Formatter formatter = new Formatter();
        return String.valueOf(formatter.format("%tB", Calendar.getInstance()));
    }

    public static long getTimeDifference(LocalDateTime startDate, LocalDateTime endDate) {
        long totalSeconds = 0;
        Duration duration = Duration.between(startDate, endDate);
        totalSeconds += duration.getSeconds();
        return totalSeconds;
    }

    public static String getTimeFormat(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
