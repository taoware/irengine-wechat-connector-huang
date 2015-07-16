package com.irengine.wechat.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TestDemo {

	@Test
	public void testString(){
		getsubstring1("sdaffffffffdddddddddddddd就算大佛寺大佛啊哈东方市东方鸡丝豆腐jo岁的金佛山多家哦送啊多家佛山多家",60);
	}
	
	private void getsubstring1(String content, int sublength) {
		int length = content.getBytes().length;
		if (sublength >= length) {
			System.out.println(content);
		} else {
			if (sublength < 0) {
				System.out.println("长度设定错误");
			} else {
				int i = 0;
				int j = 0;
				for (; i < length; i++) {
					if (content.substring(0, i + 1).getBytes().length >= sublength) {
						j = content.substring(0, i + 1).getBytes().length;
						break;
					}

				}
				String substring = j > sublength ? content.substring(0, i)
						: content.substring(0, i + 1);
				System.out.println(substring+"...");
			}
		}
	}

	@Test
	public void testDate() {
		Date now = new Date();
		System.out.println(now.equals(now));
	}

	@Test
	public void testList() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list = list.subList(0, 5);
		System.out.println(list);
	}
}
