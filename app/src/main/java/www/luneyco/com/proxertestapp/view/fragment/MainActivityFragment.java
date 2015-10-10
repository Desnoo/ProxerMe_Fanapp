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

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.IResponse;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.impl.LoginResponseParser;
import www.luneyco.com.proxertestapp.middleware.network.modelparser.impl.NotificationResponseParser;
import www.luneyco.com.proxertestapp.model.Login;
import www.luneyco.com.proxertestapp.model.Notification;
import www.luneyco.com.proxertestapp.view.activity.NewsActivity;


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

    public static Fragment newInstance() {
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
        mShowNotificationsButton = (Button) view.findViewById(R.id.news_button);
        mStartTestListActivityButton = (Button) view.findViewById(R.id.button_start_activity);
        mDebugTextView = (TextView) view.findViewById(R.id.debugText);
        mLoginName = (EditText) view.findViewById(R.id.login_name);
        mPassword = (EditText) view.findViewById(R.id.login_password);
        SharedPreferences prefs = mContext.getSharedPreferences(Preferences.LoginPreferences, Context.MODE_PRIVATE);
        mLoginName.setText(prefs.getString(Preferences.Login.LOGIN_NAME, ""));
        mPassword.setText(prefs.getString(Preferences.Login.LOGIN_PASSWORD, ""));
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
                NotificationResponseParser notificationResponseParser = new NotificationResponseParser(mNotificationResponse, mContext);
                notificationResponseParser.doRequest();
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

                LoginResponseParser loginResponseParser = new LoginResponseParser(mLoginResponse, mContext);
                if (loginResponseParser.Login(mLoginName.getText().toString(), mPassword.getText().toString())) {
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


    /**
     * Response handler for {@link Notification}s.
     *
     * @param _Notification the notification.
     */
    public void onResponse(Notification _Notification) {
        mDebugTextView.setText(_Notification.toString());
    }

    /**
     * The response handler for {@link Login}.
     *
     * @param _Ret the current Login state.
     */
    public void onResponse(Login _Ret) {

    }

    /**
     * General handler for errors.
     */
    public void onErrorResponse() {

    }


    //region Anonynmous inner classes for parser callbacks.

    /**
     * inner anonymus class for {@link Notification}s callbacks.
     */
    private IResponse<Notification> mNotificationResponse = new IResponse<Notification>() {
        @Override
        public void onResponse(Notification _Ret) {
            MainActivityFragment.this.onResponse(_Ret);
        }

        @Override
        public void onErrorResponse() {
            MainActivityFragment.this.onErrorResponse();
        }
    };

    /**
     * inner anonymus class for {@link Login} callback.
     */
    private IResponse<Login> mLoginResponse = new IResponse<Login>() {
        @Override
        public void onResponse(Login _Ret) {
            MainActivityFragment.this.onResponse(_Ret);
        }

        @Override
        public void onErrorResponse() {
            MainActivityFragment.this.onErrorResponse();
        }
    };

    //endregion
}
