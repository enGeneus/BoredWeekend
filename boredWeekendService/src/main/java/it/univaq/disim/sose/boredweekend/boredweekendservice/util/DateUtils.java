package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {

	private static final String[] DAYLIST = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

	public static List<String> extractListFromConcatenatedValues(String concatenatedValues) {
		String[] values = concatenatedValues.split(",");
		return Arrays.asList(values);
	}

	public static Date getDateFromString(String inputString, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern); 
		Date date = null;
		try {
			date = dateFormat.parse(inputString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getWeekdayName(Date date) {
		SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		return weekdayFormat.format(date);
	}

	public static List<String> getWeekdaysInInterval(Date startDate, Date endDate) {

		SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		SimpleDateFormat yearWeekFormat = new SimpleDateFormat("w");
		String startDay = weekdayFormat.format(startDate);
		String startWeek = yearWeekFormat.format(startDate);
		String endDay = weekdayFormat.format(endDate);
		String endWeek = yearWeekFormat.format(endDate);

		List<String> dayList = Arrays.asList(DAYLIST);
		
		if (dayList.indexOf(endDay)>=dayList.indexOf(startDay) && !startWeek.equals(endWeek)) {
			return dayList;
		
		} else {
			List<String> weekdays = new ArrayList<>();

			if (startDay.equals(endDay)) {
				weekdays.add(startDay);
			
			} else {
			
				boolean inInterval = false;
				int i=0;

				while (weekdays.size()<7) {
					String currentDay = DAYLIST[i];

					if (currentDay.equals(startDay)) {
						inInterval = true;
					}

					if (inInterval) {
						weekdays.add(currentDay);
					}

					if (inInterval && currentDay.equals(endDay)) {
						break;
					}

					i++;
					i = i%7;
				}
			}
			return weekdays;
		}
	}


	public static List<Date> getDaysBetweenDates(Date startDate, Date endDate) {
	    List<Date> dates = new ArrayList<Date>();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(startDate);

	    while (calendar.getTime().before(endDate)) {
	        Date result = calendar.getTime();
	        dates.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }
	    dates.add(calendar.getTime());
	    return dates;
	}

	public static String getDateSimpleRepresentation(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
}
