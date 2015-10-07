package www.luneyco.com.proxertestapp.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;

/**
 * General Utils to prevent some android errors.
 * Created by tinos_000 on 07.10.2015.
 */
public class Utils {

    /**
     * Get the current screen orientation. Because getActivity().getResources().getConfiguration().orientation won't work properly.
     * @param _Activity the activity where we want to get the screen orientation.
     * @return {@link Configuration#ORIENTATION_PORTRAIT} or {@link Configuration#ORIENTATION_LANDSCAPE}
     */
    public static int getScreenOrientation(Activity _Activity) {
        Display getOrient = _Activity.getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        Point point = new Point();
        getOrient.getSize(point);
        if (point.x < point.y) {
            orientation = Configuration.ORIENTATION_PORTRAIT;
        } else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }

        return orientation;
    }
}
