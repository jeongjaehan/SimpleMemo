package kr.kakaruto.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	public static String getDateString(String format , Locale locale){
		SimpleDateFormat formatter = new SimpleDateFormat ( format, locale);
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		
		return dTime;
	}
}
