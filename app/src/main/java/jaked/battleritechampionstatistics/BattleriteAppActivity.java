package jaked.battleritechampionstatistics;

import android.support.v4.app.Fragment;

public class BattleriteAppActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return BattleriteAppFragment.newInstance();
    }

}
