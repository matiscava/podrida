package com.example.podrida.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static LocalDate convertDateToLocalDate (Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertLocalDateToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static int getLimitCard(int handNumber){
        int cardLimit = 0;
        if (handNumber == 1 || handNumber == 21 ) cardLimit = 1;
        if (handNumber == 2 || handNumber == 20 ) cardLimit = 2;
        if (handNumber == 3 || handNumber == 19 ) cardLimit = 3;
        if (handNumber == 4 || handNumber == 18 ) cardLimit = 4;
        if (handNumber == 5 || handNumber == 17 ) cardLimit = 5;
        if (handNumber == 6 || handNumber == 16 ) cardLimit = 6;
        if (handNumber > 6 && handNumber < 16 ) cardLimit = 7;
        return cardLimit;
    }
}
