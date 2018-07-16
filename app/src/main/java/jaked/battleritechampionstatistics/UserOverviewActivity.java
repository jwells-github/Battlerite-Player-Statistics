package jaked.battleritechampionstatistics;

import android.support.v4.app.Fragment;

public class UserOverviewActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return UserOverviewFragment.newInstance();
    }
}
