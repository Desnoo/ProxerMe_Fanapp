package www.luneyco.com.proxertestapp;

import android.app.Application;
import android.content.Intent;
import android.preference.PreferenceManager;

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
        m_AlertHelper.startNewsAlarmManager(this, PreferenceManager.getDefaultSharedPreferences(this).getInt(Preferences.UPDATE_RATE, Preferences.MIN_UPDATE_RATE));

        Intent service = new Intent(this, NotificationService.class);
        startService(service);
    }
}
