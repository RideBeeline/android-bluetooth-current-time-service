package co.beeline.android.bluetooth.currenttimeservice;

/* Copyright (C) 2017 Relish Technologies Ltd. - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license.
 *
 * You should have received a copy of the MIT license with
 * this file. If not, please visit https://opensource.org/licenses/MIT
 */

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class TimeDataTest {

    private static final int ONE_HOUR = 60 * 60 * 1000;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd MMM yyyy", Locale.ENGLISH);
    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void exactTime256WithUpdateReason_millennium() {
        assertArrayEquals(
                new byte[]{-48, 7, 1, 1, 0, 0, 0, 6, 0, 0},
                TimeData.exactTime256WithUpdateReason(utc("00:00:00 01 Jan 2000"), TimeData.UPDATE_REASON_UNKNOWN)
        );
    }

    @Test
    public void exactTime256WithUpdateReason_stGeorgesDay2017_manualUpdate() {
        assertArrayEquals(
                new byte[]{-31, 7, 4, 23, 13, 36, 59, 7, 0, 1},
                TimeData.exactTime256WithUpdateReason(utc("13:36:59 23 Apr 2017"), TimeData.UPDATE_REASON_MANUAL)
        );
    }

    @Test
    public void timeZoneWithDstOffset_UTC() {
        assertArrayEquals(
                new byte[] {0, 0},
                TimeData.timezoneWithDstOffset(Calendar.getInstance(TimeZone.getTimeZone("UTC")))
        );
    }

    @Test
    public void timeZoneWithDstOffset_SriLanka() {
        assertArrayEquals(
                new byte[] {22, 0},
                TimeData.timezoneWithDstOffset(Calendar.getInstance(TimeZone.getTimeZone("Asia/Colombo")))
        );
    }

    @Test
    public void timeZoneWithDstOffset_BST() {
        Calendar bst = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        bst.set(Calendar.MONTH, Calendar.JUNE);
        bst.set(Calendar.DST_OFFSET, ONE_HOUR);
        assertArrayEquals(
                new byte[] {0, 4},
                TimeData.timezoneWithDstOffset(bst)
        );
    }

    private static Calendar utc(String dateTimeStr) {
        try {
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateTime.setTime(DATE_FORMAT.parse(dateTimeStr));
            return dateTime;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}