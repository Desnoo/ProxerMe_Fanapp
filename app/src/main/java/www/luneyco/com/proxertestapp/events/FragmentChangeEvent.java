package www.luneyco.com.proxertestapp.events;


import android.app.Fragment;

/**
 * Event that indicates changing current fragment.
 * Created by tinos_000 on 28.08.2015.
 */
public class FragmentChangeEvent {

    private Fragment m_Fragment;

    public FragmentChangeEvent(Fragment _NewFragment){
        m_Fragment = _NewFragment;
    }

    public Fragment getFragment() {
        return m_Fragment;
    }
}
