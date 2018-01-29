package com.longfor.channelmanager;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parseStr2Date() {
        try {
            Calendar testCalendar = Calendar.getInstance();
            String testStr = "2018-01-01";
            SimpleDateFormat testSDF = new SimpleDateFormat("yyyy-MM-dd");
            testCalendar.setTime(testSDF.parse(testStr));

            Calendar todayLastMinute = Calendar.getInstance();
            todayLastMinute.set(Calendar.HOUR_OF_DAY, 23);
            todayLastMinute.set(Calendar.MINUTE, 59);
            todayLastMinute.set(Calendar.SECOND, 59);
            System.out.println(String.valueOf(testCalendar.before(todayLastMinute)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}