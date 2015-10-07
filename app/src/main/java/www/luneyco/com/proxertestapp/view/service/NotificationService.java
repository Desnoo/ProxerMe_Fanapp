package www.luneyco.com.proxertestapp.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.INotificationResponseParserListener;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NewsResponseParser;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NotificationResponseParser;
import www.luneyco.com.proxertestapp.model.Login;
import www.luneyco.com.proxertestapp.model.LoginStateAccessor;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.model.Test;
import www.luneyco.com.proxertestapp.utils.NotificationUtils;
import www.luneyco.com.proxertestapp.view.activity.NewsActivity;

/**
 * Activity that handle notifications.
 * Created by TS on 30.08.2015.
 */
public class NotificationService extends Service implements INotificationResponseParserListener, IResponse<Login>,IListResponse<News> {

    private static long mNewsCount = 0L;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // executes everytime the service is started
        Realm realm = Realm.getInstance(this);
        mNewsCount = realm.where(News.class).count();
        NewsResponseParser newsResponseParser = new NewsResponseParser(this, this);
        newsResponseParser.DoRequest(0);

        long count = realm.where(Test.class).count();
        Test test = new Test();
        test.setmId((int) count);
        test.setmCreationTimeStamp(Calendar.getInstance().getTimeInMillis());

        realm.beginTransaction();
        realm.copyToRealm(test);
        realm.commitTransaction();

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(NotificationService.class.getName(), "On Create of service.");

       // LoginResponseParser loginResponseParser = new LoginResponseParser(this, this);
       // loginResponseParser.Login("", "");
    }


    @Override
    public void onResponse(Notification _Notification) {
        NotificationUtils.createNotification(this, Preferences.NOTIFICATION_NEWS_ID, "Neue ungelesene News!", _Notification.getUnreadNews() + " neue News", NewsActivity.class, R.mipmap.notification);

        if (_Notification.isSuccessful()) {
            if (_Notification.getUnreadNews() > 0) {
                NotificationUtils.createNotification(this, Preferences.NOTIFICATION_NEWS_ID, "Neue ungelesene News!", _Notification.getUnreadNews() + " neue News", NewsActivity.class, R.mipmap.notification);
            }
            if (_Notification.getOther() > 0) {
                NotificationUtils.createNotification(this, Preferences.NOTIFICATION_NEWS_ID, "Neue Animes aus Watchliste!", _Notification.getOther() + " neue News", NewsActivity.class, R.mipmap.notification);
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
    public void onResponse(List<News> _Ret) {
        Realm realm = Realm.getInstance(this);

        String unreadNews = "";
        long newestNewsId = realm.where(News.class).maximumInt("mId");

        realm.beginTransaction();
        for(News news : _Ret){
            if(news.getmId() > newestNewsId){
                realm.copyToRealmOrUpdate(news);
                unreadNews += news.getmTitle() + "\n";
            }
        }
        realm.commitTransaction();

        long newNewsCount = realm.where(News.class).count();
        int overallNewNews = (int)(newNewsCount - mNewsCount);

        if(overallNewNews > 0) {
            NotificationUtils.createNotification(this, Preferences.NOTIFICATION_NEWS_ID, overallNewNews + " neue News", unreadNews, NewsActivity.class, R.mipmap.notification);
        }
    }

    @Override
    public void onErrorResponse() {
    }
}
