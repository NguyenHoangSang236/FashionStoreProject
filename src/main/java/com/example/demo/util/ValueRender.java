package com.example.demo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ValueRender {
    //format double value
    public static String formatDoubleNumber(double number) {
        DecimalFormat dfGerman = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMAN));
        
        String result = dfGerman.format(number);
                
        return result;
    }
    
    
    //get the total amount of rating stars of product
    public static int ratingStarsTotalNumber(int oneStar, int twoStar, int threeStar, int fourStar, int fiveStar) {
        int zeroRatedNum = 0;
                
        if(oneStar != 0) {
            zeroRatedNum += 1;
        }
        if(twoStar != 0) {
            zeroRatedNum += 1;
        }
        if(threeStar != 0) {
            zeroRatedNum += 1;
        }
        if(fourStar != 0) {
            zeroRatedNum += 1;
        }
        if(fiveStar != 0) {
            zeroRatedNum += 1;
        }
        if(zeroRatedNum == 0) {
            zeroRatedNum = 1;
        }
        return (oneStar + twoStar + threeStar + fourStar + fiveStar) / zeroRatedNum;
    }
    
    
    //encode the password
    public static String encodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;
        
        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] + 5);
        }
        
        result = charArr.toString();
        
        return result;
    }
    
    
    //decode the password
    public static String decodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;
        
        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] - 5);
        }
        
        result = String.valueOf(charArr);
        
        return result;
    }
    
    
    //format string to link
    public static String stringToLink(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();
        
        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == ' ') {
                linkCharrArr[i] = '_';
            }
        }
        
        result = String.valueOf(linkCharrArr);
        
        return result;
    }
    
    
    //format link to string
    public static String linkToString(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();
        
        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == '_') {
                linkCharrArr[i] = ' ';
            }
        }
        
        result = String.valueOf(linkCharrArr);
        
        return result;
    }
    
    
    //format DateTime
    public static String formatDateDMY(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }
    
    
    //remove an element in an Integer array
    public static int[] newArrayAfterRemove(int[] arr, int element) {
    	int[] resultArr = new int[arr.length];
    	int arrInd = 0;
    	
    	for(int i = 0; i < arr.length; i++) {
    		if(arr[i] != element) {
    			resultArr[arrInd] = arr[i];
    			arrInd++;
    		}
    	}
    	
    	return resultArr;
    }
}
