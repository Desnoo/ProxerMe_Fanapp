package www.luneyco.com.proxertestapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.adapter.NewsListAdapter;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NewsResponseParser;
import www.luneyco.com.proxertestapp.model.News;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements IListResponse<News> {

    private Context mContext;
    private ListView mListView;
    private NewsListAdapter mListAdapter;

    private NewsResponseParser m_NewsResponseParser;

    public NewsActivityFragment() {
    }

    @Override
    public void onAttach(Activity _Activity) {
        super.onAttach(_Activity);
        mContext = _Activity;
        m_NewsResponseParser = new NewsResponseParser(this, mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mListView = (ListView) view.findViewById(R.id.newsListHolder);
        mListView.setAdapter(mListAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new NewsListAdapter(getActivity(),mContext, R.layout.list_adapter_news_preview, new ArrayList<News>());
    }

    @Override
    public void onResume() {
        super.onResume();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        int page = 1;
        String url = "http://proxer.me/notifications?format=json&s=news&p=" + String.valueOf(page);
        m_NewsResponseParser.DoRequest(1);
    }

    @Override
    public void onResponse(List<News> _Ret) {
        for(News news : _Ret){
            mListAdapter.add(news);
        }
    }

    @Override
    public void onErrorResponse() {
        Toast.makeText(mContext, "News konnten nicht aktualisiert werden", Toast.LENGTH_LONG).show();
    }
}
