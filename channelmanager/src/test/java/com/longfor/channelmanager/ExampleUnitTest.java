package com.longfor.channelmanager;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public void parseStr2Date(){
//        2018-01-29
        try {
//            String string = "2016-10-24 21:59:06";
            // TODO: 2018/1/29 test
            String string = "2018-01-29";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}