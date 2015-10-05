package www.luneyco.com.proxertestapp.view.adapter;

import android.content.Context;

import io.realm.RealmResults;
import www.luneyco.com.proxertestapp.model.News;
import www.luneyco.com.proxertestapp.view.adapter.base.RealmModelAdapter;

/**
 * Adapter to fit the adapter conventions.
 * Created by tinos_000 on 05.10.2015.
 */
public class RealmNewsAdapter extends RealmModelAdapter<News> {

    public RealmNewsAdapter(Context context, RealmResults<News> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


}
