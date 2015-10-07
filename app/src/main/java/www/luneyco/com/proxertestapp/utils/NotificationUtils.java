package www.luneyco.com.proxertestapp.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.view.activity.MainActivity;

/**
 * Utils for notifications. Used to easily create a notification.
 * Created by tinos_000 on 04.10.2015.
 */
public class NotificationUtils {

    /**
     * Create a new notification.
     * @param _Context the current context.
     * @param _NotificationId the id of the notification.
     * @param _Title the title of the notification.
     * @param _Text the text of the notification.
     * @param _Clazz the class that should be loaded.
     * @param _Icon the icon to be displayed.
     * */
    public static void createNotification(Context _Context, int _NotificationId, CharSequence _Title, CharSequence _Text, Class _Clazz, int _Icon) {
        Intent targetIntent = new Intent(_Context, _Clazz);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(_Context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(targetIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_Context);
        android.app.Notification notification = builder.setSmallIcon(_Icon)
                .setContentIntent(pendingIntent)
                .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle()
                    .setBigContentTitle(_Title)
                    .bigText(_Text))
                .build();
        notification.flags = android.app.Notification.FLAG_AUTO_CANCEL | android.app.Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0x0000FFFF;
        NotificationManagerCompat.from(_Context).notify(_NotificationId, notification);
    }

}
