package www.luneyco.com.proxertestapp.view.activity;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.events.FragmentChangeEvent;
import www.luneyco.com.proxertestapp.utils.Utils;
import www.luneyco.com.proxertestapp.view.fragment.NewsFragment;
import www.luneyco.com.proxertestapp.utils.provider.BusProvider;
import www.luneyco.com.proxertestapp.utils.FragmentManager;

/**
 * Activity that handles all news related things.
 */
public class NewsActivity extends BaseDrawerActivity {

    private static final String LOG_TAG = NewsActivity.class.getName();
    public static final String ORIENTATION_CHANGE = "orientation_change";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "OnCreate");

        /**
         * first time the onCreate was called. So we start the {@link NewsFragment} from here
         */
        FragmentManager.replaceFragment(this, NewsFragment.newInstance(true), true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(LOG_TAG, "Configuration changed!");
        // do not load any new news after rotation and dont add it to the backstack
        //FragmentManager.replaceFragment(this, NewsFragment.newInstance(false), false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //#region Event listener
    @Subscribe
    public void changeFragment(FragmentChangeEvent _Event) {
        FragmentManager.replaceFragment(this, _Event.getFragment(), true);
    }
    //#endregion
}
