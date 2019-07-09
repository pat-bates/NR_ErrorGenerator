package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class TimerTestUtil {
	
	private final static Logger log = LoggerFactory.getLogger(TimerTestUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random Timer Tester for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			try {
				String value = getRandomValue(dataList);
				Integer intValue = parseInteger(value);
				
				Thread.sleep(intValue.longValue());
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error(RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
		}		
	}
	
	/*
	 * Better way of checking if a string is a number that doesn't create exceptions that eat CPU cycles
	 */
	private static Integer parseInteger(String value) {
		Object object = value;
		if (object instanceof Integer) {
			return (Integer) object;
		} else {
			log.error("{} is NOT a number", value);
		}
		return 0;
	}
	
	private static String getRandomValue (List<String> dataList) throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(dataList);
		return returnString;
	}
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("1000");
		dataList.add("100");
		dataList.add("20");
		dataList.add("50");
		dataList.add("300");
		dataList.add("40");
		dataList.add("501");
		dataList.add("11");
		dataList.add("68");
		
		return dataList;
	}
}
