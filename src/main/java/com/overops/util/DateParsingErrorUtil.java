package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class DateParsingErrorUtil {
	
	private final static Logger log = LoggerFactory.getLogger(DateParsingErrorUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random DateParsing Error generation for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			try {
				Thread.sleep(100);
				long value = RandomUtil.convertDate(getRandomDate(dataList));
				
				if (value > 0) {
					long value2 = RandomUtil.convertDate(getRandomDate(dataList));
				}
			
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error("Error converting date, " + RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
		}		
	}
	
	private static String getRandomDate (List<String> dataList) throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(dataList);
		return returnString;
	}
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("#NotADate");
		dataList.add("02/12/2017");
		dataList.add("2012-10-10");
		dataList.add("03/10/2010");
		dataList.add("04/23/2012");
		dataList.add("05/23/2012");
		dataList.add("06/23/2013");
		dataList.add("07/23/2015");
		dataList.add("08/23/2017");
		dataList.add("09/23/1999");
		
		return dataList;
	}
}
