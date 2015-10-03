package www.luneyco.com.proxertestapp.events;

/**
 * Event that indicates that news should be loaded.
 * Created by TS on 29.08.2015.
 */
public class LoadNewsFromWebEvent {

    /**
     * The page to load.
     */
    private int m_NewsIdToLoad;

    public LoadNewsFromWebEvent(int _PageToLoad) {
        m_NewsIdToLoad = _PageToLoad;
    }

    public int getNewsIdToLoad() {
        return m_NewsIdToLoad;
    }
}
