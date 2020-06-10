package app.utils;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class LocalDateFormatter {
    private static final SimpleDateFormat dateFormatter;
    private static final SimpleDateFormat timestampFormatter;

    static {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        timestampFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }

    public static String getFormattedDate(Date date) {
        return dateFormatter.format(date);
    }

    public static String getFormattedDate(long timeMillis) {
        return dateFormatter.format(new Date(timeMillis));
    }

    public static String getFormattedTimestamp(Timestamp timestamp) {
        return timestampFormatter.format(timestamp);
    }

    public static String getFormattedTimestamp(long timeMillis) {
        return timestampFormatter.format(new Date(timeMillis));
    }

    public String getDateFormat() {
        return dateFormatter.toPattern();
    }

    public String getDateTimeFormat() {
        return timestampFormatter.toPattern();
    }

}
