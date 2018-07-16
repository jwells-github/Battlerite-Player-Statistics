package jaked.battleritechampionstatistics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserOverviewFragment extends Fragment {

    private String mPlayerName;

    public static UserOverviewFragment newInstance() {
        return new UserOverviewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.user_statistics);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_overview, container, false);
        ChampionLab championLab = ChampionLab.get(getActivity());

        TextView tvTotalLosses = v.findViewById(R.id.tvTotalLosses);
        TextView tvTotalWins = v.findViewById(R.id.tvTotalWins);
        TextView tvPlayerName = v.findViewById(R.id.tvPlayerName);
        TextView tvAccountLevel = v.findViewById(R.id.tvAccountLevel);
        Button btChampionStats = v.findViewById(R.id.btnChampionStats);
        tvTotalLosses.setText(championLab.getTotalLosses());
        tvTotalWins.setText(championLab.getTotalWins());
        tvAccountLevel.setText("Account Level: " + championLab.getAccountLevel());

        getName();
        tvPlayerName.setText(mPlayerName);

        // Start the ChampionListActivity
        btChampionStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChampionListActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    // Gets the saved username
    public void getName(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        mPlayerName = sharedPreferences.getString("PlayerName","");
    }
}
