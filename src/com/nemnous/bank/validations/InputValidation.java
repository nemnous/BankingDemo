package com.nemnous.bank.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nemnous.bank.exceptions.InvalidDetailsException;

public class InputValidation {
	
	private InputValidation() {
		
	}
	
	public static boolean validateName(String name) {
		if(name.equals("")) {
			throw new InvalidDetailsException("Name cannot be empty");
		}
		if(Pattern.compile("[0-9]").matcher(name).find()) {
			throw new InvalidDetailsException("Name cannot contain numbers");
		}
		return true;
	}
	
	public static boolean validatePhone(String phone) {
		Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher matcher = pattern.matcher(phone);
		boolean checkPhone = (matcher.find() && matcher.group().equals(phone));
		if(!checkPhone) {
			throw new InvalidDetailsException("Please check phone number");
		}
		return true;
	}
	
//	public boolean validateAmount() {
//		
//	}
}
