package www.luneyco.com.proxertestapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.activity.NewsActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button mLoginButton;
    private Button mShowNotificationsButton;

    private Button mStartTestListActivityButton;

    private TextView mDebugTextView;

    private EditText mLoginName;
    private EditText mPassword;

    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity _Activity) {
        super.onAttach(_Activity);
        mContext = _Activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mShowNotificationsButton = (Button) view.findViewById(R.id.notification);
        mStartTestListActivityButton = (Button) view.findViewById(R.id.button_start_activity);
        mDebugTextView = (TextView) view.findViewById(R.id.debugText);
        mLoginName = (EditText) view.findViewById(R.id.login_name);
        mPassword = (EditText) view.findViewById(R.id.login_password);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mStartTestListActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mContext, NewsActivity.class);
                startActivity(newIntent);
            }
        });

        mShowNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                String url = "https://proxer.me/notifications?format=raw&s=count";

                StringRequest stringRequest = new StringRequest
                        (Request.Method.GET, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String _Response) {
                                String out = "[Notification] Out: \n";
                                if (_Response.contains("#")) {
                                    // when we have a hashtag all is fine
                                    String[] vals = _Response.split("#");
                                    int[] values = new int[vals.length];
                                    for (int index = 0; index < vals.length; ++ index) {
                                        try {
                                            values[index] = Integer.parseInt(vals[index]);
                                        } catch (NumberFormatException e) {
                                            mDebugTextView.setText(out + " Invalid sign in response! Maybe Api changed!?");
                                            return;
                                        }
                                    }

                                    if (values[0] == 0) {
                                        out += "Pns: " + values[2] + "\nFreundschaftsanfragen: " + values[3] + "\nUngelesene News: " + values[4] + "\nSonstiger kram: " + values[5];
                                    } else {
                                        out += "Error. Some other Failure!";
                                    }
                                } else {
                                    out += "Error. Log in and try again! \n";
                                }
                                mDebugTextView.setText(out);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mDebugTextView.setText("[Notification]Error: " + error.getMessage());
                            }
                        });
                queue.add(stringRequest);

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                String url = "https://proxer.me/login?format=json&action=login";

                StringRequest stringRequest = new StringRequest
                        (Request.Method.POST, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String _Response) {

                                try {
                                    _Response.replaceAll(".*\".*", "\\\"");
                                    JSONObject response = new JSONObject(_Response);
                                    String out = "";

                                    switch (response.getInt("error")) {
                                        case 1:
                                            switch (Integer.valueOf(response.getString("code"))) {
                                                case 0:
                                                case 1:
                                                case 2:
                                                default:
                                                    out += "Error " + response.getString("code") + ": " + response.getString("message");
                                                    break;
                                            }
                                            break;
                                        case 0:
                                            int uid = Integer.valueOf(response.getString("uid"));
                                            String message = response.getString("message");
                                            out += ("Hallo User " + String.valueOf(uid) + ". " + message);
                                            break;
                                        default:
                                            break;
                                    }
                                    mDebugTextView.setText(out);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mDebugTextView.setText("Error with: " + _Response);
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mDebugTextView.setText("Error: " + error.getMessage());
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", mLoginName.getText().toString());
                        params.put("password", mPassword.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mShowNotificationsButton.setOnClickListener(null);
        mLoginButton.setOnClickListener(null);
    }
}