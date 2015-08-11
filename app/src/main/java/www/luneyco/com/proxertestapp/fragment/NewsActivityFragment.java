package www.luneyco.com.proxertestapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.adapter.NewsListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {

    private Context mContext;
    private ListView mListView;
    private NewsListAdapter mListAdapter;

    public NewsActivityFragment() {
    }

    @Override
    public void onAttach(Activity _Activity) {
        super.onAttach(_Activity);
        mContext = _Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mListView = (ListView) view.findViewById(R.id.newsListHolder);
        mListView.setAdapter(mListAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new NewsListAdapter(mContext, android.R.layout.simple_list_item_1, new ArrayList<String>());
    }

    @Override
    public void onResume() {
        super.onResume();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        int page = 1;
        String url = "http://proxer.me/notifications?format=json&s=news&p=" + String.valueOf(page);

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String _Response) {

                        try {
                            _Response.replaceAll(".*\".*", "\\\"");
                            JSONObject response = new JSONObject(_Response);
                            String out = "";

                            switch (response.getInt("error")){
                                case 0:

                                    JSONArray array = response.getJSONArray("notifications");
                                    String[] stringOut = new String[array.length()];
                                    for(int Index = 0; Index < array.length(); ++ Index){
                                        JSONObject obj = array.getJSONObject(Index);
                                        mListAdapter.add(obj.getString("nid"));
                                        //stringOut[Index] = obj.getString("nid");
                                    }
                                    break;

                                case 1:
                                    switch (Integer.valueOf(response.getString("code"))) {
                                        case 0:
                                        case 1:
                                        case 2:
                                        default:
                                            out += "Error " + response.getString("code") + ": " + response.getString("message");
                                            Toast.makeText(mContext, out, Toast.LENGTH_LONG).show();

                                            break;
                                    }
                                    break;

                                default:
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);
    }
}
