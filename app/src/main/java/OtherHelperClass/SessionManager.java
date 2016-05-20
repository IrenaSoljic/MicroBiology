package OtherHelperClass;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xerric on 11/25/2015.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MicroBiologyPrefs";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsFirstTime";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // commit changes
        editor.commit();
    }
    public boolean checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            return false;
        }
        return true;
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
