package hu.example.castlemanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.ArrayList;

public class AttackReciver extends BroadcastReceiver {

    ArrayList<KastelySzarny> kastelySzarnyak;
    private boolean needChannelId = false;
    private static final String PRIMARY_CHANNEL = "Attack handling channel";
    private NotificationManager notificationManager;
    private String attackResult;
    private int destroyCastleWingId;

    @Override
    public void onReceive(Context context, Intent intent) {
        attackResult = resolveAttack(context, intent);
        System.out.println("Reciving is succesfful: Attack");
        intent.removeExtra("attackResult");
        intent.removeExtra("deleteCastleWing");
        intent.putExtra("attackResult",attackResult);
        intent.putExtra("deleteCastleWing", destroyCastleWingId);
        intent.setClass(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        this.notificationManager =  (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , needChannelId ? PRIMARY_CHANNEL : null)
                .setContentTitle("Kastélyodat megtámadták!")
                .setContentText(attackResult)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setBadgeIconType(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        this.notificationManager.notify(0,builder.build());
    }

    public void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            needChannelId = true;
            String name = "Attack";
            String description = "Attack notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL, name, importance);
            channel.setDescription(description);
            this.notificationManager.createNotificationChannel(channel);
        }
    }

    public String resolveAttack(Context context, Intent intent) {
        int numberOfLovag = intent.getIntExtra("numberOfLovag", -1);
        int numberOfOpponent = intent.getIntExtra("numberOfOpponent", -1);
        int numberOfkastelySzarnyak = intent.getIntExtra("numberOfkastelySzarnyak", -1);
        intent.removeExtra("numberOfLovag");
        intent.removeExtra("numberOfOpponent");
        intent.removeExtra("numberOfkastelySzarnyak");
        if (numberOfLovag < 1) return "Hiba :(";
        if (numberOfLovag >= numberOfOpponent) {
            destroyCastleWingId = -1;
            return "Győzelem! Ellenfelek: "+numberOfOpponent+" vs Lovagok: "+numberOfLovag;
        }
        destroyCastleWingId = (int)(Math.random()*numberOfkastelySzarnyak+1);
        return "Vereség! Ellenfelek: "+numberOfOpponent+" vs Lovagok: "+numberOfLovag;

    }


}
