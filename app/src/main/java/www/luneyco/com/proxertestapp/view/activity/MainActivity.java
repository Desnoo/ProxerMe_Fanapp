package www.luneyco.com.proxertestapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import www.luneyco.com.proxertestapp.PersistentCookieStore;
import www.luneyco.com.proxertestapp.R;
import www.luneyco.com.proxertestapp.config.Preferences;
import www.luneyco.com.proxertestapp.view.fragment.MainActivityFragment;
import www.luneyco.com.proxertestapp.utils.AlertHelper;
import www.luneyco.com.proxertestapp.utils.FragmentManager;
import www.luneyco.com.proxertestapp.view.service.NotificationService;


public class MainActivity extends BaseDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager.replaceFragment(this, MainActivityFragment.newInstance(), true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent newIntent = new Intent(this, PreferencesActivity.class);
            startActivity(newIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // on last step go to home screen
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
