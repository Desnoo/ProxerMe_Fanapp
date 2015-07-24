package www.luneyco.com.proxertestapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class List_testFragment extends Fragment {

    private ListView mListView;
    private Context mContext;
    private ArrayAdapter<String> mListAdapter;

    public List_testFragment() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_test, container, false);
        mListView = (ListView) view.findViewById(R.id.material_design_list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListAdapter = new ArrayAdapter<String>(mContext, R.layout.single_line_list_with_icon, android.R.id.text1,new String[]{"Hallo", "Anyone out there", "Balalalala", "loram", "lorem"});
        mListView.setAdapter(mListAdapter);
    }
}
