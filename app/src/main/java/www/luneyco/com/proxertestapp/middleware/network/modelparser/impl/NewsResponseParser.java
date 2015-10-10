package www.luneyco.com.proxertestapp.middleware.network.modelparser.impl;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import www.luneyco.com.proxertestapp.config.NetworkRequestUrls;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.network.general.request.StringRequest;
import www.luneyco.com.proxertestapp.utils.RealmGsonHelper;
import www.luneyco.com.proxertestapp.utils.provider.VolleyProvider;

/**
 * Creates request for news and parses response to news.
 * Created by TS on 25.08.2015.
 */
public class NewsResponseParser implements Response.ErrorListener, Response.Listener<String> {

    private IListResponse<News> mListener;
    private Context mContext;

    /**
     * Constructor for the response parser.
     * @param _ResponseListener the listener for the response.
     */
    public NewsResponseParser(IListResponse<News> _ResponseListener, Context _Context) {
        mListener = _ResponseListener;
        mContext = _Context;
    }

    /***
     * Loads the contents of the given page.
     * @param _PageNum the number of page to load.
     */
    public void DoRequest(int _PageNum) {
        String requestUrl = NetworkRequestUrls.NewsRequest.NewsRequestUrl.replace(NetworkRequestUrls.NewsRequest.NewsRequestPageNum, String.valueOf(_PageNum));
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, requestUrl, this, this);
        VolleyProvider.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mListener.onErrorResponse();
    }

    @Override
    public void onResponse(String response) {
        Gson gson = RealmGsonHelper.getGsonParser();

        JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
        int error = jsonObject.get(NetworkRequestUrls.NewsRequest.Error).getAsInt();
        if(error != 0){
            mListener.onErrorResponse();
            return;
        }
        JsonArray jsonArray = jsonObject.get(NetworkRequestUrls.NewsRequest.Notifications).getAsJsonArray();

        List<News> news = new ArrayList<News>();
        for(JsonElement jsonElement : jsonArray) {
            news.add(gson.fromJson(jsonElement, News.class));
        }

        mListener.onResponse(news);
        // TODO: load some images?
    }
}
