package jaked.battleritechampionstatistics;

import android.support.v4.app.Fragment;

public class ChampionListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ChampionListFragment.newInstance();
    }
}
