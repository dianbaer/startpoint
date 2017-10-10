
package tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import log.LogManager;

public class TimeUtils {
	public static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		return longDateFormat.format(date);
	}

	public static Date stringToDate(String time) {
		if (StringUtil.stringIsNull(time)) {
			return null;
		}
		Date date;
		try {
			date = longDateFormat.parse(time);
			return date;
		} catch (ParseException e) {
			LogManager.initLog.error("TimeUtils.stringToDate error", e);
			return null;
		}

	}
}
