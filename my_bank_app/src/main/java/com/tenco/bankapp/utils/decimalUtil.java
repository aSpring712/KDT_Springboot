package com.tenco.bankapp.utils;

import java.text.DecimalFormat;

public class decimalUtil {
	
	public static String decimalToString(Long amount) {
		DecimalFormat df = new DecimalFormat("#,###");
		String formatNumber = df.format(amount);
		return formatNumber + "원";
	}
}
