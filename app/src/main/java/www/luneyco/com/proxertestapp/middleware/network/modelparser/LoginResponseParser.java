package www.luneyco.com.proxertestapp.middleware.network.modelparser;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import www.luneyco.com.proxertestapp.config.NetworkRequestUrls;
import www.luneyco.com.proxertestapp.model.Login;
import www.luneyco.com.proxertestapp.model.LoginState;
import www.luneyco.com.proxertestapp.model.LoginStateAccessor;
import www.luneyco.com.proxertestapp.request.general.GsonAuthRequest;

/**
 * Parses the login response.
 * Created by TS on 31.08.2015.
 */
public class LoginResponseParser implements Response.ErrorListener, Response.Listener<Login> {

    private Context          m_Context;
    private IResponse<Login> m_Listener;


    public LoginResponseParser(IResponse<Login> _ResponseListener, Context _Context) {
        m_Context = _Context;
        m_Listener = _ResponseListener;
    }

    /**
     * Performs an Login Request if session isnt valid.
     * @param _Name
     * @param _Password
     * @return true if session is valid false if not.
     */
    public boolean Login(String _Name, String _Password) {

        Realm realm = Realm.getInstance(m_Context);
        LoginState loginState = realm.where(LoginState.class).findFirst();
        if(loginState != null){
            LoginStateAccessor loginStateAccessor = new LoginStateAccessor(loginState);
            if(loginStateAccessor.isSessionValid()){
                return true;
            }
        }
        RequestQueue queue = Volley.newRequestQueue(m_Context);
        GsonAuthRequest<Login> loginRequest = new GsonAuthRequest<Login>(
                Request.Method.POST,
                NetworkRequestUrls.LoginRequestUrl,
                Login.class,
                this, this, m_Context, _Name, _Password
        );
        queue.add(loginRequest);
        return false;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(m_Context, "Login nicht möglich!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Login _Response) {
        if(_Response.getCode() == 0){
            LoginStateAccessor loginStateAccessor = new LoginStateAccessor(_Response.getUid());
            loginStateAccessor.saveOrUpdate(m_Context);
            m_Listener.onResponse(_Response);
            Toast.makeText(m_Context, _Response.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        m_Listener.onErrorResponse();
    }
}
