package com.example.demo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        return (oneStar + twoStar + threeStar + fourStar + fiveStar) / 5;
    }
}
