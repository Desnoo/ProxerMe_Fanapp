package www.luneyco.com.proxertestapp.config;

/**
 * A class that holds all preference names.
 * Created by TS on 30.08.2015.
 */
public class Preferences {

    public static final int NOTIFICATION_NEWS_ID = 0;

    public static final String UPDATE_RATE = "prefKeyNotificationCheckRate";
    public static final int MIN_UPDATE_RATE = 1;
    public static final int MAX_UPDATE_RATE = 1440;


    public static final String LoginPreferences = "prefLoginPrefs";
    public class Login {
        public static final String LOGIN_NAME = "prefLoginName";
        public static final String LOGIN_PASSWORD = "prefLoginPassword";
    }

}
