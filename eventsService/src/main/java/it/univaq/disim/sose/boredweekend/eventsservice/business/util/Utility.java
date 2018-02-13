package it.univaq.disim.sose.boredweekend.eventsservice.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static String dateTranslate(Date date) {

//		String year = date.toString().substring(24, 28);
//		String month;
//		int month_int = date.getMonth();
//
//		if (9 <= month_int) {
//			month_int = month_int + 1;
//			month = Integer.toString(month_int);
//		} else {
//			month_int += 1;
//			month = "0" + Integer.toString(month_int);
//		}
//
//		String day = date.toString().substring(8, 10);
//		String date_final = year + "-" + month + "-" + day;
//		String time = date.toString().substring(11, 19);
//		String start_day_time = date_final + " " + time;
//
//		return start_day_time;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}

}
