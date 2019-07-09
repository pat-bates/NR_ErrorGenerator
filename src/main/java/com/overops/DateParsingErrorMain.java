package com.overops;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.RandomUtil;

public class DateParsingErrorMain {
	
	private final static Logger log = LoggerFactory.getLogger(DateParsingErrorMain.class);
	static List<String> dateList = new ArrayList<String>();

	public static void main(String[] args) {
		int errorCount = 0;
		Integer numOfErrors = Integer.parseInt(args.length == 0 ? "1000": args[0]);
		
		try {
			//Sleep for 10 seconds to allow OverOps to warm up
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setupDates();

		for (int i = 0; i < numOfErrors; i++) {
			try {
				Thread.sleep(100);
				long value = RandomUtil.convertDate(getRandomDate());
				if (value == 0) {
					errorCount++;
				}
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error("Error converting date, " + convertStackTraceToString(e));
				errorCount++;
			}
		}
		
		log.info("Total errors this run is: " + errorCount);
		
	}
	
	private static String getRandomDate () throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(dateList);
		return returnString;
	}
	
	private static void setupDates() {
		dateList.add("Da Bomb");
		dateList.add("02/12/2017");
		dateList.add("2012-10-10");
		dateList.add("1/1/2015 11:22:22");
		dateList.add("Boom Daddy");
	}
	
	private static String convertStackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		return exceptionAsString;
	}
}
