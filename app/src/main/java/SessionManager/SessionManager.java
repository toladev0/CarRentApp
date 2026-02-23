package SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import kh.edu.rupp.carrentapp.SetupActivity;

public class SessionManager {

    private final Context context;
    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public void logout() {
        prefs.edit().clear().apply();
        Intent intent = new Intent(context, SetupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}