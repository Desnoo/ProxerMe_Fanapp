package www.luneyco.com.proxertestapp.model;

/**
 * The User model to hold user data.
 * Created by tinos_000 on 24.07.2015.
 */
public class User {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof User)) return false;

        User user = (User) o;

        return getmId() == user.getmId();

    }

    @Override
    public int hashCode() {
        return getmId();
    }

    //endregion
}
