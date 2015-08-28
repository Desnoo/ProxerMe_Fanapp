package www.luneyco.com.proxertestapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NewsResponseParser;

/**
 * Shows the news in detail in a webview.
 * Created by TS on 26.08.2015.
 */
public class NewsDetailFragment extends Fragment {

    private final class Bundle {
        public final static String NEWS_URL = "news_url";
    }

    private Context mContext;

    private NewsResponseParser m_NewsResponseParser;
    private WebView            m_WebView;
    private String             m_UrlToLoad;

    public static Fragment newInstance(String _NewsUrl) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(Bundle.NEWS_URL, _NewsUrl);
        Fragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity _Activity) {
        super.onAttach(_Activity);
        mContext = _Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             android.os.Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        m_WebView = (WebView) view.findViewById(R.id.webView);
        m_WebView.loadUrl(m_UrlToLoad);
        return view;
    }

    @Override
    public void onCreate(android.os.Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        m_UrlToLoad = getArguments().getString(Bundle.NEWS_URL);
        /*if(m_UrlToLoad == null){
            Toast.makeText(getActivity(), "Keine Url zum Laden gefunden :(", Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
