package www.luneyco.com.proxertestapp.view.fragment.anime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.model.anime.Anime;
import www.luneyco.com.proxertestapp.model.anime.Episode;
import www.luneyco.com.proxertestapp.request.general.StringRequest;
import www.luneyco.com.proxertestapp.utils.RealmGsonHelper;

/**
 * Created by tinos_000 on 07.10.2015.
 */
public class AnimeEpisodesFragment extends Fragment implements Response.ErrorListener, Response.Listener<String> {

    private Context mContext;
    private TextView mDebugView;
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
     * @param _Context the context where fragment was attached.
     */
    private void onAttachToContext(Context _Context) {
        mContext = _Context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        mDebugView = (TextView) view.findViewById(R.id.debugText);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, "http://proxer.me/info/11246/list?format=json", this, this);
        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Gson gson = RealmGsonHelper.getGsonParser();

        Anime anime = gson.fromJson(response, Anime.class);
        List<Episode> Episodes = anime.getmEpisodes();
        String out = "";
        for(Episode episode : Episodes){
            out += episode.getmEpisodeNumber() + " " + episode.getmLanguage().getmLanguageTag();
        }
        mDebugView.setText(out);
    }
}
