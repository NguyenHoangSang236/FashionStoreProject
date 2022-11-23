package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;

import ch.qos.logback.core.status.Status;

public class GlobalStaticValues {
	public static int[] customerFullCartIdList = {};
	
	public static int[] customerSelectedCartIdList = {};
	
	public static int[] customerSelectedCartQuantityList = {};
	
	public static int[] customerFullSelectStatusList = {};
	
	public static Product customerSelectedProduct = new Product();
	
	public static List<Cart> customerFullCartList = new ArrayList<Cart>();
	
	public static boolean[] customerCheckedCartList(int[] selectStatusArr) {
		boolean[] tmpArr = new boolean[selectStatusArr.length];
		
		for(int i = 0; i < tmpArr.length; i++) {
			if(selectStatusArr[i] == 0) {
				tmpArr[i] = false;
			} else {
				tmpArr[i] = true;
			}
		}
		
		return tmpArr;
	}
}
