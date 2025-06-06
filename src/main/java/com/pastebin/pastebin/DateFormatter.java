package com.pastebin.pastebin;

import com.pastebin.pastebin.exeption.ErrorList;
import com.pastebin.pastebin.exeption.ServerException;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateFormatter {

    public static Date format(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return formatter.parse(date);
        }
        catch (Exception e) {
            throw new ServerException("Invalid date format", ErrorList.INVALID_DATE_FORMAT);
        }

    }
}
