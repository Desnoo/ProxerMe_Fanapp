package www.luneyco.com.proxertestapp.request.general;

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

    protected Response.Listener<String> m_Listener;

    /**
     * Construct this request to get an String response.
     * @param _Method
     * @param _Url
     * @param _Listener
     * @param _ErrorListener
     */
    public StringRequest(int _Method, String _Url, Response.Listener<String> _Listener, Response.ErrorListener _ErrorListener) {
        super(_Method, _Url, _ErrorListener);
    }

    @Override
    protected void deliverResponse(String _Response) {
        m_Listener.onResponse(_Response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse _Response) {
        try {
            String response = new String(_Response.data, HttpHeaderParser.parseCharset(_Response.headers));
            return Response.success(response,HttpHeaderParser.parseCacheHeaders(_Response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
