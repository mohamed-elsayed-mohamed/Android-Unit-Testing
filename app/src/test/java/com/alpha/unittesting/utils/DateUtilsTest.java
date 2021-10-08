package com.alpha.unittesting.utils;

import org.junit.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static com.alpha.unittesting.utils.DateUtils.*;
import static com.alpha.unittesting.utils.DateUtils.monthNumbers;
import static com.alpha.unittesting.utils.DateUtils.months;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {
    public static final String today = "10/2021";

    @Test
    public void getCurrentTimeStamp_timestamp(){
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                assertEquals(today, getCurrentTimeStamp());
                print("Timestamp is generated correctly");
            }
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    public void getMonthFromNumber_success(int monthNumber, TestInfo testInfo, TestReporter testReporter){
        assertEquals(months[monthNumber], getMonthFromNumber(monthNumbers[monthNumber]));
        print(monthNumbers[monthNumber] + " : " + months[monthNumber]);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    public void getMonthFromNumber_error(int monthNumber, TestInfo testInfo, TestReporter testReporter){
        int randomNumber = new Random().nextInt(90) + 13;
        assertEquals(getMonthFromNumber(String.valueOf(monthNumber * randomNumber)), GET_MONTH_ERROR);
        print(monthNumbers[monthNumber] + " : " + GET_MONTH_ERROR);
    }

    private void print(String message){
        System.out.print(message);
    }
}