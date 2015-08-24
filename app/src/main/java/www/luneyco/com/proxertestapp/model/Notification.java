package www.luneyco.com.proxertestapp.model;

/**
 * Model for notifications given by the api.
 * Created by TS on 24.08.2015.
 */
public class Notification {

    private boolean m_Successful;

    private int m_FriendRequests;

    private int m_UnreadMessages;

    private int m_UnreadNews;

    private int m_Other;

    public static final Notification FailedNotification(){
        return new Notification(false,0,0,0,0);
    }

    /**
     * Default constructor.
     */
    public Notification(){

    }

    /**
     * All constructor.
     * @param m_Successful
     * @param m_FriendRequests
     * @param m_UnreadMessages
     * @param m_UnreadNews
     * @param m_Other
     */
    public Notification(boolean m_Successful, int m_FriendRequests, int m_UnreadMessages, int m_UnreadNews, int m_Other) {
        this.m_Successful = m_Successful;
        this.m_FriendRequests = m_FriendRequests;
        this.m_UnreadMessages = m_UnreadMessages;
        this.m_UnreadNews = m_UnreadNews;
        this.m_Other = m_Other;
    }

    public int getFriendRequests() {
        return m_FriendRequests;
    }

    public void setFriendRequests(int m_FriendRequests) {
        this.m_FriendRequests = m_FriendRequests;
    }

    public int getUnreadMessages() {
        return m_UnreadMessages;
    }

    public void setUnreadMessages(int m_UnreadMessages) {
        this.m_UnreadMessages = m_UnreadMessages;
    }

    public int getUnreadNews() {
        return m_UnreadNews;
    }

    public void setUnreadNews(int m_UnreadNews) {
        this.m_UnreadNews = m_UnreadNews;
    }

    public int getOther() {
        return m_Other;
    }

    public void setOther(int m_Other) {
        this.m_Other = m_Other;
    }

    public boolean isSuccessful() {
        return m_Successful;
    }

    public void setSuccessful(boolean m_Successful) {
        this.m_Successful = m_Successful;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "m_Successful=" + m_Successful +
                ", m_FriendRequests=" + m_FriendRequests +
                ", m_UnreadMessages=" + m_UnreadMessages +
                ", m_UnreadNews=" + m_UnreadNews +
                ", m_Other=" + m_Other +
                '}';
    }
}
