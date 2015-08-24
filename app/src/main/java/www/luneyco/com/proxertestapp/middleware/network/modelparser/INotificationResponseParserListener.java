package www.luneyco.com.proxertestapp.middleware.network.modelparser;

import www.luneyco.com.proxertestapp.model.Notification;

/**
 * Interface that is used to surpass an parsed Notification response to the caller.
 * Created by TS on 24.08.2015.
 */
public interface INotificationResponseParserListener {

    /**
     * Called when the response and parsing was done.
     * @param _Notification  the new notification. Has a successful flag set if it was successful, else no flag was set.
     */
    void onResponse(Notification _Notification);
}
