package com.wecti.unicid.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EcoTrack.db";
    private static final int DATABASE_VERSION = 3; // Aumente a versão aqui se necessário

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

    // Tabela de histórico de consumo de eletricidade
    public static final String TABLE_ELECTRICITY_HISTORY = "electricity_history";
    public static final String COLUMN_ELECTRICITY_ID = "id";
    public static final String COLUMN_TOTAL_ELECTRICITY_CONSUMPTION = "total_consumption";
    public static final String COLUMN_DATE_ELECTRICITY = "date";

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

    private static final String TABLE_CREATE_ELECTRICITY =
            "CREATE TABLE " + TABLE_ELECTRICITY_HISTORY + " (" +
                    COLUMN_ELECTRICITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TOTAL_ELECTRICITY_CONSUMPTION + " REAL, " +
                    COLUMN_DATE_ELECTRICITY + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating tables");
        db.execSQL(TABLE_CREATE_WATER);
        db.execSQL(TABLE_CREATE_CARBON);
        db.execSQL(TABLE_CREATE_ELECTRICITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Remove as tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARBON_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELECTRICITY_HISTORY);
        // Cria as novas tabelas
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

    // Método para inserir consumo de eletricidade
    public void insertElectricityConsumption(double totalConsumption, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTAL_ELECTRICITY_CONSUMPTION, totalConsumption);
        values.put(COLUMN_DATE_ELECTRICITY, date);
        db.insert(TABLE_ELECTRICITY_HISTORY, null, values);
        manageHistorySize(db, TABLE_ELECTRICITY_HISTORY);
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

    public void clearAllHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WATER_HISTORY);
        db.execSQL("DELETE FROM " + TABLE_CARBON_HISTORY);
        db.execSQL("DELETE FROM " + TABLE_ELECTRICITY_HISTORY);
    }
}
