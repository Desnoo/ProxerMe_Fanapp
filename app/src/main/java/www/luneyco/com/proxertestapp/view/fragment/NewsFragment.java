package www.luneyco.com.proxertestapp.view.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.events.LoadNewsFromWebEvent;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.INotificationResponseParserListener;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NewsResponseParser;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.utils.provider.BusProvider;
import www.luneyco.com.proxertestapp.view.adapter.NewsListAdapter;
import www.luneyco.com.proxertestapp.view.adapter.RealmNewsAdapter;

/**
 * A fragment that holds the news related contents.
 */
@SuppressWarnings("deprecation")
public class NewsFragment extends Fragment implements IListResponse<News>, INotificationResponseParserListener, SwipeRefreshLayout.OnRefreshListener {

    private static final long HOUR = 360000; // 60*60*1000
    private static final long MINUTE = 60000; // 60*1000

    private Context mContext;
    private RecyclerView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsListAdapter mListAdapter;

    private NewsResponseParser m_NewsResponseParser;

    public NewsFragment() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new NewsFragment();
        return fragment;
    }


    /**
     * onAttach(Context) is not called before android m.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onAttach(Context _Context) {
        super.onAttach(_Context);
        onAttachToContext(_Context);
    }

    /**
     * Deprecated on API 23.
     * Use onAttach(Context _Context) instead.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity _Activity) {
        super.onAttach(_Activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(_Activity);
        }

    }

    private void onAttachToContext(Context _Context) {
        mContext = _Context;
        m_NewsResponseParser = new NewsResponseParser(this, mContext);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mListView = (RecyclerView) view.findViewById(R.id.newsListHolder);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView.setAdapter(mListAdapter);
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // only trigger update when scrolled to the top
                boolean atTop = !recyclerView.canScrollVertically(-1);
                int topRowVerticalPosition = (mListAdapter == null || mListView.getChildCount() == 0) ? 0 : mListView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(atTop && topRowVerticalPosition >= 0);
            }

        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new NewsListAdapter(mContext, R.layout.list_adapter_news_preview);
    }

    @Override
    public void onResume() {
        super.onResume();
        Realm realm = Realm.getInstance(mContext);
        RealmResults<News> news = realm.where(News.class).findAllSorted("mId", false);

        RealmNewsAdapter newsAdapter = new RealmNewsAdapter(mContext, news, true);
        mListAdapter.setRealmAdapter(newsAdapter);
        BusProvider.getInstance().register(this);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        triggerLoadNews();
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResponse(List<News> _Ret) {
        mSwipeRefreshLayout.setRefreshing(false);
        Realm realm = Realm.getInstance(mContext);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(_Ret);
        realm.commitTransaction();
        mListAdapter.notifyDataSetChanged();
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
            for (int page = 1; page <= pagesToUpdate; ++page) {
                m_NewsResponseParser.DoRequest(page);
            }
        }
    }

    @Subscribe
    public void loadNewsFromWeb(LoadNewsFromWebEvent _Event) {
        Realm realm = Realm.getInstance(mContext);
        if (realm.where(News.class).count() > 0) {
            int latestNewsId = (int) realm.where(News.class).maximumInt("mId");
            int page = (int) Math.ceil((double) (latestNewsId - _Event.getNewsIdToLoad()) / (double) 15);
            m_NewsResponseParser.DoRequest(page + 1);

        }
    }

    @Override
    public void onRefresh() {
        m_NewsResponseParser.DoRequest(0);
    }


    /**
     * Used on start up to initialize the listview.
     */
    private void triggerLoadNews() {
        Realm realm = Realm.getInstance(mContext);
        RealmResults<News> news = realm.where(News.class).findAll();

        if (news.size() > 0) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            long latestTimestamp = realm.where(News.class).maximumInt("mCreationTimeStamp");
            // boolean requestFlag = sharedPreferences.getBoolean(Preferences.KEY_NOTIFICATION_NEWS, true);

            int requestTime = sharedPreferences.getInt(www.luneyco.com.proxertestapp.config.Preferences.UPDATE_RATE, 30);

            // here we want to reduce the request rate
            if (latestTimestamp + MINUTE * requestTime < Calendar.getInstance().getTimeInMillis()) {
                m_NewsResponseParser.DoRequest(0);
            }
        } else {
            // fetch
            int page = 1;
            String url = "http://proxer.me/notifications?format=json&s=news&p=" + String.valueOf(page);
            m_NewsResponseParser.DoRequest(0);
        }
    }
}
