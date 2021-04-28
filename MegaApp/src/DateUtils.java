import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    Date date = new Date(); 
	    return formatter.format(date);
	}
	
	public static String getDateDifference(String olderDate , String newerDate) {
		long startTime = getlongDate(newerDate);
		long endTime = getlongDate(olderDate);
		long diffTime = startTime - endTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		return String.valueOf(diffDays);
	}

	private static long getlongDate(String dateStr) {
		Calendar cal = Calendar.getInstance();
		LocalDate localDate;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			localDate = LocalDate.parse(dateStr, formatter);
		} catch (Exception e) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
			localDate = LocalDate.parse(dateStr, formatter);
		}
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int dayOfMonth = localDate.getDayOfMonth();

		cal.set(year, month, dayOfMonth);
		Date date = cal.getTime();
		return date.getTime();
	}

}
