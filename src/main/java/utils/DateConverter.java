package utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateConverter {
    private DateConverter() {}

    /**
     * Convert a string of format "YYYY-MM-DD" to a <code>LocalDate</code>
     */
    public static LocalDate convertStringToLocalDate(String date) {
        String[] array = date.split("-");

        return LocalDate.of(
            Integer.parseInt(array[0]),
            Integer.parseInt(array[1]),
            Integer.parseInt(array[2])
        );
    }

    /**
     * Convert a string of format "HH:MM" to a <code>LocalTime</code>
     */
    public static LocalTime convertStringToLocalTime(String time) {
        String[] array = time.split(":");

        return LocalTime.of(
            Integer.parseInt(array[0]),
            Integer.parseInt(array[1])
        );
    }
}
