package www.luneyco.com.proxertestapp.view.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import io.realm.Realm;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.INotificationResponseParserListener;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.LoginResponseParser;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NotificationResponseParser;
import www.luneyco.com.proxertestapp.model.Login;
import www.luneyco.com.proxertestapp.model.LoginState;
import www.luneyco.com.proxertestapp.model.LoginStateAccessor;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.view.activity.NewsActivity;

/**
 * Activity that handle notifications.
 * Created by TS on 30.08.2015.
 */
public class NotificationService extends Service implements INotificationResponseParserListener, IResponse<Login> {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(NotificationService.class.getName(), "On Create of service.");

        LoginResponseParser loginResponseParser = new LoginResponseParser(this, this);
        loginResponseParser.Login("", "");
    }

    /**
     * Create a new notification.
     * @param _NotificationId the id of the notification.
     * @param _Title the title of the notification.
     * @param _Text the text of the notification.
     * @param _Clazz the class that should be loaded.
     */
    private void createNotification(int _NotificationId, CharSequence _Title, CharSequence _Text, Class _Clazz) {
        Intent targetIntent = new Intent(this, _Clazz);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(_Clazz);
        stackBuilder.addNextIntent(targetIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        android.app.Notification notification = builder.setSmallIcon(R.mipmap.no_image)
                .setContentTitle(_Title)
                .setContentText(_Text)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = android.app.Notification.FLAG_AUTO_CANCEL | android.app.Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0x0000FFFF;
        NotificationManagerCompat.from(this).notify(_NotificationId, notification);
    }


    @Override
    public void onResponse(Notification _Notification) {
        createNotification(Preferences.NOTIFICATION_NEWS_ID, "Neue ungelesene News!", _Notification.getUnreadNews() + " neue News", NewsActivity.class);

        if (_Notification.isSuccessful()) {
            if (_Notification.getUnreadNews() > 0) {
                createNotification(Preferences.NOTIFICATION_NEWS_ID, "Neue ungelesene News!", _Notification.getUnreadNews() + " neue News", NewsActivity.class);
            }
            if (_Notification.getOther() > 0) {
                createNotification(Preferences.NOTIFICATION_NEWS_ID, "Neue Animes aus Watchliste!", _Notification.getOther() + " neue News", NewsActivity.class);
            }
        }
    }

    @Override
    public void onResponse(Login _Ret) {
        NotificationResponseParser responseParser = new NotificationResponseParser(this, this);
        new LoginStateAccessor(_Ret.getUid()).touch(this);
        responseParser.DoRequest();
    }

    @Override
    public void onErrorResponse() {

    }
}
