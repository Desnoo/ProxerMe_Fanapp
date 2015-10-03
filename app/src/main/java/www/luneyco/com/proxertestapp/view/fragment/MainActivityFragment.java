package www.luneyco.com.proxertestapp.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Map;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.NetworkRequestUrls;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IListResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.LoginResponseParser;
import www.luneyco.com.proxertestapp.model.Login;
import www.luneyco.com.proxertestapp.request.general.GsonAuthRequest;
import www.luneyco.com.proxertestapp.view.activity.NewsActivity;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.INotificationResponseParserListener;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.NotificationResponseParser;
import www.luneyco.com.proxertestapp.model.Notification;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements INotificationResponseParserListener, IResponse<Login> {

    private Button mLoginButton;
    private Button mShowNotificationsButton;

    private Button mStartTestListActivityButton;

    private TextView mDebugTextView;

    private EditText mLoginName;
    private EditText mPassword;

    private Context mContext;


    public MainActivityFragment() {
    }

    public static Fragment newInstance(){
        Fragment fragment = new MainActivityFragment();
        return fragment;
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
        SharedPreferences prefs = mContext.getSharedPreferences(Preferences.LoginPreferences, Context.MODE_PRIVATE);
        mLoginName.setText(prefs.getString(Preferences.Login.LOGIN_NAME, ""));
        mPassword.setText( prefs.getString(Preferences.Login.LOGIN_PASSWORD, ""));
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
                NotificationResponseParser notificationResponseParser = new NotificationResponseParser(MainActivityFragment.this, mContext);
                notificationResponseParser.DoRequest();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences(Preferences.LoginPreferences, Context.MODE_PRIVATE)
                        .edit()
                        .putString(Preferences.Login.LOGIN_NAME, mLoginName.getText().toString())
                        .putString(Preferences.Login.LOGIN_PASSWORD, mPassword.getText().toString())
                        .commit();

                LoginResponseParser loginResponseParser = new LoginResponseParser(MainActivityFragment.this, mContext);
                if(loginResponseParser.Login(mLoginName.getText().toString(),mPassword.getText().toString() )){
                    Toast.makeText(mContext, "Bereits eingeloggt!", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mShowNotificationsButton.setOnClickListener(null);
        mLoginButton.setOnClickListener(null);
    }

    @Override
    public void onResponse(Notification _Notification) {
        mDebugTextView.setText(_Notification.toString());
    }

    @Override
    public void onResponse(Login _Ret) {

    }

    @Override
    public void onErrorResponse() {

    }
}
