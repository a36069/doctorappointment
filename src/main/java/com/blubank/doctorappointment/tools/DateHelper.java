package com.blubank.doctorappointment.tools;

import com.blubank.doctorappointment.Exception.IntervalDateException;
import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {

    public static List<AppointmentEntity> setIntervalsDate(String start, String stop, int amount, int unit) throws IntervalDateException {
       if (start==null)
           throw new IntervalDateException("The start time cannot be null");
       if (stop==null)
           throw new IntervalDateException("The start time cannot be null");

        String strStart;
        String strEnd;
        List<AppointmentEntity> arrayList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date dStart = null;
        Date dStop = null;
        try {
            dStart = df.parse(start);
            dStop = df.parse(stop);
        } catch (ParseException e) {
            throw new IntervalDateException(e);
        }
        if (dStop.before(dStart))
           throw new IntervalDateException("The start time cannot be after the end time");

        Calendar cal = Calendar.getInstance();
        cal.setTime(dStart);
        while (cal.getTime().before(dStop)) {
            strStart = df.format(cal.getTime());
            cal.add(unit, amount);
            Date dateEnd = cal.getTime();
            if (dateEnd.before(dStop) || dateEnd.equals(dStop)) {
                strEnd = df.format(dateEnd);
                arrayList.add(new AppointmentEntity(strStart, strEnd));
            }
        }
        return arrayList;
    }
}
