package com.demo.session1;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class LoginDurationUtil {

	private static final String ZERO = "0";
	private static final String TIMESTAMP_SEPARATOR = ":";
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println(
				"Last logged in duration - " + calculateDuration("2020-12-28 03:50:50", "2020-12-28 03:52:51"));
		
		System.out.println("Time taken to calculate - "+(System.currentTimeMillis()-startTime)+" ms");
	}

	private static String calculateDuration(String firstLocalDateTimeString, String secondLocalDateTimeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

		LocalDateTime firstLocalDateTime = LocalDateTime.parse(firstLocalDateTimeString, formatter);
		LocalDateTime secondLocalDateTime = LocalDateTime.parse(secondLocalDateTimeString, formatter);

		long[] chronoUnits = new long[6];
		if (secondLocalDateTime.isAfter(firstLocalDateTime)) {
			calculateDuration(firstLocalDateTime, secondLocalDateTime, chronoUnits);
		} else {
			calculateDuration(secondLocalDateTime, firstLocalDateTime, chronoUnits);
		}

//	    System.out.println("Difference - \n");
//	    System.out.println("Years - "+chronoUnits[0]);
//	    System.out.println("Months - "+chronoUnits[1]);
//	    System.out.println("Days - "+chronoUnits[2]);
//	    System.out.println("Hours - "+chronoUnits[3]);
//	    System.out.println("Minutes - "+chronoUnits[4]);
//	    System.out.println("Seconds - "+chronoUnits[5]);
//	    System.out.println("-------------------------------- \n");

		StringBuilder timeDiffBuilder = new StringBuilder();
		timeDiffBuilder
				.append((chronoUnits[2] * 24 + chronoUnits[3]) < 10 ? ZERO + (chronoUnits[2] * 24 + chronoUnits[3])
						: (chronoUnits[2] * 24 + chronoUnits[3]));
		timeDiffBuilder.append(TIMESTAMP_SEPARATOR);
		timeDiffBuilder.append((chronoUnits[4] < 10) ? ZERO + chronoUnits[4] : chronoUnits[4]);
		timeDiffBuilder.append(TIMESTAMP_SEPARATOR);
		timeDiffBuilder.append((chronoUnits[5] < 10) ? ZERO + chronoUnits[5] : chronoUnits[5]);

		return timeDiffBuilder.toString();

	}

	private static void calculateDuration(LocalDateTime firstLocalDateTime, LocalDateTime secondLocalDateTime,
			long[] chronoUnits) {
		/* Separate LocaldateTime on LocalDate and LocalTime */
		LocalDate firstLocalDate = firstLocalDateTime.toLocalDate();
		LocalTime firstLocalTime = firstLocalDateTime.toLocalTime();

		LocalDate secondLocalDate = secondLocalDateTime.toLocalDate();
		LocalTime secondLocalTime = secondLocalDateTime.toLocalTime();

		/* Calculate the time difference */
		Duration duration = Duration.between(firstLocalDateTime, secondLocalDateTime);
		long durationDays = duration.toDays();
		Duration throughoutTheDayDuration = duration.minusDays(durationDays);

//	    System.out.println("Duration is: " + duration + " this is " + durationDays
//	            + " days and " + throughoutTheDayDuration + " time.");

		Period period = Period.between(firstLocalDate, secondLocalDate);

		/* Correct the date difference */
		if (secondLocalTime.isBefore(firstLocalTime)) {
			period = period.minusDays(1);
//	        System.out.println("minus 1 day");
		}

//	    System.out.println("Period between " + firstLocalDateTime + " and "
//	            + secondLocalDateTime + " is: " + period + " and duration is: "
//	            + throughoutTheDayDuration
//	            + "\n-----------------------------------------------------------------");

		/* Calculate chrono unit values and write it in array */
		chronoUnits[0] = period.getYears();
		chronoUnits[1] = period.getMonths();
		chronoUnits[2] = period.getDays();
		chronoUnits[3] = throughoutTheDayDuration.toHours();
		chronoUnits[4] = throughoutTheDayDuration.toMinutes() % 60;
		chronoUnits[5] = throughoutTheDayDuration.getSeconds() % 60;

	}
}
