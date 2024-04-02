package com.util;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {
//    Validating Email

    public static boolean isValidEmail(String emailId) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(emailId);
    }

//    validating mobile number
    public static boolean isValidMobile(String mobileNo) {
        if (mobileNo.length() != 10) {
            return false;
        }
        char[] mobArr = mobileNo.toCharArray();
        for (char ch : mobArr) {
            if (Character.isDigit(ch) == false) {
                return false;
            }
        }
        return true;
    }
}
