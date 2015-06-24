package com.tempcompany.smstemp;

/**
 * Created by Tim on 6/23/2015.
 */
public class Utilities  {

    //if phonenumber string is 10 digits then return true
    public boolean isValidNumber(String phoneNum) {

        phoneNum = phoneNum.replaceAll("/[^0-9]/g", "");
        if(phoneNum.length() == 10) {
            return true;
        }

        else    {
            return false;
        }

    }
}
