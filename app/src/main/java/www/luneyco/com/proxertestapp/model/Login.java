package www.luneyco.com.proxertestapp.model;

/**
 * Model for logging in to parse it with gson.
 * Created by TS on 01.09.2015.
 */
public class Login {

    private int    error;
    private String message;
    private int uid;
    private int    code;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
