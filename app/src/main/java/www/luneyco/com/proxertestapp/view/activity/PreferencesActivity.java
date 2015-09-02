package www.luneyco.com.proxertestapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.view.fragment.preferences.MainPreferencesFragment;
import www.luneyco.com.proxertestapp.utils.FragmentManager;

/**
 * Activity that holds all fragments for settings/preferences.
 * Created by TS on 30.08.2015.
 */
public class PreferencesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        setContentView(R.layout.activity_base);
        FragmentManager.replaceFragment(this, new MainPreferencesFragment(), false);
    }
}
