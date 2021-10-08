package com.alpha.unittesting.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String TAG = "Utility";

    public static final String[] monthNumbers = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    public static final String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    public static final String GET_MONTH_ERROR = "Error. Invalid month number.";
    public static final String DATE_FORMAT = "MM/yyyy";

    public static String getCurrentTimeStamp() throws Exception{
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Couldn't format the date into MM/yyyy");
        }
    }

    public static String getMonthFromNumber(String monthNumber){
        if(monthNumber.matches("0?[1-9]\\d*")){
            int monthIndex = Integer.parseInt(monthNumber);
            if(monthIndex <= 12)
                return months[monthIndex - 1];
        }

        return GET_MONTH_ERROR;
    }
}
