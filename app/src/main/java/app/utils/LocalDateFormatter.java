package app.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class LocalDateFormatter {
    private static final SimpleDateFormat dateFormatter;
    private static final SimpleDateFormat dateTimeFormatter;

    static {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }

    public static String getFormattedDate(Date date) {
        return dateFormatter.format(date);
    }

    public static String getFormattedDate(long timeMillis) {
        return dateFormatter.format(new Date(timeMillis));
    }

    public static String getFormattedDateTime(Date timestamp) {
        return dateTimeFormatter.format(timestamp);
    }

    public static String getFormattedDateTime(long timeMillis) {
        return dateTimeFormatter.format(new Date(timeMillis));
    }

    public static Date parseDate(String date) throws ParseException {
        return dateFormatter.parse(date);
    }

    public static Date parseDateTime(String dateTime) throws ParseException {
        return dateTimeFormatter.parse(dateTime);
    }

    public String getDateFormat() {
        return dateFormatter.toPattern();
    }

    public String getDateTimeFormat() {
        return dateTimeFormatter.toPattern();
    }

}
