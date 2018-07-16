package jaked.battleritechampionstatistics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChampionListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ChampionAdapter mAdapter;

    private String sortBy = "name";


    
    public static ChampionListFragment newInstance() {
        return new ChampionListFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.champion_statistics);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.sortByName:
                sortBy = "name";
                updateList();
                return true;
            case R.id.sortByWins:
                sortBy = "wins";
                updateList();
                return true;
            case R.id.sortByLosses:
                sortBy = "losses";
                updateList();
                return true;
            case R.id.sortByTimePlayed:
                sortBy = "timeplayed";
                updateList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_champion_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_champion_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateList();

        return view;
    }

    //  Sets the adapter for the Recyclerview if it doesn't already have one
    //  Otherwise update the Recyclerview
    private void updateList(){

        ChampionLab championLab = ChampionLab.get(getActivity());

        List<Champion> champions;

        // Sorts the list by the chosen paramater
        switch (sortBy){
            case "name":
                champions = championLab.getChampionsByName();
                break;
            case "wins":
                 champions = championLab.getChampionsByWins();
                break;
            case "losses":
                champions = championLab.getChampionsByLosses();
                break;
            case "timeplayed":
                champions = championLab.getChampionsByTimePlayed();
                break;
            default:
                System.out.println("default");
                champions = championLab.getChampionsByName();
        }

        // Creates a new adapter, if one already exists the data set is updated
        if(mAdapter == null){
            mAdapter = new ChampionAdapter(champions);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();

    }

    private class ChampionHolder extends RecyclerView.ViewHolder{

        final ImageView mChampionImage;
        public TextView mChampionName;
        public TextView mWinRatio;
        public TextView mTotalWins;
        public TextView mTotalLoses;
        public TextView mTimePlayedTitle;
        public TextView mTimePlayed;
        public TextView mLevel;
        public TextView mXP;

        public ChampionHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.champion_list_item,parent,false));
            mChampionImage = itemView.findViewById(R.id.ivChampionImage);
            mChampionName = itemView.findViewById(R.id.tvChampionName);
            mWinRatio = itemView.findViewById(R.id.tvWinratio);
            mTotalWins = itemView.findViewById(R.id.tvWins);
            mTotalLoses = itemView.findViewById(R.id.tvLosses);
            mTimePlayedTitle = itemView.findViewById(R.id.tvTimePlayedTitle);
            mTimePlayed = itemView.findViewById(R.id.tvTimePlayed);
            mLevel = itemView.findViewById(R.id.tvChampionLevel);
          //  mXP = itemView.findViewById(R.id.tvXP);

        }
    }

    private class ChampionAdapter extends RecyclerView.Adapter<ChampionHolder>{
        private List<Champion> mChampionList;

        public ChampionAdapter(List<Champion> champions){
            mChampionList = champions;
        }

        @Override
        public ChampionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ChampionHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(ChampionHolder championHolder, int i) {
            Champion champion = mChampionList.get(i);
            championHolder.mChampionName.setText(champion.getName());
            championHolder.mTotalWins.setText(champion.getWins());
            championHolder.mTotalLoses.setText(champion.getLosses());
            championHolder.mTimePlayed.setText(champion.getTimePlayed()  + " Hours");
//            championHolder.mXP.setText(champion.getXP());
            championHolder.mChampionImage.setImageResource(getImage(champion.getName()));

            // Calculate the win percentage
            String ratio = String.valueOf((Float.parseFloat(champion.getWins()) / (Float.parseFloat(champion.getWins()) + Float.parseFloat(champion.getLosses()))) * 100.0f);

            // Trims the string to 3 figures
            if (ratio.length() < 4){
                ratio = ratio.substring(0,3);
            }
            else {
                ratio = ratio.substring(0,4);
            }
            championHolder.mWinRatio.setText(ratio + "%");

            championHolder.mLevel.setText(champion.getLevel());

        }

        @Override
        public int getItemCount() {
            return mChampionList.size();
        }
    }

    // Returns the corresponding image for a given champion's name
    public int getImage(String championName){
        switch (championName){
            case "Lucie":
                return R.drawable.luice;
            case "Sirius":
                return R.drawable.sirius;
            case "Iva":
                return R.drawable.iva;
            case "Jade":
                return R.drawable.jade;
            case "Ruh Kaan":
                return R.drawable.ruh_kaan;
            case "Oldur":
                return R.drawable.oldur;
            case "Ashka":
                return R.drawable.ashka;
            case "Varesh":
                return R.drawable.varesh;
            case "Pearl":
                return R.drawable.pearl;
            case "Taya":
                return R.drawable.taya;
            case "Poloma":
                return R.drawable.poloma;
            case "Croak":
                return R.drawable.croak;
            case "Freya":
                return R.drawable.freya;
            case "Jumong":
                return R.drawable.jumong;
            case "Shifu":
                return R.drawable.shifu;
            case "Ezmo":
                return R.drawable.ezmo;
            case "Bakko":
                return R.drawable.bakko;
            case "Rook":
                return R.drawable.rook;
            case "Pestilus":
                return  R.drawable.pestilus;
            case "Destiny":
                return R.drawable.destiny;
            case "Raigon":
                return R.drawable.raigon;
            case "Blossom":
                return R.drawable.blossom;
            case "Thorn":
                return R.drawable.thorn;
            case "Zander":
                return R.drawable.zander;
            case "Ulric":
                return R.drawable.ulric;
            case "Alysia":
                return R.drawable.alysia;
            case "Jamila":
                return R.drawable.jamila;
            default:
                return R.drawable.ic_launcher_background;
        }

    }
}
