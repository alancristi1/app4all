package resende.alan.app4all.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

public class Database {

    private static DatabaseHandler handler = null;

    public Database(Context context) {
        if (handler == null) {
            handler = new DatabaseHandler(context);
        }
    }

    public void gravarMarker(LatLng latLng){
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("latitude", latLng.latitude);
        value.put("longitude", latLng.longitude);
        db.insert("marker", null, value);
        db.close();
    }

    public LatLng buscarDadosMarker(){
        SQLiteDatabase db = handler.getReadableDatabase();
        String sql = "SELECT latitude, longitude FROM marker";
        Cursor cursor;

        cursor = db.rawQuery(sql, null);
        if(cursor != null && cursor.moveToLast()){
            do {
                double latitude = cursor.getDouble(0);
                double longitude = cursor.getDouble(1);

                LatLng latLng = new LatLng(latitude, longitude);
                return  latLng;
            }while (cursor.moveToNext());
        }
        return null;
    }
}