package www.luneyco.com.proxertestapp.utils.provider;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Provider to deliver the Volley instance and to use volley request queues much more effective.
 * Created by Tino on 10.10.2015.
 */
public class VolleyProvider {

    /**
     * The Instance of the VolleyProvider.
     */
    private static VolleyProvider mInstance;

    /**
     * The Volley Request queue. To manage the Volley request queue.
     */
    private RequestQueue mQueue;

    /**
     *The {@link Context} of the current app. Use Context.getApplicationContext() to prevent other application call errors.
     */
    private Context mContext;

    /**
     * Get the Instance of the VolleyProvider.
     * @param _Context the context of the current app.
     * @return the {@link VolleyProvider} object to add a new request.
     */
    public static VolleyProvider getInstance(Context _Context) {
        if(mInstance == null){
            mInstance = new VolleyProvider(_Context);
        }
        return mInstance;
    }

    /**
     * Constructor of the VolleyProvider.
     * @param _Context the context of the app.
     */
    private VolleyProvider(Context _Context) {
        mContext = _Context;
    }

    /**
     * Get the current request queue.
     * @return the global request queue.
     */
    public RequestQueue getRequestQueue(){
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    /**
     * Add a request to the volley request queue.
     * @param _Request the request to be added.
     * @param <T> of type Request.
     */
    public <T> void addToRequestQueue(Request<T> _Request){
        getRequestQueue().add(_Request);
    }


}
