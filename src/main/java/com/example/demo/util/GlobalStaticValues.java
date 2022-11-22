package com.example.demo.util;

import java.util.List;

import com.example.demo.entity.Cart;

import ch.qos.logback.core.status.Status;

public class GlobalStaticValues {
	public static int[] customerFullCartIdList = {};
	
	public static int[] customerSelectedCartIdList = {};
	
	public static boolean[] customerCheckedCartList(int[] fullCartIdArr) {
		boolean[] tmpArr = new boolean[fullCartIdArr.length];
		
		for(int i = 0; i < tmpArr.length; i++) {
			tmpArr[i] = false;
		}
		
		return tmpArr;
	}
}
