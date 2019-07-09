package com.overops.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newrelic.api.agent.NewRelic;

public class IndexOutOfBoundsErrorUtil {
	
	private final static Logger log = LoggerFactory.getLogger(IndexOutOfBoundsErrorUtil.class);

	public static void testErrors (int numOfIterations) {	
		List<String> dataList = setupData();
		log.debug("Random IndexOutOfBounds Error generation for {} iterations ", numOfIterations);
		for (int i = 0; i < numOfIterations; i++) {
			try {
				Thread.sleep(100);
				int value = RandomUtil.generateRandom(10);
				String string = dataList.get(value);
			
			} catch (Exception e) {
				//log the exception and keep chugging
				log.error(RandomUtil.convertStackTraceToString(e));
				NewRelic.noticeError(e, false);
			}
		}		
	}
	
	private static List<String> setupData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("1");
		dataList.add("2");
		dataList.add("3");
		dataList.add("4");
		dataList.add("5");
		dataList.add("6");
		dataList.add("7");
		dataList.add("8");
		dataList.add("9");
		dataList.add("10");
		
		return dataList;
	}
}
