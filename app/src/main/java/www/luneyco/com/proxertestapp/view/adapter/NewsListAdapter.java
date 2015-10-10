package www.luneyco.com.proxertestapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.events.FragmentChangeEvent;
import www.luneyco.com.proxertestapp.events.LoadNewsFromWebEvent;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.utils.NetworkUtils;
import www.luneyco.com.proxertestapp.utils.provider.BusProvider;
import www.luneyco.com.proxertestapp.view.adapter.base.RealmRecyclerViewAdapter;
import www.luneyco.com.proxertestapp.view.adapter.clickListener.INewsClickListener;
import www.luneyco.com.proxertestapp.view.fragment.NewsDetailFragment;

/**
 * Adapter for lists.
 * Created by tinos_000 on 24.07.2015.
 */
public class NewsListAdapter extends RealmRecyclerViewAdapter<News> implements INewsClickListener {

    /**
     * Recyclerviewholder for the news.
     */
    private class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView newsTitle;
        //TextView      textDescription;
        public ImageView image;
        private int mPosition;
        private INewsClickListener mListener;

        public NewsViewHolder(View _ItemView, int _Position, INewsClickListener _Listener) {
            super(_ItemView);
            newsTitle = (TextView) _ItemView.findViewById(R.id.textTitle);
            //textDescription  = (TextView) _ConvertView.findViewById(R.id.textDescription);
            image = (ImageView) _ItemView.findViewById(R.id.titleImage);
            mPosition = _Position;
            mListener = _Listener;
            _ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(mPosition);
        }

        public void setPosition(int _Position) {
            mPosition = _Position;
        }
    }


    private static final String LOG_TAG = NewsListAdapter.class.getName();
    private Context m_Context;
    private int m_LayoutId;

    public NewsListAdapter(Context _Context, int _Layout){
        m_Context = _Context;
        m_LayoutId = _Layout;
    }


    @Override
    public void onClick(int _Position) {
        News news = getItem(_Position);
        BusProvider.getInstance().post(new FragmentChangeEvent(NewsDetailFragment.newInstance(NetworkUtils
                .getNewsUrl(news.getmCategory().getmId(), news.getmThreadId()))));
    }

    @Override
    public int getItemCount() {
        return getRealmAdapter() != null ? getRealmAdapter().getCount() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup _ViewGroup, int _I) {
        LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new NewsViewHolder(inflater.inflate(m_LayoutId, _ViewGroup, false), _I, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _ViewHolder, int _Position) {
        News news = getItem(_Position);
        NewsViewHolder newsViewHolder = (NewsViewHolder) _ViewHolder;
        Picasso.with(m_Context)
                .load(NetworkUtils.getNewsImageUrl(news.getmId(), news.getmImageId()))
                .fit()
                .centerCrop()
                .tag(_ViewHolder)
                .error(R.mipmap.no_image)
                .into(newsViewHolder.image);
        newsViewHolder.newsTitle.setText(news.getmTitle());
        newsViewHolder.setPosition(_Position);

        // check if we need to reload some more data
        if (_Position >= getItemCount() - 1) {
            // event to load some more data
            BusProvider.getInstance().post(new LoadNewsFromWebEvent(news.getmId() - 1));
        }
    }
}
