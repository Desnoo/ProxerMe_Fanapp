package www.luneyco.com.proxertestapp.view.fragment;

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
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.view.adapter.NewsListAdapter;
import www.luneyco.com.proxertestapp.events.LoadNewsFromWebEvent;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.INotificationResponseParserListener;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NewsResponseParser;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NotificationResponseParser;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.utils.provider.BusProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements IListResponse<News>, INotificationResponseParserListener {

    private Context mContext;
    private ListView mListView;
    private NewsListAdapter mListAdapter;

    private NewsResponseParser m_NewsResponseParser;

    public NewsActivityFragment() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new NewsActivityFragment();
        return fragment;
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
        mListAdapter = new NewsListAdapter(mContext, R.layout.list_adapter_news_preview, new ArrayList<News>());
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        Realm realm = Realm.getInstance(mContext);
        RealmResults<News> news = realm.where(News.class).findAllSorted("mId", false);
        for (News newsToInsert : news) {
            mListAdapter.add(newsToInsert);
        }
        if (news.size() == 0) {
            // no entries then load first page
            int page = 1;
            String url = "http://proxer.me/notifications?format=json&s=news&p=" + String.valueOf(page);
            m_NewsResponseParser.DoRequest(1);
        } else {
            new NotificationResponseParser(this, mContext);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResponse(List<News> _Ret) {
        Realm realm = Realm.getInstance(mContext);
        int latestNewsId = 0;
        int oldestNewsId =Integer.MAX_VALUE;
        if (realm.where(News.class).count() > 0) {
            latestNewsId = (int) realm.where(News.class).maximumInt("mId");
            oldestNewsId = (int) realm.where(News.class).minimumInt("mId");
        }
        for (News news : _Ret) {
            if (news.getmId() > latestNewsId || news.getmId() < oldestNewsId) {
                mListAdapter.add(news);
            }
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(_Ret);
        realm.commitTransaction();

    }

    @Override
    public void onErrorResponse() {
        Toast.makeText(mContext, "News konnten nicht aktualisiert werden", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Notification _Notification) {
        if (!_Notification.isSuccessful()) {
            Toast.makeText(mContext, "Keine Notifications erhalten. Bitte einloggen!", Toast.LENGTH_LONG).show();
            return;
        }
        if (_Notification.getUnreadNews() > 0) {
            int pagesToUpdate = (int) Math.ceil((double) _Notification.getUnreadNews() / 15);
            for(int page = 1; page <= pagesToUpdate; ++ page) {
                m_NewsResponseParser.DoRequest(page);
            }
        }
    }

    @Subscribe
    public void loadNewsFromWeb(LoadNewsFromWebEvent _Event){
        Realm realm = Realm.getInstance(mContext);
        if (realm.where(News.class).count() > 0) {
            int latestNewsId = (int) realm.where(News.class).maximumInt("mId");
            int page = (int)Math.ceil((double)((double)(latestNewsId - _Event.getNewsIdToLoad()) / (double)15));
            m_NewsResponseParser.DoRequest(page);

        }
    }

}
