package www.luneyco.com.proxertestapp.middleware.network.modelparser;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.Date;

import www.luneyco.com.proxertestapp.config.NetworkRequestUrls;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.request.general.StringRequest;

/**
 * Parses the notification request and the resulting response.
 * Created by TS on 24.08.2015.
 */
public class NotificationResponseParser implements Response.ErrorListener, Response.Listener<String> {


    /**
     * Order and meaning of values:  http://proxer.me/wiki/Proxer_API/Notifications
     * Error: 0 Wenn erfolgreich.
     * Altes PN-System: Veraltet, wird nicht mehr verwendet.
     * Neues PN-System: Anzahl der ungelesenen PNs.
     * Freundschaftsanfragen.
     * Ungelesene News.
     * Sonstige Benachrichtigungen.
     */
    private static final short ERROR              = 0;
    private static final short NEW_PN_MSG         = 2;
    private static final short FRIEND_REQ         = 3;
    private static final short UNREAD_NEWS        = 4;
    private static final short OTHER_NOIFIFCATION = 5;

    /**
     * The listener that should receive the parsed info of a response.
     */
    private INotificationResponseParserListener m_Listener;

    /**
     * Saves the date of last request to prevent to many requests.
     */
    private Date m_LastRequest;

    private StringRequest m_Request;
    private Context       m_Context;

    /**
     * Creates an instance that makes it possible to request the notifications.
     *
     * @param _Listener the listener that should get the data when the request and response is finished. (use DoRequest to init a new request)
     * @param _Context  the context of the calling activity/fragment.
     */
    public NotificationResponseParser(INotificationResponseParserListener _Listener, Context _Context) {
        m_Listener = _Listener;
        m_Context = _Context;
    }

    /**
     * Creates an Request to the server to get the notifications.
     */
    public void DoRequest() {
        m_LastRequest = new Date();
        RequestQueue queue = Volley.newRequestQueue(m_Context);
        m_Request = new StringRequest(StringRequest.Method.GET, NetworkRequestUrls.NotificationRequestUrl, this, this);
        queue.add(m_Request);

    }

    @Override
    public void onErrorResponse(VolleyError _Error) {
        m_Listener.onResponse(Notification.FailedNotification());
    }

    @Override
    public void onResponse(String _Response) {
        if (! _Response.contains("#")) {
            // if no hashtag is in the response string, there has happened an error or api has changed.
            m_Listener.onResponse(Notification.FailedNotification());
            return;
        }

        // when we have a hashtag all is fine
        String[] vals = _Response.split("#");

        try {
            // return new notification with information
            Notification notification = new Notification();
            boolean success = Integer.valueOf(vals[ERROR]) == 0;
            if (! success) {
                m_Listener.onResponse(Notification.FailedNotification());
                return;
            }
            notification.setSuccessful(success);
            notification.setFriendRequests(Integer.valueOf(vals[FRIEND_REQ]));
            notification.setOther(Integer.valueOf(vals[OTHER_NOIFIFCATION]));
            notification.setUnreadMessages(Integer.valueOf(vals[NEW_PN_MSG]));
            notification.setUnreadNews(Integer.valueOf(vals[UNREAD_NEWS]));
            m_Listener.onResponse(notification);
        } catch (NullPointerException | NumberFormatException e) {
            m_Listener.onResponse(Notification.FailedNotification());
        }

    }
}
