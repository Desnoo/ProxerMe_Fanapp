package www.luneyco.com.proxertestapp.view.fragment.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import www.luneyco.com.proxertestapp.R;

/**
 * The fragment that holds all main preferences.
 * Created by TS on 30.08.2015.
 */
public class MainPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


}
