package com.Group6.checkup.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;

    /**
     * Initializes the class attribute 'preferences' with the shared preferences file named "CheckUp", if the file does not exist it will create one.
     * @param context application context or activity context.
     */
    public Session(Context context) {
        preferences = context.getSharedPreferences("Checkup",Context.MODE_PRIVATE);
    }

    /**
     * Retrieves the current loginId string value from the shared preferences file.
     * @return <code>String</code> loginId if there is an existing loginId value in the shared preferences file, otherwise a null value will be returned <code>null</code>.
     */
    public String getCurrentUsername(){
        return  preferences.getString("loginId",null);
    }

    /**
     * Sets the specified loginId string value into the shared preferences file.
     * @param loginId the login id name of the user.
     * @return <code>true</code> if successfully able to add the loginId string to the shared preferences file.
     */
    public boolean setCurrentUsername(String loginId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loginId",loginId);

        return editor.commit();
    }

    /**
     *
     * @return
     */
    public int getUserId(){
        return  preferences.getInt("actorId",0);
    }

    /**
     * Sets the specified row id value into the shared preferences file.
     * @param actorId the database row id of the actor (Admin, Cashier, Doctor, Patient).
     * @return <code>true</code> if successfully able to add the loginId string to the shared preferences file.
     */
    public boolean setUserId(int actorId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("actorId",actorId);

        return editor.commit();
    }

}
