package www.luneyco.com.proxertestapp.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * In here is the state of the login tracked. So that we do not have to penetrate the server all the time.
 * Only hold one entry...maybe use a singleton might be better ?
 * Created by TS on 01.09.2015.
 */
public class LoginState extends RealmObject {

    @PrimaryKey
    private int  m_UserId;
    private Date m_LastAccessDate;

    public LoginState(){

    }

    public LoginState(int _UserId) {
        m_UserId = _UserId;
        m_LastAccessDate = new Date(System.currentTimeMillis());
    }

    public int getM_UserId() {
        return m_UserId;
    }

    public void setM_UserId(int m_UserId) {
        this.m_UserId = m_UserId;
    }

    public Date getM_LastAccessDate() {
        return m_LastAccessDate;
    }

    public void setM_LastAccessDate(Date m_LastAccessDate) {
        this.m_LastAccessDate = m_LastAccessDate;
    }

}
