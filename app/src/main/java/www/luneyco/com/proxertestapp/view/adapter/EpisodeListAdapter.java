package www.luneyco.com.proxertestapp.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.model.anime.Episode;
import www.luneyco.com.proxertestapp.view.adapter.clickListener.IOnTouchListener;

/**
 * The adapter for handling the episode view.
 * Created by tinos_000 on 09.10.2015.
 */
public class EpisodeListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private IOnTouchListener<Episode> mTouchListener;
    private List<Episode> mEpisodeList;

    /**
     * Constructor in which the episodelist must be given to initialize the view.
     * @param _Context the context of activity/fragment.
     * @param _EpisodeList the {@link List} of {@link Episode}s.
     */
    public EpisodeListAdapter(Context _Context, IOnTouchListener _TouchListener, List<Episode> _EpisodeList) {
        super();
        mContext = _Context;
        mEpisodeList = _EpisodeList;
        mTouchListener = _TouchListener;
    }


    /**
     * Recyclerviewholder for the news.
     */
    private class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mEpisodeName;

        public EpisodeViewHolder(View _ItemView) {
            super(_ItemView);
            mEpisodeName = (TextView) _ItemView.findViewById(R.id.entry_number);
            _ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Episode clicked", Toast.LENGTH_LONG).show();
            mTouchListener.onItemClicked(mEpisodeList.get(getAdapterPosition()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new EpisodeViewHolder(inflater.inflate(R.layout.list_item_anime_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Episode episodeText = mEpisodeList.get(position);
        EpisodeViewHolder episodeViewHolder = (EpisodeViewHolder) holder;
        episodeViewHolder.mEpisodeName.setText(String.valueOf(episodeText.getmEpisodeNumber()));
    }



    @Override
    public int getItemCount() {
        return mEpisodeList != null ? mEpisodeList.size() : 0;
    }
}
