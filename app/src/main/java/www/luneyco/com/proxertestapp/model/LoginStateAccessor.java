package www.luneyco.com.proxertestapp.model;

import android.content.Context;

import java.util.Date;

import io.realm.Realm;

/**
 * Accessor for Login state because of realm.
 * Created by TS on 02.09.2015.
 */
public class LoginStateAccessor {

    private LoginState m_LoginState;

    public LoginStateAccessor(int _UserId) {
       m_LoginState = new LoginState(_UserId);
    }

    public LoginStateAccessor(LoginState _LoginState) {
        m_LoginState = _LoginState;
    }

    public int getM_UserId() {
        return m_LoginState.getM_UserId();
    }

    public void setM_UserId(int m_UserId) {
        this.m_LoginState.setM_UserId(m_UserId);
    }

    public Date getM_LastAccessDate() {
        return this.m_LoginState.getM_LastAccessDate();
    }

    public void setM_LastAccessDate(Date m_LastAccessDate) {
        this.m_LoginState.setM_LastAccessDate(m_LastAccessDate);
    }

    /**
     * Changes the last accessed date.
     */
    public void touch(Context _Context){
        this.m_LoginState.setM_LastAccessDate(new Date(System.currentTimeMillis()));
        Realm realm = Realm.getInstance(_Context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this.m_LoginState);
        realm.commitTransaction();
    }

    /**
     * Call this to check if the current session is currently active and has not yet ended.
     * @return true if is valid, false if not.
     */
    public boolean isSessionValid(){
        return this.m_LoginState.getM_LastAccessDate().before(new Date(System.currentTimeMillis()));
    }

    /**
     * Saves or updates the context.
     * @param _Context
     */
    public void saveOrUpdate(Context _Context){
        Realm realm = Realm.getInstance(_Context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this.m_LoginState);
        realm.commitTransaction();
    }
}
