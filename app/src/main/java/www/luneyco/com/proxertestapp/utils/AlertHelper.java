package www.luneyco.com.proxertestapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import www.luneyco.com.proxertestapp.view.service.NotificationService;

/**
 * Helper to create
 * Created by TS on 30.08.2015.
 */
public class AlertHelper {

    private AlarmManager m_AlarmManager;
    private PendingIntent m_PendingIntent;

    private static int m_NewsAlarmRequestCode = 1;

    /**
     * Starts a new alarm that is triggered every _UpdateRate minutes.
     * @param _Context the context of the app.
     * @param _UpdateRate the update rate in minutes.
     */
    public void startNewsAlarmManager(Context _Context, int _UpdateRate){
        stopNewsAlarmManager(_Context);
        long updateRate = _UpdateRate * 60L * 1000L;
        m_AlarmManager = (AlarmManager) _Context.getSystemService(Context.ALARM_SERVICE);
        Intent intent  = new Intent(_Context, NotificationService.class);
        m_PendingIntent = PendingIntent.getService(_Context, m_NewsAlarmRequestCode, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        m_AlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), updateRate, m_PendingIntent);
    }

    public void stopNewsAlarmManager(Context _Context){
        if(m_AlarmManager == null || m_PendingIntent == null){
            Intent intent  = new Intent(_Context, NotificationService.class);
            m_PendingIntent = PendingIntent.getService(_Context, m_NewsAlarmRequestCode, intent, 0);
            m_AlarmManager = (AlarmManager) _Context.getSystemService(Context.ALARM_SERVICE);
        }
        m_AlarmManager.cancel(m_PendingIntent);

    }
}
