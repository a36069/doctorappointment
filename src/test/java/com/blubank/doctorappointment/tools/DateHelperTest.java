package com.blubank.doctorappointment.tools;

import com.blubank.doctorappointment.Exception.IntervalDateException;
import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {
    private static final String dateStart = "2022/12/01 10:00:00";
    private static final String dateEnd = "2022/12/01 13:00:00";

    @Test
    void setIntervalNull() {
        Exception exception = assertThrows(IntervalDateException.class, () -> DateHelper.setIntervalsDate(null, null, 30, Calendar.MINUTE));
        String expectedMessage = "The start time cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void setIntervalNormalWithDate() {
        List<AppointmentEntity> intervalList = DateHelper.setIntervalsDate(dateStart, dateEnd, 30, Calendar.MINUTE);
        assertEquals(6, intervalList.size());
    }

    @Test
    void setIntervalAfterDate() {
        Exception exception = assertThrows(IntervalDateException.class, () -> DateHelper.setIntervalsDate(dateEnd, dateStart, 30, Calendar.MINUTE));
        String expectedMessage = "The start time cannot be after the end time";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}