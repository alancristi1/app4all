package resende.alan.app4all.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "4all.db";
    private static final int VERSAO = 1;

    public DatabaseHandler(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    private static String marker = "CREATE TABLE marker(" +
            "latitude REAL," +
            "longitude REAL" + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(marker);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE marker");
    }
}
