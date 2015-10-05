package www.luneyco.com.proxertestapp.view.adapter.base;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import android.support.v7.widget.RecyclerView;

/**
 * Adapter to use the realm database to display the contents.
 * Created by tinos_000 on 05.10.2015.
 */
public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    /**
     * The realm adapter for this recyclerview.
     */
    private RealmBaseAdapter<T> mRealmAdapter;

    /**
     * Set the corresponding adapter for this adapter.
     * @param _Adapter the adapter to be used for this view.
     */
    public void setRealmAdapter(RealmBaseAdapter<T> _Adapter){
        mRealmAdapter = _Adapter;
    }

    public T getItem(int _Position){
        return mRealmAdapter.getItem(_Position);
    }

    public RealmBaseAdapter<T> getRealmAdapter(){
        return mRealmAdapter;
    }

}
