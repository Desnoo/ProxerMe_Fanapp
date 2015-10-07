package www.luneyco.com.proxertestapp.view.activity;

import android.os.Bundle;

import www.luneyco.com.proxertestapp.utils.FragmentManager;
import www.luneyco.com.proxertestapp.view.fragment.anime.AnimeEpisodesFragment;

/**
 * Created by tinos_000 on 07.10.2015.
 */
public class AnimeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager.replaceFragment(this, new AnimeEpisodesFragment(), true);
    }
}
