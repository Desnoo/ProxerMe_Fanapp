package www.luneyco.com.proxertestapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The Category model to hold category data.
 * Created by tinos_000 on 24.07.2015.
 */
public class Category extends RealmObject {

    @PrimaryKey
    private int mId;

    private String mCategoryName;

    //region Getter and Setter
    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmId() {
        return mId;
    }
    //endregion

    //region equals and hashCode

    //endregion
}
