package www.luneyco.com.proxertestapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by tinos_000 on 24.07.2015.
 */
public class NewsListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mEntries;


    public NewsListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public void add(String object) {
        super.add(object);
    }


}
