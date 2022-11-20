package com.example.demo.util;

import com.example.demo.entity.Account;

public class Network {
	public static Account temporaryAccount;
	
	public static String randomTemporaryPassword(String userName) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		 
		StringBuilder sb = new StringBuilder(10);
		 
		for (int i = 0; i < 10; i++) {
			int index = (int)(AlphaNumericString.length() * Math.random());
		 
			sb.append(AlphaNumericString.charAt(index));
		}
		
		temporaryAccount = new Account(userName, sb.toString(), "user");
		
		return sb.toString();
	}
}
