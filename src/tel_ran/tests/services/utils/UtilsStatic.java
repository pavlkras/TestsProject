package tel_ran.tests.services.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UtilsStatic {
	
	
	
	public static String getDuration(long timeStart, long timeEnd) { 
		
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		Date dt1 = new Date(timeStart);
		
		Date dt2 = new Date(timeEnd);
				
		cl.setTimeInMillis(0L);
			
		cl.setTimeInMillis(dt2.getTime()-dt1.getTime());
		
		int year = cl.get(Calendar.YEAR) - 1970;
		int days = cl.get(Calendar.DAY_OF_YEAR) - 1;
		if(year > 0) {
			days = 365*year + days;
		}
		int hour = cl.get(Calendar.HOUR_OF_DAY);
		int min = cl.get(Calendar.MINUTE);
		int sec = cl.get(Calendar.SECOND);
		
		StringBuilder result = new StringBuilder("");
		
		if(days>0) 
			result.append(days).append("d. ");
		if (hour<10)
			result.append(0);
		result.append(hour).append(":");
		if(min<10)
			result.append(0);
		result.append(min).append(":");
		if(sec<10)
			result.append(0);
		result.append(sec);
		
		return result.toString();
	}

}
