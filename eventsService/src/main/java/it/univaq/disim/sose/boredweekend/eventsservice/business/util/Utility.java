package it.univaq.disim.sose.boredweekend.eventsservice.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static String date2Mysql(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}

}
