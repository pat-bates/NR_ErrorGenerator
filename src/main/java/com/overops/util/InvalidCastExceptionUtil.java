package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class InvalidCastExceptionUtil {
	
	private final static Logger log = LoggerFactory.getLogger(DateParsingErrorUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random InvalidCast Error generation for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			try {
				Thread.sleep(100);
				String value = getRandomValue(dataList);
				Long value2 = new Long(value);
			
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
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("12344");
		dataList.add("3234");
		dataList.add("356");
		dataList.add("9449");
		dataList.add("4565");
		dataList.add("33445");
		dataList.add("-54433");
		dataList.add("333");
		dataList.add("777");
		dataList.add("2012-10-10");
		dataList.add("#WhoKnows");
		
		return dataList;
	}
}
