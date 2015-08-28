package www.luneyco.com.proxertestapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.events.FragmentChangeEvent;
import www.luneyco.com.proxertestapp.fragment.NewsDetailFragment;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.utils.provider.BusProvider;
import www.luneyco.com.proxertestapp.utils.NetworkUtils;

/**
 * Adapter for lists.
 * Created by tinos_000 on 24.07.2015.
 */
public class NewsListAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsListAdapter.class.getName();
    private Context  m_Context;
    private int      m_LayoutId;

    public NewsListAdapter(Context context, int resource, ArrayList<News> objects) {
        super(context, resource, objects);
        m_LayoutId = resource;
        m_Context = context;
    }

    @Override
    public View getView(int _Position, View _ConvertView, ViewGroup _Parent) {

        if (_ConvertView == null) {
            LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _ConvertView = inflater.inflate(m_LayoutId, _Parent, false);
        }

        TextView newsTitle = (TextView) _ConvertView.findViewById(R.id.textTitle);
        //TextView textDescription = (TextView) _ConvertView.findViewById(R.id.textDescription);
        ImageView image = (ImageView) _ConvertView.findViewById(R.id.titleImage);

        News currentNews = getItem(_Position);
        String imageUrl    = NetworkUtils.getNewsImageUrl(currentNews.getmId(), currentNews.getmImageId());

        newsTitle.setText(currentNews.getmTitle());
       // Log.i(LOG_TAG, currentNews.toString());
        //textDescription.setText(currentNews.getmDescription());
        Picasso.with(m_Context)
                .load(imageUrl)
                .fit()
                .tag(_ConvertView)
                .error(R.mipmap.no_image)
                .into(image);

        _ConvertView.setOnClickListener(new OnClickListener(NetworkUtils
                        .getNewsUrl(currentNews.getmCategory().getmId(), currentNews.getmThreadId()))
        );

        return _ConvertView;
    }

    @Override
    public void add(News object) {
        super.add(object);
    }


    /**
     * On click listener when news entry was pressed.
     */
    private class OnClickListener implements View.OnClickListener {

        private String m_Url;

        public OnClickListener(String _Url) {
            m_Url = _Url;
        }


        @Override
        public void onClick(View v) {
           BusProvider.getInstance().post(new FragmentChangeEvent(NewsDetailFragment.newInstance(m_Url)));
        }
    }

}
