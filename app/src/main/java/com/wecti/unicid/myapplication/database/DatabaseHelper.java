package com.wecti.unicid.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EcoTrack.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de histórico de consumo de água
    public static final String TABLE_WATER_HISTORY = "water_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOTAL_CONSUMPTION = "total_consumption";
    public static final String COLUMN_DATE = "date";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_WATER_HISTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL_CONSUMPTION + " REAL, " +
                    COLUMN_DATE + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER_HISTORY);
        onCreate(db);
    }

    public void insertWaterConsumption(double totalConsumption, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insere novo registro
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL_CONSUMPTION, totalConsumption);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_WATER_HISTORY, null, values);

        // Verifica se há mais de 10 registros e remove o mais antigo
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_WATER_HISTORY, null);
        // Verifica se há mais de 10 registros e remove o mais antigo
        if (cursor.getCount() > 10) {
            cursor.moveToFirst();
            int oldestId = cursor.getInt(0); // Pega o valor da primeira coluna diretamente
            db.delete(TABLE_WATER_HISTORY, COLUMN_ID + "=?", new String[]{String.valueOf(oldestId)});
        }

        cursor.close();
        db.close();
    }
}
