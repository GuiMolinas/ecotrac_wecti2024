package com.wecti.unicid.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EcoTrack.db";
    private static final int DATABASE_VERSION = 2; // Aumente a versão aqui

    // Tabela de histórico de consumo de água
    public static final String TABLE_WATER_HISTORY = "water_history";
    public static final String COLUMN_WATER_ID = "id";
    public static final String COLUMN_TOTAL_WATER_CONSUMPTION = "total_consumption";
    public static final String COLUMN_DATE_WATER = "date";

    // Tabela de histórico de pegada de carbono
    public static final String TABLE_CARBON_HISTORY = "carbon_history";
    public static final String COLUMN_CARBON_ID = "id";
    public static final String COLUMN_TOTAL_CARBON_EMISSION = "total_emission";
    public static final String COLUMN_DATE_CARBON = "date";

    private static final String TABLE_CREATE_WATER =
            "CREATE TABLE " + TABLE_WATER_HISTORY + " (" +
                    COLUMN_WATER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL_WATER_CONSUMPTION + " REAL, " +
                    COLUMN_DATE_WATER + " TEXT" +
                    ");";

    private static final String TABLE_CREATE_CARBON =
            "CREATE TABLE " + TABLE_CARBON_HISTORY + " (" +
                    COLUMN_CARBON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL_CARBON_EMISSION + " REAL, " +
                    COLUMN_DATE_CARBON + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_WATER);
        db.execSQL(TABLE_CREATE_CARBON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARBON_HISTORY);
        onCreate(db);
    }

    // Método para inserir consumo de água
    public void insertWaterConsumption(double totalConsumption, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL_WATER_CONSUMPTION, totalConsumption);
        values.put(COLUMN_DATE_WATER, date);
        db.insert(TABLE_WATER_HISTORY, null, values);
        manageHistorySize(db, TABLE_WATER_HISTORY);
    }

    // Método para inserir pegada de carbono
    public void insertCarbonEmission(double totalEmission, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL_CARBON_EMISSION, totalEmission);
        values.put(COLUMN_DATE_CARBON, date);
        db.insert(TABLE_CARBON_HISTORY, null, values);
        manageHistorySize(db, TABLE_CARBON_HISTORY);
    }

    // Método para gerenciar o tamanho do histórico
    private void manageHistorySize(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT id FROM " + tableName + " ORDER BY id ASC", null);
        if (cursor.getCount() > 10) {
            cursor.moveToFirst();
            int oldestId = cursor.getInt(0);
            db.delete(tableName, "id=?", new String[]{String.valueOf(oldestId)});
        }
        cursor.close();
    }
}
