package www.luneyco.com.proxertestapp.adapter;

import android.app.Activity;
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
import www.luneyco.com.proxertestapp.fragment.NewsDetailFragment;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.utils.FragmentManager;
import www.luneyco.com.proxertestapp.utils.NetworkUtils;

/**
 * Adapter for lists.
 * Created by tinos_000 on 24.07.2015.
 */
public class NewsListAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsListAdapter.class.getName();
    private Context  m_Context;
    private Activity m_Activity;
    private int      m_LayoutId;

    public NewsListAdapter(Activity _Activity, Context context, int resource, ArrayList<News> objects) {
        super(context, resource, objects);
        m_LayoutId = resource;
        m_Context = context;
        m_Activity = _Activity;
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
        Log.i(LOG_TAG, currentNews.toString());
        //textDescription.setText(currentNews.getmDescription());
        Picasso.with(m_Context)
                .load(imageUrl)
                .fit()
                .tag(_ConvertView)
                .error(R.mipmap.no_image)
                .into(image);

        _ConvertView.setOnClickListener(new OnClickListenerNews(NetworkUtils
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
    private class OnClickListenerNews implements View.OnClickListener {

        private String m_Url;

        public OnClickListenerNews(String _Url) {
            m_Url = _Url;
        }

        @Override
        public void onClick(View v) {

            FragmentManager.changeFragment(m_Activity, NewsDetailFragment.newInstance(m_Url));
        }
    }

}
