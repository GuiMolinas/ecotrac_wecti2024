package com.wecti.unicid.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.util.Log; // Importação para logging

import com.wecti.unicid.myapplication.R;

public class EcoTrackWidget extends AppWidgetProvider {

    private static final String PREFS_NAME = "com.wecti.unicid.myapplication.widget.EcoTrackWidget";
    private static final String PREF_PREFIX_KEY_TOTAL_WATER = "total_water_";
    private static final String PREF_PREFIX_KEY_RECOMMENDED_WATER = "recommended_water_";
    private static final String PREF_PREFIX_KEY_TOTAL_ELECTRICITY = "total_electricity_";
    private static final String PREF_PREFIX_KEY_RECOMMENDED_ELECTRICITY = "recommended_electricity_";
    private static final String PREF_PREFIX_KEY_TOTAL_CARBON = "total_carbon_";
    private static final String PREF_PREFIX_KEY_RECOMMENDED_CARBON = "recommended_carbon_";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Recuperar dados salvos de SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int totalWater = prefs.getInt(PREF_PREFIX_KEY_TOTAL_WATER + appWidgetId, 0);
        int recommendedWater = prefs.getInt(PREF_PREFIX_KEY_RECOMMENDED_WATER + appWidgetId, 0);
        int totalElectricity = prefs.getInt(PREF_PREFIX_KEY_TOTAL_ELECTRICITY + appWidgetId, 0);
        int recommendedElectricity = prefs.getInt(PREF_PREFIX_KEY_RECOMMENDED_ELECTRICITY + appWidgetId, 0);
        int totalCarbon = prefs.getInt(PREF_PREFIX_KEY_TOTAL_CARBON + appWidgetId, 0);
        int recommendedCarbon = prefs.getInt(PREF_PREFIX_KEY_RECOMMENDED_CARBON + appWidgetId, 0);

        // Logging para depuração
        Log.d("EcoTrackWidget", "Atualizando widget com ID: " + appWidgetId);
        Log.d("EcoTrackWidget", "Dados recuperados - Água: " + totalWater + "L, Eletricidade: " + totalElectricity + "kWh, Carbono: " + totalCarbon + "kg");

        // Atualizar a visualização do widget com os dados recuperados
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.eco_track_widget);
        views.setTextViewText(R.id.appwidget_text, "Meus Consumos");
        views.setTextViewText(R.id.water_consumo, "Água: " + totalWater + "L (Recomendado: " + recommendedWater + "L)");
        views.setTextViewText(R.id.electricity_consumo, "Eletricidade: " + totalElectricity + "kWh (Recomendado: " + recommendedElectricity + "kWh)");
        views.setTextViewText(R.id.carbon_consumo, "Carbono: " + totalCarbon + "kg (Recomendado: " + recommendedCarbon + "kg)");

        // Atualizar o widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // Método para salvar os dados de consumo no SharedPreferences e atualizar o widget
    public static void saveConsumptionData(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int totalWater, int recommendedWater,
                                           int totalElectricity, int recommendedElectricity, int totalCarbon, int recommendedCarbon) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY_TOTAL_WATER + appWidgetId, totalWater);
        prefs.putInt(PREF_PREFIX_KEY_RECOMMENDED_WATER + appWidgetId, recommendedWater);
        prefs.putInt(PREF_PREFIX_KEY_TOTAL_ELECTRICITY + appWidgetId, totalElectricity);
        prefs.putInt(PREF_PREFIX_KEY_RECOMMENDED_ELECTRICITY + appWidgetId, recommendedElectricity);
        prefs.putInt(PREF_PREFIX_KEY_TOTAL_CARBON + appWidgetId, totalCarbon);
        prefs.putInt(PREF_PREFIX_KEY_RECOMMENDED_CARBON + appWidgetId, recommendedCarbon);
        prefs.apply();

        // Logging para verificar se os dados foram salvos
        Log.d("EcoTrackWidget", "Dados salvos - Água: " + totalWater + ", Eletricidade: " + totalElectricity + ", Carbono: " + totalCarbon);

        // Atualizar o widget para refletir os dados salvos
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Atualiza todos os widgets ativos
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
