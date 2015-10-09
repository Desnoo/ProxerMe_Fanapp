package www.luneyco.com.proxertestapp.view.fragment.anime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.model.Language;
import www.luneyco.com.proxertestapp.model.anime.Anime;
import www.luneyco.com.proxertestapp.model.anime.Episode;
import www.luneyco.com.proxertestapp.request.general.StringRequest;
import www.luneyco.com.proxertestapp.utils.RealmGsonHelper;
import www.luneyco.com.proxertestapp.view.adapter.EpisodeListAdapter;
import www.luneyco.com.proxertestapp.view.adapter.IOnTouchListener;

/**
 * Created by tinos_000 on 07.10.2015.
 */
public class AnimeEpisodesFragment extends Fragment implements Response.ErrorListener, Response.Listener<String>,IOnTouchListener<Episode> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<Episode> mEpisodes;
    private int mAnimeId;

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


    /**
     * Used to attach the context to this activity. Do all the related things in this instead of in onAttach.
     *
     * @param _Context the context where fragment was attached.
     */
    private void onAttachToContext(Context _Context) {
        mContext = _Context;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnimeId = 11246;
        setHasOptionsMenu(true);
        mEpisodes = null;
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, "http://proxer.me/info/" + mAnimeId + "/list?format=json", this, this);
        queue.add(stringRequest);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_anime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getGroupId() == R.id.filter_lang_group){
            switch (item.getItemId()){
                case R.id.filter_ger:
                    setFilteredEpisodes(Language.GER_SUB);
                    break;
                case R.id.filter_eng:
                    setFilteredEpisodes(Language.ENG_SUB);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFilteredEpisodes(String gerSub) {
        if(mEpisodes == null){
            return;
        }
        List<Episode> episodes = new ArrayList<>();
        for (Episode episode : mEpisodes) {
            if (episode.getmLanguage().getmLanguageTag().matches(gerSub)) {
                episodes.add(episode);
            }
        }
        mRecyclerView.setAdapter(new EpisodeListAdapter(mContext, this, episodes));
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Gson gson = RealmGsonHelper.getGsonParser();

        Anime anime = gson.fromJson(response, Anime.class);
        mEpisodes = anime.getmEpisodes();
        String out = "";
        /*for(Episode episode : Episodes){
            out += episode.getmEpisodeNumber() + " " + episode.getmLanguage().getmLanguageTag();
        }*/
        mRecyclerView.setAdapter(new EpisodeListAdapter(mContext, this, mEpisodes));
        Toast.makeText(mContext, "Response", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(Episode _Episode) {

    }
}
