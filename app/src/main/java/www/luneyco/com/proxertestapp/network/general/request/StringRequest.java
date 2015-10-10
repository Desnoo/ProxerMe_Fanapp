package www.luneyco.com.proxertestapp.network.general.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * String Request to get String response for a normal request.
 * created by ts on 14.08.2015.
 */
public class StringRequest extends Request<String> {

    private static final String LOG_TAG = StringRequest.class.getName();

    protected Response.Listener<String> m_Listener;

    /**
     * Construct this request to get an String response.
     * @param _Method the request method.
     * @param _Url the request url.
     * @param _Listener the listener that gets the response.
     * @param _ErrorListener the listener for an error.
     */
    public StringRequest(int _Method, String _Url, Response.Listener<String> _Listener, Response.ErrorListener _ErrorListener) {
        super(_Method, _Url, _ErrorListener);
        m_Listener = _Listener;
    }

    @Override
    protected void deliverResponse(String _Response) {
        m_Listener.onResponse(_Response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse _Response) {
        try {
            String response = new String(_Response.data, "UTF-8");
            Log.d(LOG_TAG, response);
            return Response.success(response,HttpHeaderParser.parseCacheHeaders(_Response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
