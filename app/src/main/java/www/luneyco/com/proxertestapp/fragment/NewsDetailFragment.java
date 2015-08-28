package www.luneyco.com.proxertestapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.charset.Charset;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.request.general.StringRequest;

/**
 * Shows the news in detail in a webview.
 * Created by TS on 26.08.2015.
 */
public class NewsDetailFragment extends Fragment implements Response.ErrorListener, Response.Listener<String> {

    private final class Bundle {
        public final static String NEWS_URL = "news_url";
    }

    private static final String LOG_TAG = NewsDetailFragment.class.getName();

    private static final String KBLOCK = "kblock";
    private static final int POSITION_OF_CONTAINER_KBLOCK = 2;

    private Context mContext;

    private Document m_Document;
    private WebView m_WebView;
    private String m_UrlToLoad;

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
        return view;
    }

    @Override
    public void onCreate(android.os.Bundle _SavedInstanceState) {
        super.onCreate(_SavedInstanceState);
        m_UrlToLoad = getArguments().getString(Bundle.NEWS_URL);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(new StringRequest(StringRequest.Method.GET, m_UrlToLoad, this, this));

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(LOG_TAG, "Cannot load url or no access to it.");
        Toast.makeText(mContext, "Seite konnte nicht geladen werden!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        m_Document = Jsoup.parse(response);
        m_Document.charset(Charset.forName("utf-8"));
        String relevantHtml = m_Document.getElementsByClass("kmsgtext").outerHtml();
        m_Document.body().html(relevantHtml);
        m_WebView.loadData(m_Document.outerHtml(), "text/html", "utf-8");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
