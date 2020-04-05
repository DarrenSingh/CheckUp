package com.Group6.checkup.Utils;

import android.text.TextUtils;
import android.widget.EditText;

public class AccountValidation {

    static public boolean isEmpty(EditText et) {

        if (TextUtils.isEmpty(et.getText()) || et.getText().toString().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean nameValidation(EditText et){

        if(et.getText().toString().trim().matches("[a-zA-Z ]*")){
            return true;
        }else{
            return false;
        }
    }

    static public boolean emailValidation(EditText et){
        if(et.getText().toString().trim().matches("([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})")){
            return true;
        }else{
            return false;
        }
    }
}
