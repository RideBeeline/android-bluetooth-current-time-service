package co.beeline.android.bluetooth.currenttimeservice;

/* Copyright (C) 2017 Relish Technologies Ltd. - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license.
 *
 * You should have received a copy of the MIT license with
 * this file. If not, please visit https://opensource.org/licenses/MIT
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;

class TimeData {

    private TimeData() {}

    static byte UPDATE_REASON_UNKNOWN = 0;
    static byte UPDATE_REASON_MANUAL = 1;
    static byte UPDATE_REASON_EXTERNAL_REF = (1 << 1);
    static byte UPDATE_REASON_TIME_ZONE_CHANGE = (1 << 2);
    static byte UPDATE_REASON_DAYLIGHT_SAVING = (1 << 3);

    static byte[] exactTime256WithUpdateReason(Calendar time, byte updateReason) {
        // See https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.current_time.xml

        // Calendar: January = 0
        // CTS: January = 1
        int month = time.get(Calendar.MONTH) + 1;

        // Calendar: Monday = 2, Sunday = 1
        // CTS: Monday = 1, Sunday = 7
        int dayOfWeek = time.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }

        // 256ths of a second
        int fractionsOfSecond = (int)((time).get(Calendar.MILLISECOND) * 0.255F);

        return ByteBuffer.allocate(10)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) time.get(Calendar.YEAR))
                .put((byte) month)
                .put((byte) time.get(Calendar.DAY_OF_MONTH))
                .put((byte) time.get(Calendar.HOUR_OF_DAY))
                .put((byte) time.get(Calendar.MINUTE))
                .put((byte) time.get(Calendar.SECOND))
                .put((byte) dayOfWeek)
                .put((byte) fractionsOfSecond)
                .put(updateReason)
                .array();
    }

    static byte[] timezoneWithDstOffset(Calendar time) {
        // See https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.local_time_information.xml

        // UTC-1:00 = -4
        // UTC+0:00 = 0
        // UTC+1:00 = 4
        int timezone = time.get(Calendar.ZONE_OFFSET) / 1000 / 60 / 15;
        int dstOffset = time.get(Calendar.DST_OFFSET) / 1000 / 60 / 15;

        return ByteBuffer.allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put((byte) timezone)
                .put((byte) dstOffset)
                .array();
    }

}
