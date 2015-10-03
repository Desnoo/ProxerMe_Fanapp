package www.luneyco.com.proxertestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceManager;

import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.utils.AlertHelper;

/**
 * A receiver that is notified when the device is started.
 * Created by TS on 30.08.2015.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context _Context, Intent _Intent) {
        if(_Intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            // set the alarm.
            int updateRate = PreferenceManager.getDefaultSharedPreferences(_Context).getInt(Preferences.UPDATE_RATE, Preferences.MIN_UPDATE_RATE);
            //AlertHelper.startNewsAlarmManager(_Context, updateRate);
        }
    }
}
