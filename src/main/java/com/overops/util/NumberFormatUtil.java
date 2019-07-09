package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class NumberFormatUtil {
	
	private final static Logger log = LoggerFactory.getLogger(NumberFormatUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random Parse Integer Error generation for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			String value="";
			try {
				Thread.sleep(100);
				value = getRandomValue(dataList);
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error(RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
				if (i%2 == 0) {
					parseIntegerOld(value);
				} else {
					parseIntegerNew(value);
				}
/*
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error(RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
*/
		}		
	}
	
	/*
	 * try catch method of testing if a string is a number
	 */
	private static void parseIntegerOld(String value) {
		try {
			Integer.parseInt(value);
			log.info("{} is a number", value);
		} catch (NumberFormatException ex) {
			log.error("{} is NOT a number", value);
			NewRelic.noticeError(ex, false);
		}
	}
	
	/*
	 * Better way of checking if a string is a number that doesn't create exceptions that eat CPU cycles
	 */
	private static void parseIntegerNew(String value) {
		Object object = value;
		if (object instanceof Integer) {
			Integer integer = (Integer) object;
			log.info("{} is a number", value);
		} else {
			log.error("{} is NOT a number", value);
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
