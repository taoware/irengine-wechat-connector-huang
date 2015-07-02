package com.irengine.wechat.util;

import java.util.Date;

import org.junit.Test;

public class TestDemo {

	@Test
	public void testDate(){
		Date now=new Date();
		System.out.println(now.equals(now));
	}
}
