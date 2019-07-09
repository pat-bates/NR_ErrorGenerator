package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class NullErrorUtil {
	private final static Logger log = LoggerFactory.getLogger(NullErrorUtil.class);
	
	public static void testErrors (int numOfIterations) {
		List<String> dataList = setupData();
		log.debug("Random NullPointer Error generation for {} iterations ", numOfIterations);
		
		for (int i = 0; i < numOfIterations; i++) {	
			sleep();
			int value = RandomUtil.generateRandom(4);
			log.debug("Case number is {}", value);
			switch (value) {
				case 0 :
					testCaughtNulls(getRandomString(dataList));
					break;
				case 1 : 
					// try {
						testUncaughtNulls(getRandomString(dataList));
					/*
					} catch (Exception e) {
						log.error("This error was caught", RandomUtil.convertStackTraceToString(e));
						NewRelic.noticeError(e, false);
					}
					*/
					break;
				case 2 :
					testSwallowedNulls(getRandomString(dataList));
					break;
				case 3 :
					intermediateMethod(getRandomString(dataList));
					break;
			}
		}
	}
	
	private static void intermediateMethod (String value) {
		testCaughtNulls(value);
	}
	/*
	 * This creates a null pointer that is caught and logged
	 */
	private static void testCaughtNulls (String value) {
		try {
			if (value.equalsIgnoreCase("hello")) {
				//matching logic would go here
			}
		} catch (Exception e) {
			log.error(RandomUtil.convertStackTraceToString(e));
			NewRelic.noticeError(e, false);
		}
	}
	
	/*
	 * This creates a null pointer that is caught and not logged
	 */
	private static void testSwallowedNulls (String value) {	
		try {
			if (value.equalsIgnoreCase("hello")) {
				//matching logic would go here
			}	
		} catch (Exception e) {
			// TODO: handle exception
			//EAT it
		}
	}
	
	/*
	 * This creates a null pointer that is caught and not logged
	 */
	private static void testUncaughtNulls (String value) {	
		if (value.equalsIgnoreCase("hello")) {
			//matching logic would go here
		}	
	}
	
	private static void sleep () {
		try {
			Thread.sleep(200);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static String getRandomString (List<String> dataList) {
		String returnString = null;
		try {
			returnString = (String) RandomUtil.getRandomObjectFromList(dataList);
		} catch (Exception e) {
			log.error("Unable to get random value from dataList, " + e);
		}
		
		return returnString;
	}
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("hello");
		dataList.add(null);
		dataList.add("#NoNullHere");
		dataList.add("It is a value");
		dataList.add("Word");
		dataList.add("Not Null");
		dataList.add("Null");
		dataList.add("Chaching");
		dataList.add("Yup");
		dataList.add("This is it");
		
		return dataList;
	}

}
