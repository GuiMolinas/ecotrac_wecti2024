package com.wecti.unicid.myapplication.first_contact;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.wecti.unicid.myapplication.R;

import java.util.Calendar;

public class EcoNotificationService extends BroadcastReceiver {

    private static final String CHANNEL_ID = "EcoTrackChannel";
    private static final String CHANNEL_NAME = "Lembretes EcoTrack";
    private static final int NOTIFICATION_ID = 1;
    private static final String[] MESSAGES = {
            "Economize água: feche a torneira ao escovar os dentes.",
            "Desligue as luzes ao sair de um cômodo.",
            "Reduza o uso de plásticos: use sacolas reutilizáveis.",
            "Consuma de forma consciente: evite o desperdício de comida.",
            "Recicle sempre que possível!",
            "Evite o uso excessivo de energia elétrica.",
            "Proteja o planeta: plante uma árvore ou cuide das plantas!"
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        String message = MESSAGES[dayOfWeek % MESSAGES.length];
        sendNotification(context, message);
    }

    private void sendNotification(Context context, String message) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Criar canal de notificação (apenas para Android O e superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificações diárias do EcoTrack");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo) // Confirme que o ícone está no diretório drawable
                .setContentTitle("Dica EcoTrack")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Fecha a notificação ao ser clicada

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void scheduleDailyNotifications(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, EcoNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);  // Define para 9h da manhã
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Ajusta a hora caso já tenha passado, para o próximo dia
        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Configura alarme diário
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }
}
