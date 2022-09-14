package com.dealsdelta.scheduleme.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalTime;
import java.util.Calendar;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class DateTimeUtilTest {

    @Test
    public void getTime() {
        assertNotNull(new DateTimeUtil());
        String time = "15:35";
        LocalTime localTime = LocalTime.parse(time);
        assertEquals(time, DateTimeUtil.getTime(localTime));
    }

    @Test
    public void getCalenderInstance() {
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH),
            DateTimeUtil.getCalenderInstance().get(Calendar.DAY_OF_MONTH));
    }
}