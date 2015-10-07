package www.luneyco.com.proxertestapp.view.fragment.preferences;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;

/**
 * The fragment that holds all main preferences.
 * Created by TS on 30.08.2015.
 */
public class MainPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference licensePreference = findPreference(Preferences.PREF_KEY_LICENSE);
        licensePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _Preference) {
                new LibsBuilder()
                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT)
                                //start the activity
                        .withAboutAppName(getString(R.string.app_name))
                        .withAboutDescription(getString(R.string.app_description))
                        .withAboutVersionShown(true)
                        .withAboutIconShown(true)
                        .start(getActivity());
                return true;
            }
        });
    }


}
