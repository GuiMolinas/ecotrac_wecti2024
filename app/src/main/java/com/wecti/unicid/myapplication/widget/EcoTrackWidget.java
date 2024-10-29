package com.wecti.unicid.myapplication.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.screens.Splash;

public class EcoTrackWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Define as views do layout atualizado
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.eco_track_widget);

        // Configura o texto e a imagem conforme solicitado
        views.setImageViewResource(R.id.logo_image, R.drawable.logo); // Atribui a imagem da logo
        views.setTextViewText(R.id.click_text, "Clique aqui para acessar o EcoTrack!");

        // Intent para abrir o aplicativo ao clicar no widget
        Intent intent = new Intent(context, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent); // Define o clique para o layout principal do widget

        // Atualiza o widget com as novas visualizações
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Atualiza todos os widgets ativos
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
