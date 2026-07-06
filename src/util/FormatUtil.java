package util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    private static final DateTimeFormatter DATE_FMT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_FMT =
        NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private FormatUtil() {}

    public static String formatDate(LocalDate date) {
        return (date == null) ? "-" : date.format(DATE_FMT);
    }

    public static String formatCurrency(double amount) {
        return CURRENCY_FMT.format(amount);
    }

    public static LocalDate parseDate(String text) {
        if (text == null || text.isBlank()) return null;
        return LocalDate.parse(text.trim(), DATE_FMT);
    }
}