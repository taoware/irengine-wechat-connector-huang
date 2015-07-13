package com.irengine.wechat.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TestDemo {

	@Test
	public void testDate(){
		Date now=new Date();
		System.out.println(now.equals(now));
	}
	
	@Test
	public void testList(){
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list=list.subList(0, 5);
		System.out.println(list);
	}
}
