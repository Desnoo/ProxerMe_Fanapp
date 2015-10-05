package www.luneyco.com.proxertestapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tinos_000 on 05.10.2015.
 */
public class Test extends RealmObject {

    @PrimaryKey
    private int mId;

    // Unix Timestamp
    private long mCreationTimeStamp;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public long getmCreationTimeStamp() {
        return mCreationTimeStamp;
    }

    public void setmCreationTimeStamp(long mCreationTimeStamp) {
        this.mCreationTimeStamp = mCreationTimeStamp;
    }
}
