package www.luneyco.com.proxertestapp.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import www.luneyco.com.proxertestapp.R;

/**
 * Base activity for usage of a drawer.
 * Created by tinos_000 on 07.10.2015.
 */
public class BaseDrawerActivity extends AppCompatActivity {

    public static final int NAVIGATION_NEWS = 0;
    public static final int NAVIGATION_ANIME = 1;
    private static final int NAVIGATION_SETTINGS = 2;
    private static final int NAVIGATION_DONATE = 3;

    private DrawerLayout mDrawerLayout;
    private ListView mNavigationView;
    private String[] mNavigationItems;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mTitle = getTitle();
        mDrawerTitle = getString(R.string.drawer_open);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (ListView) findViewById(R.id.drawer_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationItems = getResources().getStringArray(R.array.navigation_items);

        mNavigationView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mNavigationItems));
        mNavigationView.setOnItemClickListener(new OnDrawerListClickListener());

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle != null ? mTitle : "Unknown");
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mNavigationView);
        if(isDrawerOpen){

        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * pass the click event to the actionbardrawertoggle if it returns true the app icon was pressed.
         */
        if(mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ClickListener which is used to interact as the main navigation unit.
     */
    private class OnDrawerListClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> _Parent, View _View, int _Position, long _Id) {
            selectItem(_Position);
        }

    }

    /**
     * Do the navigation inside of the app.
     * @param _Position the target intent.
     */
    private void selectItem(int _Position) {
        Intent intent = null;
        mNavigationView.setItemChecked(_Position,true);
        switch (_Position) {
            case NAVIGATION_NEWS:
                intent = new Intent(BaseDrawerActivity.this, NewsActivity.class);
                break;
            case NAVIGATION_ANIME:
                intent = new Intent(BaseDrawerActivity.this, AnimeActivity.class);
                break;
            case NAVIGATION_SETTINGS:
                intent = new Intent(BaseDrawerActivity.this, PreferencesActivity.class);
                break;
            case NAVIGATION_DONATE:
                String url  = getResources().getString(R.string.abc_donation_link);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                break;
        }

        if(intent != null){
            startActivity(intent);
        }
    }


    public void setTitle(CharSequence _Title) {
        mToolbar.setTitle(_Title);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
