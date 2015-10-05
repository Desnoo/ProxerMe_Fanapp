package www.luneyco.com.proxertestapp.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import www.luneyco.com.proxertestapp.MainApplication;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.view.fragment.preferences.MainPreferencesFragment;
import www.luneyco.com.proxertestapp.utils.FragmentManager;

/**
 * Activity that holds all fragments for settings/preferences.
 * Created by TS on 30.08.2015.
 */
public class PreferencesActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        setContentView(R.layout.activity_base);
        FragmentManager.replaceFragment(this, new MainPreferencesFragment(), false);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key){
            case Preferences.PREF_ENABLE_NEWS_NOTIFICATION:
                boolean enableNewsNotification = sharedPreferences.getBoolean(key, false);
                if(enableNewsNotification){
                    MainApplication.getInstance().startNewsService();
                } else {
                    MainApplication.getInstance().stopNewsService();
                }
                break;
            case Preferences.UPDATE_RATE:
                sharedPreferences.getInt(key, 30);
                MainApplication.getInstance().startNewsService();
                break;
        }
    }
}
