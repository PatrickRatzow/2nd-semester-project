package util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SQLDateConverter {
    public static Timestamp localDateTimeToTimestamp(final LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    public static LocalDateTime timestampToLocalDateTime(final Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static Date localDateToDate(final LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}