package www.luneyco.com.proxertestapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The User model to hold user data.
 * Created by tinos_000 on 24.07.2015.
 */
public class User extends RealmObject{

    @PrimaryKey
    private int mId;

    private String mCreatorName;

    //region Getter and Setter

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmId() {
        return mId;
    }

    public String getmCreatorName() {
        return mCreatorName;
    }

    public void setmCreatorName(String mCreatorName) {
        this.mCreatorName = mCreatorName;
    }

    //endregion

    //region equals and hashCode

    //endregion
}
