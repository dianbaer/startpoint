
package tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	public static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		return longDateFormat.format(date);
	}
}
