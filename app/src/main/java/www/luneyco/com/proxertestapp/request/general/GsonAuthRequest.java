package www.luneyco.com.proxertestapp.request.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import www.luneyco.com.proxertestapp.config.Preferences;

/**
 * Creates an request with authentication with cookie.
 * Created by TS on 31.08.2015.
 */
public class GsonAuthRequest<T> extends Request<T> {


    private Context m_Context;


    private final Gson gson = new Gson();
    private final Class<T>             clazz;
    private final Response.Listener<T> listener;

    private String username;
    private String password;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     */
    public GsonAuthRequest(int _Method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener, Context _Context, String _Username, String _Pwd) {
        super(_Method, url, errorListener);
        this.clazz = clazz;
        //this.headers = headers;
        this.listener = listener;
        this.m_Context = _Context;
        this.password = _Pwd;
        this.username = _Username;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse _Response) {
        try {
            String json = new String(
                    _Response.data,
                    HttpHeaderParser.parseCharset(_Response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(_Response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences   prefs  = m_Context.getSharedPreferences(Preferences.LoginPreferences, Context.MODE_PRIVATE);
        params.put("username", username);
        params.put("password", password);
        return params;
    }
}
