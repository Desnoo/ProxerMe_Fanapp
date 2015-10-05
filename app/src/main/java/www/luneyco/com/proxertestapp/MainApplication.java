package www.luneyco.com.proxertestapp;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.utils.AlertHelper;
import www.luneyco.com.proxertestapp.view.service.NotificationService;

/**
 * The main application class that holds some useful methods and cookies for example.
 * Created by TS on 31.08.2015.
 */
public class MainApplication extends Application {

    public static String LOG_TAG = MainApplication.class.getName();
    private AlertHelper m_AlertHelper;
    private        CookieManager   m_CookieManager;
    private static MainApplication m_MainApplication;

    public static MainApplication getInstance() {
        return m_MainApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        m_CookieManager = new CookieManager(new PersistentCookieStore(this), CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(m_CookieManager);
        m_MainApplication = this;
        m_AlertHelper = new AlertHelper();
        startNewsService();
    }

    /**
     * Starts a news service if the preference ENABLE_NEWS_NOTIFICATION is true.
     */
    public void startNewsService(){
        SharedPreferences preferenceManager =  PreferenceManager.getDefaultSharedPreferences(this);
        boolean startNewsService = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Preferences.PREF_ENABLE_NEWS_NOTIFICATION, false);
        stopNewsService();
        if(startNewsService) {
            int updateRate = (int) preferenceManager.getInt(Preferences.UPDATE_RATE, 30);
            m_AlertHelper.startNewsAlarmManager(this, PreferenceManager.getDefaultSharedPreferences(this).getInt(Preferences.UPDATE_RATE, Preferences.MIN_UPDATE_RATE));
            Log.i(LOG_TAG, "Start new news service. Starts every " + updateRate + " minutes");
        }
    }

    /**
     * Stops the news service.
     */
    public void stopNewsService(){
        m_AlertHelper.stopNewsAlarmManager(this);
        Log.i(LOG_TAG, "Stop news service.");
    }
}
