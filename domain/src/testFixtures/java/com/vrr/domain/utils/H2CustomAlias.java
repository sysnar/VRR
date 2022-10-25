package com.vrr.domain.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class H2CustomAlias {

    public static String date_format(LocalDateTime date, String mysqlFormatPattern) {
        if (date == null) return null;
        String dateFormatPattern = mysqlFormatPattern
                .replace("%Y", "yyyy")
                .replace("%m", "MM")
                .replace("%d", "dd")
                .replace("%H", "HH")
                .replace("%i", "mm")
                .replace("%s", "ss");
        return date.toInstant(ZoneOffset.UTC)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().format(DateTimeFormatter.ofPattern(dateFormatPattern));
    }
}
