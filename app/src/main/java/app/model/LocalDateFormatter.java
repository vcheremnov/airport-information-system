package app.model;

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
        timestampFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    }

    public static String getFormattedDate(Date date) {
        return dateFormatter.format(date);
    }

    public static String getFormattedTimestamp(Timestamp timestamp) {
        return timestampFormatter.format(timestamp);
    }
}
