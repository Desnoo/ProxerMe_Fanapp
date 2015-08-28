package www.luneyco.com.proxertestapp.utils;

import android.app.Activity;
import android.app.Fragment;

import www.luneyco.com.proxertestapp.R;

/**
 * The fragment manager to control fragment switching and maybe the navigation order.
 * Created by TS on 26.08.2015.
 */
public class FragmentManager {

    /**
     * Changes the current fragment.
     *
     * @param _Activity the current activity.
     * @param _Fragment the fragment to change to.
     */
    public static void replaceFragment(Activity _Activity, Fragment _Fragment) {
        _Activity.getFragmentManager().beginTransaction().replace(R.id.fragment, _Fragment).addToBackStack(null).commit();
    }
}
