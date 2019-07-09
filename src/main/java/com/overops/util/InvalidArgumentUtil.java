package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class InvalidArgumentUtil {
	
	private final static Logger log = LoggerFactory.getLogger(InvalidArgumentUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random InvalidArgument Error generation for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			try {
				Long value = new Long(getRandomValue(dataList));
				if (i % 2 == 0) {
					checkPercentage(value);
				} else {
					percentValidator(value);
				}
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error(RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
		}		
	}
	
	private static String getRandomValue (List<String> dataList) throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(dataList);
		return returnString;
	}
	
	private static void checkPercentage (Long value) throws IllegalArgumentException {	
		if (value < 0 || value > 100) {
			throw new IllegalArgumentException("Invalid Percentage");
		}	
	}
	
	private static void percentValidator (Long value) throws IllegalArgumentException {	
		checkPercentage(value);
	}
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("3");
		dataList.add("78");
		dataList.add("34");
		dataList.add("0");
		dataList.add("99");
		dataList.add("100");
		dataList.add("-3");
		dataList.add("202");
		dataList.add("33");
		dataList.add("O");
		
		return dataList;
	}
}
