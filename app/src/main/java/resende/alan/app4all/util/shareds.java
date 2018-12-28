package resende.alan.app4all.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class shareds {

    public void gravarLatitude(double latitude, Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("ShareLatitude", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putLong("ShareLatitude", Double.doubleToLongBits(latitude));
        prefsEditor.apply();
    }

    public Double buscarLatitude(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("ShareLatitude", MODE_PRIVATE);
        return Double.longBitsToDouble(mPrefs.getLong("ShareLatitude", 0));
    }

    public void gravarLongitude(double longitude, Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("ShareLongitude", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putLong("ShareLongitude", Double.doubleToLongBits(longitude));
        prefsEditor.apply();
    }

    public Double buscarLongitude(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("ShareLongitude", MODE_PRIVATE);
        return Double.longBitsToDouble(mPrefs.getLong("ShareLongitude", 0));
    }
}
